package com.monopatinmicroservicio.service;

import com.monopatinmicroservicio.model.*;
import com.monopatinmicroservicio.repository.MonopatinRepository;
import com.monopatinmicroservicio.service.DTO.*;
import com.monopatinmicroservicio.service.DTO.monopatin.MonopatinDTORequest;
import com.monopatinmicroservicio.service.DTO.monopatin.MonopatinDTOResponse;
import com.monopatinmicroservicio.service.DTO.monopatin.MonopatinMasUsadoDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class MonopatinService {

    private final MonopatinRepository repository;
    private WebClient webClient;

    /*
    * TODO: Al empezar el viaje en el otro microservicio, podria pegarle a este servicio
    *  para que se actualice cada segundo la ubicacion y
    *  se vaya aumentando su kilometraje
    */

    public MonopatinService(MonopatinRepository repository) {
        this.repository = repository;
        this.webClient = WebClient.create("http://localhost:8080");
    }
    // CRUD
    @Transactional(readOnly = true)
    public Optional<MonopatinDTOResponse> traerMonopatin(Long id){
        Optional<Monopatin> monopatin = repository.findById(id);
        return monopatin.map(MonopatinDTOResponse::new);


    }

    @Transactional(readOnly = true)
    public List<MonopatinDTOResponse> traerMonopatines() {
       List<Monopatin> monopatines = repository.findAll();
       return monopatines.stream().map(MonopatinDTOResponse::new).toList();
    }

    @Transactional
    public boolean eliminarMonopatin(Long id) {
        try{
            if (repository.existsById(id)){
                repository.deleteById(id);
                return true;
            }
            return false;
        }catch (Error err){
            return false;
        }
    }

    @Transactional
    public MonopatinDTOResponse agregarMonopatin(MonopatinDTORequest monopatin){
        Monopatin savedMonopatin = repository.save(new Monopatin(monopatin));
        return new MonopatinDTOResponse(savedMonopatin);
    }

    // OTRAS
    @Transactional
    public boolean cambiarDisponibilidad(Long id, boolean disponibilidad) {
        Monopatin monopatin = repository.findById(id).orElse(null);

        if (monopatin != null) {
            monopatin.setEnMantenimiento(disponibilidad);
            repository.save(monopatin);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean actualizarUbicacion(Long id, Ubicacion ubicacion) {
        Optional<Monopatin> optionalMonopatin = repository.findById(id);
        if (optionalMonopatin.isPresent()) {
            Monopatin monopatin = optionalMonopatin.get();
            monopatin.setUbicacion(ubicacion);
            repository.save(monopatin);
            return true;
        }
        return false;
    }



    @Transactional
    public List<MonopatinDTOResponse> traerMonopatinesCercanos(double latitud, double longitud) {
        return repository.traerMonopatinesCercanos(latitud, longitud)
                .stream()
                .map(MonopatinDTOResponse::new)
                .toList();
    }


    //tiene sentido pegarle al otro microservicio si solo traigo datos pero no hago mas que mostrarlos?
    @Transactional
    public Flux<MonopatinMasUsadoDTO> traerMonopatinesMasUsados(int minCantidadViajes, int anio) {
        return webClient.get()
                .uri("/viaje/monopatinesMasUsados/minCantidadViajes={minCantidadViajes}&anio={anio}", minCantidadViajes, anio)
                .retrieve()
                .bodyToFlux(MonopatinMasUsadoDTO.class);
    }


    @Transactional
    public ReporteEstadoMonopatinesDTO traerReporteEstadoMonopatines() {
        return this.repository.traerReporteEstadoMonopatines();
    }

    @Transactional
    public List<ReporteKilometrajeDTO> traerReporteKilometraje() {
        return this.repository.traerReporteKilometraje();
    }



}
