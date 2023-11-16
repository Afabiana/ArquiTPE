package com.monopatinmicroservicio.controller;

import com.monopatinmicroservicio.model.Ubicacion;
import com.monopatinmicroservicio.service.DTO.estacion.EstacionDTORequest;
import com.monopatinmicroservicio.service.DTO.estacion.EstacionDTOResponse;
import com.monopatinmicroservicio.service.EstacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("estacionController")
@RequestMapping("/estacion")
public class EstacionController {
    private EstacionService estacionService;

    public EstacionController(EstacionService estacionService) {
        this.estacionService = estacionService;
    }

    @GetMapping("/estaciones")
    public ResponseEntity<?> traerEstacionesMasCercanas(@RequestParam(name = "latitud") double latitud,
                                                        @RequestParam(name = "longitud") double longitud){
        return ResponseEntity.ok(estacionService.traerEstacionesMasCercanas(latitud, longitud));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerEstacion(@PathVariable Long id) {
        EstacionDTOResponse estacion = estacionService.traerEstacion(id);
        if (estacion != null) {
            return ResponseEntity.ok(estacion);
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    @GetMapping
    public ResponseEntity<?> traerEstaciones() {
        return ResponseEntity.ok(estacionService.traerEstaciones());
    }

    @PutMapping("/{id}/ubicacion")
    public ResponseEntity<?> actualizarUbicacion(@PathVariable Long id, @RequestBody Ubicacion ubicacion) {
        if (estacionService.actualizarUbicacion(id, ubicacion)) {
            return ResponseEntity.ok().body("Se modifico con exito la ubicacion de la estacion");
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    @PostMapping("/estacion")
    public ResponseEntity<?> guardarEstacion(@RequestBody EstacionDTORequest request) {
        EstacionDTOResponse estacion = estacionService.guardarEstacion(request);
        if (estacion != null) {
            return ResponseEntity.ok(estacion);
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    @DeleteMapping("/estacion/{id}")
    public ResponseEntity<?> eliminarEstacion(@PathVariable Long id) {
        if (estacionService.eliminarEstacion(id)) {
            return ResponseEntity.ok().body("Estacion eliminada");
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

}
