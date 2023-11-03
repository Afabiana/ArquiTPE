package com.monopatinmicroservicio.controller;

import com.monopatinmicroservicio.service.DTO.TarifaDTORequest;
import com.monopatinmicroservicio.service.DTO.ViajeDTORequest;
import com.monopatinmicroservicio.service.ViajeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("ViajeController")
@RequestMapping("/viaje")
public class ViajeController{
    private ViajeService viajeService;

    public ViajeController(ViajeService viajeService) {
        this.viajeService = viajeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerViaje(Long id){
        if (viajeService.traerViaje(id) != null) {
            return ResponseEntity.ok(viajeService.traerViaje(id));
        }
        return ResponseEntity.status(404).body("No se encontro el viaje");
    }


    //TODO: Falta crear el metodo empezarViaje. Estaria bueno hacerlo con webclient

   /* @PostMapping("/empezar")
    public ResponseEntity<?> empezarViaje(ViajeDTORequest viaje){
        return viajeService.empezarViaje(viaje);
    }
    */

    //metodos que necesitarian rol de admin u otro

    @GetMapping("")
    public ResponseEntity<?> traerViajes(){
        return ResponseEntity.ok(viajeService.traerViajes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarViaje(@PathVariable Long id) {
        if (viajeService.eliminarViaje(id)) {
            return ResponseEntity.ok(viajeService.eliminarViaje(id));
        }
        return ResponseEntity.status(404).body("No se encontro el viaje");
    }

    @PutMapping("tarifa/{id}")
    //podria recibir directamente un tarifaDTORequest?
    public ResponseEntity<?> actualizarTarifa(@PathVariable Long id, @RequestBody double tarifa) {
        Double tarifaActualizada = viajeService.actualizarTarifa(id, tarifa);
        if (tarifaActualizada != null) {
            return ResponseEntity.ok(tarifaActualizada);
        }
        return ResponseEntity.status(404).body("No se encontro el viaje");
    }

    @PostMapping("/tarifa")
    public ResponseEntity<?> agregarTarifa(@RequestBody TarifaDTORequest tarifa) {
        Double tarifaAgregada = viajeService.agregarTarifa(tarifa);
        if (tarifaAgregada != null) {
            return new ResponseEntity<>(tarifaAgregada, HttpStatus.CREATED);
        }
        return ResponseEntity.status(404).body("No se encontro el viaje");
    }

    @GetMapping("/totalFacturado?anio={anio}&mesDesde={mesDesde}&mesHasta={mesHasta}")
    public ResponseEntity<?> traerTotalFacturado(@RequestParam int anio, @RequestParam int mesDesde,
                                               @RequestParam int mesHasta) {
        if (viajeService.traerTotalFacturado(anio, mesDesde, mesHasta) != null) {
            return ResponseEntity.ok(viajeService.traerTotalFacturado(anio, mesDesde, mesHasta));
        }
        return ResponseEntity.status(404).body("No se encontro el viaje");
    }








}
