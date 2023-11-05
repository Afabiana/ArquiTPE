package com.monopatinmicroservicio.service;

import com.monopatinmicroservicio.model.Monopatin;
import com.monopatinmicroservicio.model.MonopatinViaje;
import com.monopatinmicroservicio.model.Tarifa;
import com.monopatinmicroservicio.model.Viaje;
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
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.Optional;

@Service("ViajeService")
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
    }

    @Transactional
    public ViajeDTOResponse empezarViaje(ViajeDTORequest viaje) {
        Optional<Monopatin> monopatinNuevo = this.monopatinRepository.findById(viaje.getId_monopatin());
        if (monopatinNuevo.isEmpty()){
            return null;
        }
        Viaje viajeNuevo = new Viaje(viaje);
        MonopatinViaje monopatinViaje = new MonopatinViaje(viajeNuevo,monopatinNuevo.get());
        this.monopatinViajeRepository.save(monopatinViaje);
        //aca podria llamar a un metodo que empiece a cobrar en funcion del tiempo
        //pero esa logica quizas la maneja quien consuma este microservicio
        return new ViajeDTOResponse(viajeNuevo);
    }

    public Mono<Boolean> cobrarViaje(Viaje viaje, double monto) {
        if (viaje.getHora_fin() == null) {
            return webClient.put()
                    .uri("http://localhost:8081/cuenta/descontar/"+viaje.getId_usuario())
                    .bodyValue(monto)
                    .retrieve()
                    .toBodilessEntity()
                    .thenReturn(true)
                    .onErrorReturn(false);
        }
        return Mono.just(false);
    }

    public ViajeDTOResponse pausarViaje(Long id){
        return null;
    }

    @Transactional
    public Optional<ViajeDTOResponse> traerViaje(Long id) {
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
            optionalTarifa.get().setPrecio(tarifa);
            tarifaRepository.save(optionalTarifa.get());
            return optionalTarifa.get().getPrecio();
        }

        return null;
    }


    @Transactional
    public Double agregarTarifa(TarifaDTORequest tarifa) {
        Tarifa tarifa1 = new Tarifa(tarifa);
        tarifaRepository.save(tarifa1);
        return tarifa1.getPrecio();
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

}
