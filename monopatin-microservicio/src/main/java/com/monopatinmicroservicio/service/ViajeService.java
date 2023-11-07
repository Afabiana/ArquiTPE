package com.monopatinmicroservicio.service;

import com.monopatinmicroservicio.model.*;
import com.monopatinmicroservicio.repository.MonopatinRepository;
import com.monopatinmicroservicio.repository.MonopatinViajeRepository;
import com.monopatinmicroservicio.repository.TarifaRepository;
import com.monopatinmicroservicio.repository.ViajeRepository;
import com.monopatinmicroservicio.service.DTO.TarifaDTORequest;
import com.monopatinmicroservicio.service.DTO.ViajeDTORequest;
import com.monopatinmicroservicio.service.DTO.ViajeDTOResponse;
import jakarta.transaction.Transactional;
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
    private MonopatinRepository monopatinRepository;
    private MonopatinViajeRepository monopatinViajeRepository;
    private TarifaRepository tarifaRepository;
    public ViajeService(ViajeRepository viajeRepository,
                        TarifaRepository tarifaRepository,
                        MonopatinRepository monopatinRepository,
                        MonopatinViajeRepository monopatinViajeRepository) {
        this.webClient = WebClient.create();
        this.repository = viajeRepository;
        this.tarifaRepository = tarifaRepository;
        this.monopatinViajeRepository = monopatinViajeRepository;
        this.monopatinRepository = monopatinRepository;
    }

    @Transactional
    public ViajeDTOResponse empezarViaje(ViajeDTORequest viaje) {
        System.out.println(viaje);
        //busco el monopatin y la tarifa del viaje que se quiere iniciar
        Optional<Monopatin> monopatinNuevo = this.monopatinRepository.findById(viaje.getId_monopatin());
        Optional<Tarifa> tarifa = this.tarifaRepository.findById(viaje.getId_tarifa());

        //verifico que existan
        if (monopatinNuevo.isEmpty()|| tarifa.isEmpty() || !monopatinNuevo.get().isDisponible()){
            return null;
        }
        Viaje viajeNuevo = new Viaje(
                LocalDateTime.now(),
                viaje.getId_cuenta(),
                tarifa.get()
        );

        this.repository.save(viajeNuevo);
        MonopatinViaje monopatinViaje = new MonopatinViaje(viajeNuevo,monopatinNuevo.get());
        this.monopatinViajeRepository.save(monopatinViaje);
        monopatinNuevo.get().setDisponibilidad(false);
        this.monopatinRepository.save(monopatinNuevo.get());

        //deberia llamar a cobrar viaje? o eso lo hace el front o lo que consuma este servicio?
        return new ViajeDTOResponse(viajeNuevo);
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
                    .uri("http://localhost:8081/cuenta/descontar/" + viaje.getId_cuenta() + "?monto=" + monto)
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


    @Transactional
    public Optional<ViajeDTOResponse> traerViaje(Long id) {
        System.out.println("id :"  +id);
        Optional<Viaje> optionalViaje = this.repository.findById(id);

        if (optionalViaje.isPresent()) {
            ViajeDTOResponse viaje = new ViajeDTOResponse(optionalViaje.get());
            return Optional.of(viaje);
        }

        return Optional.empty();
    }


    @Transactional
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

    @Transactional
    public List<ViajeDTOResponse> traerViajes() {
        return repository.findAll().stream().map(ViajeDTOResponse::new).toList();
    }

    @Transactional
    public boolean eliminarViaje(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

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
                    // llamar a reanudarViaje
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

    @Transactional
    public boolean terminarViaje(Long id) {
        Optional<Viaje> opcional = this.repository.findById(id);
        if (!opcional.isPresent()) {
            return false;
        }
        //finalizo el tiempo de viaje
        Viaje viaje = opcional.get();
        viaje.setHora_fin(LocalDateTime.now());
        this.repository.save(viaje);

        //libero el monopatin
        Monopatin monopatin = this.monopatinViajeRepository.findByViaje(viaje.getId_viaje());
        monopatin.setDisponibilidad(true);
        this.monopatinRepository.save(monopatin);
        return true;
    }
}
