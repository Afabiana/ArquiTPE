package com.monopatinmicroservicio.controller;

import com.monopatinmicroservicio.model.Ubicacion;
import com.monopatinmicroservicio.service.DTO.EstacionDTO;
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
    public ResponseEntity<?> traerMonopatin(@PathVariable Long id) {
        if (monopatinService.traerMonopatin(id).isPresent()) {
            return ResponseEntity.ok(monopatinService.traerMonopatin(id));
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    @GetMapping
    public ResponseEntity<?> traerMonopatines() {
        return ResponseEntity.ok(monopatinService.traerMonopatines());
    }


    @PutMapping("/{id}/ubicacion")
    public ResponseEntity<?> actualizarUbicacion(@PathVariable Long id, @RequestBody Ubicacion ubicacion) {
        if (monopatinService.actualizarUbicacion(id, ubicacion)) {
            return ResponseEntity.ok().body("Se modifico con exito");
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    /**
     * TODO: puedo hacer un DTO de ubi que me traiga los 3 params por body
     *
     * endpoint de ej: http://localhost:55255/monopatin/cercanos?maxdistancia=0.5&latitud=-34.603722&longitud=-58.381592
     */
    @GetMapping("/cercanos")
    public ResponseEntity<?> traerMonopatinesCercanos(@RequestParam(name = "latitud") double latitud,
                                                    @RequestParam(name = "longitud") double longitud) {
        System.out.println("latitud: " + latitud + " longitud: " + longitud );
        return ResponseEntity.ok(monopatinService.traerMonopatinesCercanos(latitud, longitud));
    }

    @GetMapping("/estaciones")
    public ResponseEntity<?> traerEstacionesMasCercanas(@RequestParam(name = "latitud") double latitud,
                                                     @RequestParam(name = "longitud") double longitud){
        return ResponseEntity.ok(monopatinService.traerEstacionesMasCercanas(latitud, longitud));
    }

    //estos metodos requieren permisos de admin o mantenimiento

    //endpoint de ejemplo: http://localhost:55255/monopatin/1
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMonopatin(@PathVariable Long id) {
        if (monopatinService.eliminarMonopatin(id)) {
            return ResponseEntity.ok().body("Monopatin eliminado");
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    //endpoint de ejemplo: http://localhost:55255/monopatin

    /**
     * {
     *     "latitud": -37.33333,
     *     "longitud": 59.1111,
     *     "disponibilidad": false
     * }
     */
    @PostMapping
    public ResponseEntity<?> agregarMonopatin(@RequestBody MonopatinDTO monopatinDTO) {
        MonopatinDTO monopatin = monopatinService.agregarMonopatin(monopatinDTO);
        if (monopatin != null) {
            return ResponseEntity.ok(monopatin);
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    @GetMapping("/masUsados/minCantidadViajes={minCantidadViajes}&anio={anio}")
    public ResponseEntity<?> traerMonopatinesMasUsados(@PathVariable int minCantidadViajes, @PathVariable int anio) {
        return ResponseEntity.ok(monopatinService.traerMonopatinesMasUsados(minCantidadViajes, anio));
    }

    @GetMapping("/reporteEstadoMonopatines")
    public ResponseEntity<?> traerReporteEstadoMonopatines() {
        return ResponseEntity.ok(monopatinService.traerReporteEstadoMonopatines());
    }

    @PostMapping("/estacion")
    public ResponseEntity<?> guardarEstacion(@RequestBody Ubicacion ubicacion) {
        EstacionDTO estacion = monopatinService.guardarEstacion(ubicacion);
        if (estacion != null) {
            return ResponseEntity.ok(estacion);
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    @DeleteMapping("/estacion/{id}")
    public ResponseEntity<?> eliminarEstacion(@PathVariable Long id) {
        if (monopatinService.eliminarEstacion(id)) {
            return ResponseEntity.ok().body("Estacion eliminada");
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    @PutMapping("/mantenimiento/{id}")
    public ResponseEntity<?> mandarAMantenimiento(@PathVariable Long id) {
        if (monopatinService.cambiarDisponibilidad(id, false)) {
            return ResponseEntity.ok().body("Monopatin mandado a mantenimiento");
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    @PutMapping("/habilitar/{id}")
    public ResponseEntity<?> sacarDeMantenimiento(@PathVariable Long id) {
        if (monopatinService.cambiarDisponibilidad(id, true)) {
            return ResponseEntity.ok().body("Monopatin habilitado");
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    @GetMapping("/kilometraje/pausas")
    public ResponseEntity<?> traerReporteKilometraje() {
        return ResponseEntity.ok(monopatinService.traerReporteKilometrajeConPausas());
    }

    @GetMapping("/kilometraje/sin-pausas")
    public ResponseEntity<?> traerReporteKilometrajeSinPausas() {
        return ResponseEntity.ok(monopatinService.traerReporteKilometrajeSinPausas());
    }

    @GetMapping("/kilometraje/total")
    public ResponseEntity<?> traerReporteKilometrajeTotal() {
        return ResponseEntity.ok(monopatinService.traerReporteKilometrajeSinPausas()); //esto es a propocito, porque la consulta es la misma
    }


}

