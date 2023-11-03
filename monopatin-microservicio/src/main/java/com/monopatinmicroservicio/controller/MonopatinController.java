package com.monopatinmicroservicio.controller;

import com.monopatinmicroservicio.model.Monopatin;
import com.monopatinmicroservicio.model.Ubicacion;
import com.monopatinmicroservicio.service.DTO.MonopatinDTO;
import com.monopatinmicroservicio.service.MonopatinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/monopatin")
public class MonopatinController {
    private MonopatinService monopatinService;

    public MonopatinController(MonopatinService monopatinService) {
        this.monopatinService = monopatinService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMonopatin(@PathVariable Long id) {
        Optional<MonopatinDTO> monopatin = monopatinService.getMonopatin(id);
        if (monopatin.isPresent()){
            return ResponseEntity.ok(monopatin);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("no se haya un monnopatin con id "+id);
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

    @GetMapping("/monopatinesMasUsados/minCantidadViajes={minCantidadViajes}&anio={anio}")
    public ResponseEntity<?> getMonopatinesMasUsados(@PathVariable int minCantidadViajes, @PathVariable int anio) {
        return monopatinService.getMonopatinesMasUsados(minCantidadViajes, anio);
    }

    @GetMapping("/reporteEstadoMonopatines")
    public ResponseEntity<?> getReporteEstadoMonopatines() {
        return monopatinService.getReporteEstadoMonopatines();
    }

    //tiempo de uso y los kilómetros recorridos
    //tiempo con pausas y sin pausas. habria que hacer un sum de los tiempos de viaje etcetc
    //monopatines cercanos a mi zona, esto seria algo como si la diferencia de long y lat es menor a tanto = cerca
    //buscar monopatines por ubicacion (long y lat)
    //buscar monopatines que necesiten mantenimiento
    //buscar por tiempo de uso
}

