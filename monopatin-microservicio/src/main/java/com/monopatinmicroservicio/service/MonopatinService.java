package com.monopatinmicroservicio.service;

import com.monopatinmicroservicio.model.*;
import com.monopatinmicroservicio.repository.EstacionRepository;
import com.monopatinmicroservicio.repository.MonopatinRepository;
import com.monopatinmicroservicio.repository.MonopatinViajeRepository;
import com.monopatinmicroservicio.service.DTO.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service("monopatinService")
public class MonopatinService {

    private final MonopatinRepository repository;
    private final EstacionRepository estacionRepository;
    private final MonopatinViajeRepository monopatinViajeRepository;

    public MonopatinService(MonopatinRepository repository, EstacionRepository estacionRepository, MonopatinViajeRepository monopatinViajeRepository) {
        this.repository = repository;
        this.monopatinViajeRepository = monopatinViajeRepository;
        this.estacionRepository = estacionRepository;
    }
    // CRUD
    @Transactional
    public Optional<MonopatinDTO> traerMonopatin(Long id){
        Optional<Monopatin> monopatin = repository.findById(id);
        if (monopatin.isPresent()){
            return monopatin.map(MonopatinDTO::new);
        }
        return null;
    }

    @Transactional
    public Stream<MonopatinDTO> traerMonopatines() {
        return repository.findAll().stream().map(MonopatinDTO::new);
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
    public MonopatinDTO agregarMonopatin(MonopatinDTO monopatin){
        Monopatin savedMonopatin = repository.save(new Monopatin(monopatin));
        return new MonopatinDTO(savedMonopatin);
    }

    // OTRAS
    @Transactional
    public boolean cambiarDisponibilidad(Long id, boolean disponible) {
        Monopatin monopatin = repository.findById(id).orElse(null);

        if (monopatin != null) {
            monopatin.setDisponibilidad(disponible);
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
    public List<MonopatinDTO> traerMonopatinesCercanos(double latitud, double longitud) {
        List<MonopatinDTO> monopatines = repository.traerMonopatinesCercanos(latitud, longitud)
                .stream()
                .map(m->{
                    Monopatin monopatin = (Monopatin) m[0];
                    Double distancia = (Double) m[1];
                    System.out.println("monopatin: " + monopatin + " distancia: " + distancia);
                    return new MonopatinDTO(monopatin);
                })
                .toList();
        return monopatines;
    }


    @Transactional
    public Optional<List<MonopatinMasUsadoDTO>> traerMonopatinesMasUsados(int minCantidadViajes, int anio) {
        List<MonopatinMasUsadoDTO> masUsados = this.monopatinViajeRepository.traerMonopatinesMasUsados(minCantidadViajes, anio);

        if (!masUsados.isEmpty()) {
            return Optional.of(masUsados);
        }

        return Optional.empty();
    }


    @Transactional
    public ReporteEstadoMonopatinesDTO traerReporteEstadoMonopatines() {
        return repository.traerReporteEstadoMonopatines();
    }

    @Transactional
    public EstacionDTO guardarEstacion(Ubicacion ubicacion) {
        Estacion estacion = new Estacion(ubicacion);
        return new EstacionDTO(estacionRepository.save(estacion));
    }

    @Transactional
    public boolean eliminarEstacion(Long id) {
        Optional<Estacion> optionalEstacion = estacionRepository.findById(id);
        if (optionalEstacion.isPresent()) {
            Estacion estacion = optionalEstacion.get();
            estacionRepository.delete(estacion);
            return true;
        }
        return false;
    }

    @Transactional
    public List<ReporteKilometrajeDTO> traerReporteKilometrajeConPausas() {
        return monopatinViajeRepository.traerReporteKilometrajeConPausas().stream().map(m -> {
            ReporteKilometrajeDTO reporteKilometrajeDTO = new ReporteKilometrajeDTO();
            reporteKilometrajeDTO.setKilometros_recorridos((Double) m[0]);
            reporteKilometrajeDTO.setMinutos_uso((BigDecimal) m[1]);
            reporteKilometrajeDTO.setCantidad_viajes(((Long) m[2]));
            return reporteKilometrajeDTO;
        }).toList();
    }

    @Transactional
    public List<ReporteKilometrajeDTO> traerReporteKilometrajeSinPausas() {
        return monopatinViajeRepository.traerReporteKilometrajeSinPausas().stream().map(
                m->{
                    ReporteKilometrajeDTO reporteKilometrajeDTO = new ReporteKilometrajeDTO();
                    reporteKilometrajeDTO.setKilometros_recorridos((Double) m[0]);
                    reporteKilometrajeDTO.setMinutos_uso((BigDecimal) m[1]);
                    reporteKilometrajeDTO.setCantidad_viajes(((Long) m[2]));
                    return reporteKilometrajeDTO;
                }
        ).toList();
    }

    @Transactional
    public List<EstacionDTO> traerEstacionesMasCercanas(double latitud, double longitud) {
        return estacionRepository.traerEstacionesMasCercanas(latitud, longitud);
    }
}
