package com.monopatinmicroservicio.controller;

import com.monopatinmicroservicio.service.TarifaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.monopatinmicroservicio.service.DTO.TarifaDTORequest;

@RestController
public class TarifaController {
    private TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    @GetMapping("")
    public ResponseEntity getTarifas() {
        return tarifaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getTarifaById(@PathVariable Long id) {
        return tarifaService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTarifa(@PathVariable Long id) {
        return tarifaService.deleteTarifa(id);
    }

    @PostMapping("")
    public ResponseEntity addTarifa(@RequestBody TarifaDTORequest tarifa) {
        return tarifaService.saveTarifa(tarifa);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTarifa(@PathVariable Long id, @RequestBody TarifaDTORequest tarifa) {
        return tarifaService.updateTarifa(id, tarifa);
    }

}
