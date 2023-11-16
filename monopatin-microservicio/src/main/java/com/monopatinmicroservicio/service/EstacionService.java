package com.monopatinmicroservicio.service;

import com.monopatinmicroservicio.model.Estacion;
import com.monopatinmicroservicio.model.Ubicacion;
import com.monopatinmicroservicio.repository.EstacionRepository;
import com.monopatinmicroservicio.service.DTO.estacion.EstacionDTORequest;
import com.monopatinmicroservicio.service.DTO.estacion.EstacionDTOResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("estacionService")
public class EstacionService {
    private EstacionRepository estacionRepository;

    public EstacionService(EstacionRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
    }

    @Transactional
    public EstacionDTOResponse guardarEstacion(EstacionDTORequest request) {
        Estacion estacion = new Estacion(request);
        return new EstacionDTOResponse(estacionRepository.save(estacion));
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

    @Transactional(readOnly = true)
    public List<EstacionDTOResponse> traerEstacionesMasCercanas(double latitud, double longitud) {
        return estacionRepository.traerEstacionesMasCercanas(latitud, longitud);
    }

    public boolean actualizarUbicacion(Long id, Ubicacion ubicacion) {
        Optional<Estacion> optionalEstacion = estacionRepository.findById(id);
        if (optionalEstacion.isPresent()) {
            Estacion estacion = optionalEstacion.get();
            estacion.setUbicacion(ubicacion);
            estacionRepository.save(estacion);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<EstacionDTOResponse> traerEstaciones() {
        return this.estacionRepository.findAll().stream().map(EstacionDTOResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public EstacionDTOResponse traerEstacion(Long id) {
        Optional<Estacion> estacion = estacionRepository.findById(id);
        if (estacion.isPresent()){
            return new EstacionDTOResponse(estacion.get());
        }
        return null;
    }
}
