package com.example.viajemicroservicio.service;

import com.example.viajemicroservicio.model.Tarifa;
import com.example.viajemicroservicio.repository.TarifaRepository;
import com.example.viajemicroservicio.service.DTO.tarifa.TarifaDTORequest;
import com.example.viajemicroservicio.service.DTO.tarifa.TarifaDTOResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifaService {
    private TarifaRepository tarifaRepository;

    public TarifaService(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }


    @Transactional(readOnly = true)
    public TarifaDTOResponse traerTarifa(Long id) {
        return tarifaRepository.findById(id).map(TarifaDTOResponse::new).orElse(null);
    }

    @Transactional
    public TarifaDTOResponse actualizarTarifa(Long id, TarifaDTORequest tarifaDTORequest) {
        return tarifaRepository.findById(id).map(tarifa -> {
            tarifa.setNombre(tarifaDTORequest.getNombre());
            tarifa.setValor_por_segundo(tarifaDTORequest.getValor_por_segundo());
            tarifa.setFecha_habilitacion(tarifaDTORequest.getFecha_de_alta());
            tarifa.setHabilitada(tarifaDTORequest.isHabilitada());
            return new TarifaDTOResponse(tarifaRepository.save(tarifa));
        }).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<TarifaDTOResponse> traerTarifas() {
        return tarifaRepository.findAll().stream().map(TarifaDTOResponse::new).toList();
    }

    @Transactional
    public TarifaDTOResponse agregarTarifa(TarifaDTORequest tarifa) {
        return new TarifaDTOResponse(tarifaRepository.save(new Tarifa(tarifa)));
    }

    @Transactional
    public boolean eliminarTarifa(Long id) {
        try {
            if (tarifaRepository.existsById(id)) {
                tarifaRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Error err) {
            throw err;
        }
    }
}
