package com.monopatinmicroservicio.controller;

import com.monopatinmicroservicio.model.Ubicacion;
import com.monopatinmicroservicio.model.enums.EstadoMonopatin;
import com.monopatinmicroservicio.service.DTO.monopatin.MonopatinDTORequest;
import com.monopatinmicroservicio.service.DTO.monopatin.MonopatinDTOResponse;
import com.monopatinmicroservicio.service.MonopatinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/monopatin")
public class MonopatinController {
    private MonopatinService monopatinService;


    public MonopatinController(MonopatinService monopatinService) {
        this.monopatinService = monopatinService;
    }

    //GETTERS
    @GetMapping("/{id}")
    public ResponseEntity<?> traerMonopatin(@PathVariable Long id) {
        Optional<MonopatinDTOResponse> monopatin = monopatinService.traerMonopatin(id);
        if (monopatin.isPresent()) {
            return ResponseEntity.ok(monopatin);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontro el monopatin con id "+id);
    }

    @GetMapping
    public ResponseEntity<?> traerMonopatines() {
        Stream<MonopatinDTOResponse> monopatinStream = monopatinService.traerMonopatines();
        if (monopatinStream.findAny().isPresent()){
            return ResponseEntity.ok(monopatinStream);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("algo salio mal");
    }

    @GetMapping("/cercanos")
    public ResponseEntity<?> traerMonopatinesCercanos(@RequestParam(name = "latitud") double latitud,
                                                      @RequestParam(name = "longitud") double longitud) {
        // TODO - deberia venir por body
        return ResponseEntity.ok(monopatinService.traerMonopatinesCercanos(latitud, longitud));
    }

    //GETTERS - REPORTES
    @GetMapping("/kilometraje/total")
    public ResponseEntity<?> traerReporteKilometrajeTotal() {
        return ResponseEntity.ok(monopatinService.traerReporteKilometraje()); //esto es a proposito, porque la consulta es la misma
    }

    @GetMapping("/reporteEstadoMonopatines")
    public ResponseEntity<?> traerReporteEstadoMonopatines() {
        return ResponseEntity.ok(monopatinService.traerReporteEstadoMonopatines());
    }

    //UNICO POST
    @PostMapping
    public ResponseEntity<?> agregarMonopatin(@RequestBody MonopatinDTORequest monopatinDTO) {
        MonopatinDTOResponse monopatin = monopatinService.agregarMonopatin(monopatinDTO);
        if (monopatin != null) {
            return ResponseEntity.ok(monopatin);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("algo salio mal");
    }

    //PUTTERS
    @PutMapping("/mantenimiento/{id}")
    public ResponseEntity<?> mandarAMantenimiento(@PathVariable Long id) {
        if (monopatinService.cambiarDisponibilidad(id, EstadoMonopatin.EN_MANTENIMIENTO)) {
            return ResponseEntity.ok().body("Monopatin mandado a mantenimiento");
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    @PutMapping("/habilitar/{id}")
    public ResponseEntity<?> sacarDeMantenimiento(@PathVariable Long id) {
        if (monopatinService.cambiarDisponibilidad(id, EstadoMonopatin.APAGADO)) {
            return ResponseEntity.ok().body("Monopatin habilitado");
        }
        return ResponseEntity.status(404).body("No se encontro el monopatin");
    }

    @PutMapping("/{id}/ubicacion")
    public ResponseEntity<?> actualizarUbicacion(@PathVariable Long id, @RequestBody Ubicacion ubicacion) {
        if (monopatinService.actualizarUbicacion(id, ubicacion)) {
            return ResponseEntity.ok().body("Se modifico con exito");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el monopatin");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMonopatin(@PathVariable Long id) {
        if (monopatinService.eliminarMonopatin(id)) {
            return ResponseEntity.ok().body("Monopatin eliminado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el monopatin");
    }

    /**
     * {
     *     "latitud": -37.33333,
     *     "longitud": 59.1111,
     *     "estado": "APAGADO",
     *     "kilometraje": 10
     *
     * }
     */








}

