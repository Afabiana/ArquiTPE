package com.monopatinmicroservicio.controller;

import com.monopatinmicroservicio.model.Ubicacion;
import com.monopatinmicroservicio.service.DTO.MonopatinDTO;
import com.monopatinmicroservicio.service.MonopatinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monopatin")
public class MonopatinController {
    private MonopatinService monopatinService;

    public MonopatinController(MonopatinService monopatinService) {
        this.monopatinService = monopatinService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMonopatin(@PathVariable Long id) {
        return monopatinService.getMonopatin(id);
    }

    @GetMapping
    public ResponseEntity<?> getMonopatines() {
        return monopatinService.getMonopatines();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMonopatin(@PathVariable Long id) {
        return monopatinService.deleteMonopatin(id);
    }

    //esta bien poner estos como put?
    @PutMapping("prender/{id}")
    public ResponseEntity<?> powerOnMonopatin(@PathVariable Long id) {
        return monopatinService.updateDisponibilidad(id, true);
    }

    @PutMapping("apagar/{id}")
    public ResponseEntity<?> powerOffMonopatin(@PathVariable Long id) {
        return monopatinService.updateDisponibilidad(id, false);
    }

    @PutMapping("/{id}/ubicacion")
    public ResponseEntity<?> updateUbicacion(@PathVariable Long id, @RequestBody Ubicacion ubicacion) {
        return monopatinService.updateUbicacion(id, ubicacion);
    }

    @PostMapping
    public ResponseEntity<?> addMonopatin(@RequestBody MonopatinDTO monopatin) {
        return monopatinService.saveMonopatin(monopatin);
    }

    //buscar monopatines disponibles
    //buscar monopatines por ubicacion
    //podria hacer un search generico para buscar por cualquier atributo
    //buscar monopatines que necesiten mantenimiento
    //buscar por tiempo de uso
}

