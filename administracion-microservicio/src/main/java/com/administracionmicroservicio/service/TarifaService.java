package com.administracionmicroservicio.service;

import com.administracionmicroservicio.model.Tarifa;
import com.administracionmicroservicio.repository.TarifaRepository;
import com.administracionmicroservicio.service.DTO.TarifaDTORequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("tarifaService")
public class TarifaService {
    private TarifaRepository tarifaRepository;

    public TarifaService(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }


    public ResponseEntity getAll() {
        return ResponseEntity.ok(tarifaRepository.findAll());
    }

    public ResponseEntity getById(Long id) {
        return ResponseEntity.ok(tarifaRepository.findById(id));
    }

    public ResponseEntity deleteTarifa(Long id) {
        tarifaRepository.deleteById(id);
        return ResponseEntity.ok("Tarifa eliminada");
    }

    public ResponseEntity saveTarifa(TarifaDTORequest tarifa) {
        Tarifa newTarifa = new Tarifa(tarifa.getNombre(), tarifa.getPrecio());
        tarifaRepository.save(newTarifa);
        return ResponseEntity.ok("Tarifa guardada");
    }

    public ResponseEntity updateTarifa(Long id, TarifaDTORequest tarifa) {
        Tarifa tarifaToUpdate = tarifaRepository.findById(id).get();
        tarifaToUpdate.setNombre(tarifa.getNombre());
        tarifaToUpdate.setPrecio(tarifa.getPrecio());
        tarifaRepository.save(tarifaToUpdate);
        return ResponseEntity.ok("Tarifa actualizada");
    }
}
