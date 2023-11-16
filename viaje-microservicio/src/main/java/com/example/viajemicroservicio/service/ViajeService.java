package com.example.viajemicroservicio.service;

import com.example.viajemicroservicio.model.*;
import com.example.viajemicroservicio.repository.TarifaRepository;
import com.example.viajemicroservicio.repository.ViajeRepository;
import com.example.viajemicroservicio.service.DTO.monopatin.MonopatinDTO;
import com.example.viajemicroservicio.service.DTO.monopatin.MonopatinesMasUsadosDTO;
import com.example.viajemicroservicio.service.DTO.monopatin.ReporteTiempoDeUsoDTO;
import com.example.viajemicroservicio.service.DTO.tarifa.TarifaDTORequest;
import com.example.viajemicroservicio.service.DTO.viaje.ViajeDTORequest;
import com.example.viajemicroservicio.service.DTO.viaje.ViajeDTOResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {
    private WebClient webClient;
    private ViajeRepository repository;
    private TarifaRepository tarifaRepository;
    public ViajeService(ViajeRepository viajeRepository,
                        TarifaRepository tarifaRepository
                        ) {
        this.webClient = WebClient.create("http://localhost:8080");
        this.repository = viajeRepository;
        this.tarifaRepository = tarifaRepository;
    }

    /**
     * @brief Inicia un viaje
     * @TODO: habria que actualizar la ubicacion del monopatin a medida que se mueve
     * @param viaje
     * @return ViajeDTOResponse con el viaje iniciado
     */
    @Transactional
    public Mono<ViajeDTOResponse> empezarViaje(ViajeDTORequest viaje) {

        Long id = viaje.getId_monopatin();

        //busco el monopatin del viaje que se quiere iniciar
        Mono<MonopatinDTO> monopatinMono =  this.webClient.get()
                .uri("monopatin/usuario/deshabilitar/{id}",id)
                .retrieve()// Enviar la solicitud GET y recibir la respuesta como un Mono
                .bodyToMono(MonopatinDTO.class);

        //busco la tarifa
        Mono<Optional<Tarifa>> tarifaMono = Mono.just(this.tarifaRepository.findById(viaje.getId_tarifa()));

        return Mono.zip(monopatinMono, tarifaMono)
                .flatMap(tuple -> {
                    MonopatinDTO monopatin = tuple.getT1();
                    Optional<Tarifa> tarifaOptional = tuple.getT2();

                    // Verifico que el monopatin exista y que la tarifa tambien
                    if (tarifaOptional.isPresent() && monopatin.getId_monopatin() != null) {
                        //creo el viaje
                        Viaje viajeNuevo = new Viaje(
                                LocalDateTime.now(),
                                viaje.getId_cuenta(),
                                tarifaOptional.get()
                        );

                        this.repository.save(viajeNuevo);

                        // Debería llamar a cobrarViaje aca???

                        // Devuelvo una nueva instancia de ViajeDTOResponse
                        return Mono.just(new ViajeDTOResponse(viajeNuevo));
                    } else {
                        return Mono.error(new RuntimeException("Condiciones de viaje no cumplidas"));
                    }
                });
    }

    @Transactional
    public Mono<Boolean> cobrarViaje(Viaje viaje) {
        Tarifa tarifa = viaje.getTarifa();

        if (viaje.getTarifaExtra() != null) {
            tarifa = viaje.getTarifaExtra();
        }

        double monto = tarifa.getValor_por_segundo();

        if (viaje.getHora_fin() == null) {
            //con delay pordia hacer que se cobre cada 1 segundo
            return webClient.put()
                    .uri("http://localhost:8081/cuenta/descontar/" + viaje.getId_cuenta())
                    .bodyValue(monto) // Enviar monto en el cuerpo
                    .retrieve()
                    .toBodilessEntity()
                    .flatMap(response -> {
                        if (response.getStatusCode().is2xxSuccessful()) {
                            viaje.setCostoTotal(viaje.getCostoTotal() + monto);

                            //guardo de manera asincrónica y devuelve un Mono<Boolean>
                            return Mono.fromCallable(() -> repository.save(viaje))
                                    .map(savedViaje -> true)
                                    .onErrorReturn(false);
                        }
                    return Mono.just(false);
            });
        }

        return Mono.just(false);
    }


    @Transactional(readOnly = true)
    public Optional<ViajeDTOResponse> traerViaje(Long id) {
        System.out.println("id :"  +id);
        Optional<Viaje> optionalViaje = this.repository.findById(id);

        if (optionalViaje.isPresent()) {
            ViajeDTOResponse viaje = new ViajeDTOResponse(optionalViaje.get());
            return Optional.of(viaje);
        }

        return Optional.empty();
    }


    @Transactional(readOnly = true)
    public Double traerTotalFacturado(int anio, int mesDesde, int mesHasta) {
        if (repository.traerTotalFacturado(anio, mesDesde, mesHasta) != null) {
            return repository.traerTotalFacturado(anio, mesDesde, mesHasta);
        }
        return null;
    }

    //metodos que necesitarian rol de admin u otro
    @Transactional
    public Double actualizarTarifa(Long id, double tarifa) {
        Optional<Tarifa> optionalTarifa = tarifaRepository.findById(id);

        if (optionalTarifa.isPresent()) {
            optionalTarifa.get().setValor_por_segundo(tarifa);
            tarifaRepository.save(optionalTarifa.get());
            return optionalTarifa.get().getValor_por_segundo();
        }

        return null;
    }


    @Transactional
    public Double agregarTarifa(TarifaDTORequest tarifa) {
        Tarifa tarifa1 = new Tarifa(tarifa);
        tarifaRepository.save(tarifa1);
        return tarifa1.getValor_por_segundo();
    }

    @Transactional(readOnly = true)
    public List<ViajeDTOResponse> traerViajes() {
        return repository.findAll().stream().map(ViajeDTOResponse::new).toList();
    }

    @Transactional
    public boolean eliminarViaje(Long id) {
        Viaje viaje = repository.findById(id).orElse(null);
        if (viaje!=null) {
            this.repository.delete(viaje);
            return true;
        }
        return false;
    }

    /**
     *
     * @brief Pausa el viaje por 15 minutos
     * Si el usuario supera los 15 minutos de pausa. El tiempo de viaje empieza a correr nuevamente de manera atuomatica
     * y se le cobra ahora una tarifa extra
     * @Todo: se puede optimizar el codigo para que sea mas legible y 100% reactivo
     * @param id id del viaje a pausar
     * @return ViajeDTOResponse con el viaje pausado
     *
     */
    @Transactional
    public ViajeDTOResponse pausarViaje(Long id) {
        Optional<Viaje> viaje = this.repository.findById(id);
        if (!viaje.isPresent() || viaje.get().getPausa() != null) {
            return null;
        }
        Viaje viajeEnPausa = viaje.get();

        Tarifa tarifaExtra = tarifaRepository.getTarifaExtra(LocalDate.now());

        // declaro el contador
        Flux<Long> contador = Flux.interval(Duration.ofMinutes(15)) //va a esperar 15 min
                .take(1)  //nomas se va a ejecutar 1 vez
                .doOnNext(t -> {
                    System.out.println("Terminando pausa excedida");
                    viajeEnPausa.setTarifaExtra(tarifaExtra);
                    this.repository.save(viajeEnPausa);
                    reanudarViaje(id);
                });

        System.out.println("Iniciando pausa");
        // Guardar el viaje con la pausa iniciada
        Pausa pausa = new Pausa();
        viajeEnPausa.setPausa(pausa);
        viajeEnPausa.getPausa().iniciarPausa();
        this.repository.save(viajeEnPausa);

        // Se suscribe al contador para que se ejecute
        contador.subscribe();
        return new ViajeDTOResponse(viajeEnPausa);

    }


    /**
     * @brief Reanuda el viaje
     * @param id id del viaje a reanudar
     * @return true si se pudo reanudar el viaje, false si no se pudo
     */
    @Transactional
    public boolean reanudarViaje(Long id) {
        Optional<Viaje> opcional = this.repository.findById(id);
        if (!opcional.isPresent()) {
            return false;
        }
        Viaje viaje = opcional.get();
        viaje.getPausa().finalizarPausa();

        this.repository.save(viaje);
        //se reanuda el cobro del viaje
        cobrarViaje(viaje).subscribe();
        return true;
    }

    /**
     * @brief Termina el viaje
     * @Todo: se puede optimizar el codigo para que sea mas legible y 100% reactivo
     * @param id id del viaje a terminar
     * @return true si se pudo terminar el viaje, false si no se pudo
     */
    @Transactional(rollbackFor = Exception.class)
    public Mono<Boolean> terminarViaje(Long id) {
        Optional<Viaje> opcional = this.repository.findById(id);
        if (!opcional.isPresent()||opcional.get().getHora_fin()!=null) {
            return Mono.just(false);
        }
        //finalizo el tiempo de viaje
        Viaje viaje = opcional.get();
        viaje.setHora_fin(LocalDateTime.now());
        this.repository.save(viaje);

        //libero el monopatin
        return webClient.put()
                .uri("http://localhost:8080/monopatin/habilitar/{id}", viaje.getId_monopatin())
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .defaultIfEmpty(false);
    }




    @Transactional(readOnly = true)
    public List<MonopatinesMasUsadosDTO> traerMonopatinesMasUsados(int minCantidadViajes, int anio) {
        return repository.traerMonopatinesMasUsados(minCantidadViajes, anio);
    }

    @Transactional(readOnly = true)
    public List<ReporteTiempoDeUsoDTO> traerReporteTiempoDeUsoConPausas() {
        return repository.traerReporteTiempoDeUsoConPausas();
    }

    @Transactional(readOnly = true)
    public List<ReporteTiempoDeUsoDTO> traerReporteTiempoDeUsoSinPausas() {
        return repository.traerReporteTiempoDeUsoSinPausas();
    }
}
