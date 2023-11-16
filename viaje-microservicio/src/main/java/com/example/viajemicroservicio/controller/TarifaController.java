package com.example.viajemicroservicio.controller;

import com.example.viajemicroservicio.service.DTO.tarifa.TarifaDTORequest;
import com.example.viajemicroservicio.service.DTO.tarifa.TarifaDTOResponse;
import com.example.viajemicroservicio.service.TarifaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("TarifaController")
@RequestMapping("/tarifa")
public class TarifaController {
    private TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerTarifa(@PathVariable Long id) {
        TarifaDTOResponse tarifa = tarifaService.traerTarifa(id);
        if (tarifa != null) {
            return ResponseEntity.ok(tarifa);
        }
        return ResponseEntity.status(404).body("No se encontro la tarifa");
    }

    @GetMapping("")
    public ResponseEntity<?> traerTarifas() {
        List<TarifaDTOResponse> tarifas = tarifaService.traerTarifas();
        if (!tarifaService.traerTarifas().isEmpty()) {
            return ResponseEntity.ok(tarifaService.traerTarifas());
        }
        return ResponseEntity.status(404).body("No se encontraron tarifas");
    }

    /*endpoint de ejemplo: http://localhost:55255/viaje/tarifa FUNCIONA
        body de ejemplo:{
            "nombre": "estudiante",
            "valor_por_segundo": 8.50,
            "fecha_de_alta": "2023-11-12",
            "habilitada": true
        }
    */
    @PostMapping("")
    public ResponseEntity<?> agregarTarifa(@RequestBody TarifaDTORequest tarifa) {
        TarifaDTOResponse tarifaAgregada = tarifaService.agregarTarifa(tarifa);
        if (tarifaAgregada != null) {
            return new ResponseEntity<>(tarifaAgregada, HttpStatus.CREATED);
        }
        return ResponseEntity.status(404).body("Tarifa no agregada");
    }

    @PutMapping("tarifa/{id}")
    //quizas deberia pasar el valor de la tarifa en un JSON
    public ResponseEntity<?> actualizarTarifa(@PathVariable Long id, @RequestBody TarifaDTORequest tarifa) {
        TarifaDTOResponse tarifaActualizada = tarifaService.actualizarTarifa(id, tarifa);
        if (tarifaActualizada != null) {
            return ResponseEntity.ok(tarifaActualizada);
        }
        return ResponseEntity.status(404).body("No se encontro el viaje");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTarifa(@PathVariable Long id) {
        if (tarifaService.eliminarTarifa(id)) {
            return ResponseEntity.ok("Tarifa eliminada");
        }
        return ResponseEntity.status(404).body("No se encontro la tarifa");
    }


}
