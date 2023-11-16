package com.example.viajemicroservicio.controller;

import com.example.viajemicroservicio.service.DTO.viaje.ViajeDTORequest;
import com.example.viajemicroservicio.service.DTO.viaje.ViajeDTOResponse;
import com.example.viajemicroservicio.service.ViajeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController("ViajeController")
@RequestMapping("/viaje")
public class ViajeController{
    private ViajeService viajeService;

    public ViajeController(ViajeService viajeService) {
        this.viajeService = viajeService;
    }


    //endpoint de ejemplo: http://localhost:55255/viaje/1
    @GetMapping("/{id}")
    public ResponseEntity<?> traerViaje(@PathVariable Long id){
        if (!viajeService.traerViaje(id).isEmpty()) {
            return ResponseEntity.ok(viajeService.traerViaje(id));
        }
        return ResponseEntity.status(404).body("No se encontro el viaje");
    }


    //endpoint de ejemplo: http://localhost:55255/viaje/empezar
    //body de ejemplo:
   /*{
    "id_monopatin": 1,
    "id_usuario": 1,
    "id_tarifa": 1
    }
   * */
   @PostMapping("/empezar")
    public Mono<ResponseEntity<ViajeDTOResponse>> empezarViaje(@RequestBody ViajeDTORequest viajeDTORequest){
       return this.viajeService.empezarViaje(viajeDTORequest)
               .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    //endpoint de ejemplo http://localhost:55255/viaje/pausar/1
    @PutMapping("/pausar/{id_viaje}")
    public ResponseEntity<?> pausarViaje(@PathVariable Long id_viaje){
        return ResponseEntity.ok(viajeService.pausarViaje(id_viaje));
    }

    @PutMapping("/reanudar/{id_viaje}")
    //endpoint de ejemplo http://localhost:55255/viaje/reanudar/1
    public ResponseEntity reanudarViaje(@PathVariable Long id_viaje){
        return ResponseEntity.ok(viajeService.reanudarViaje(id_viaje));
    }

    @PutMapping("/terminar/{id}")
    public ResponseEntity<?> terminarViaje(@PathVariable Long id){
        return ResponseEntity.ok(viajeService.terminarViaje(id));
    }

    //metodos que necesitarian rol de admin u otro

    @GetMapping("")
    public ResponseEntity<?> traerViajes(){
        return ResponseEntity.ok(viajeService.traerViajes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarViaje(@PathVariable Long id) {
        if (viajeService.eliminarViaje(id)) {
            return ResponseEntity.ok("Viaje eliminado");
        }
        return ResponseEntity.status(404).body("No se encontro el viaje");
    }


    //endpoint de ejemplo: http://localhost:55255/viaje/totalFacturado?anio=2023&mesDesde=6&mesHasta=12
    @GetMapping("/totalFacturado")
    public ResponseEntity<?> traerTotalFacturado(@RequestParam int anio, @RequestParam int mesDesde,
                                               @RequestParam int mesHasta) {
        if (viajeService.traerTotalFacturado(anio, mesDesde, mesHasta) != null) {
            return ResponseEntity.ok(viajeService.traerTotalFacturado(anio, mesDesde, mesHasta));
        }
        return ResponseEntity.status(404).body("Algo salio mal");
    }

    @GetMapping("/monopatines-mas-usados")
    public ResponseEntity<?> traerMonopatinesMasUsados(@RequestParam int minCantidadViajes, @RequestParam int anio) {
        if (viajeService.traerMonopatinesMasUsados(minCantidadViajes, anio) != null) {
            return ResponseEntity.ok(viajeService.traerMonopatinesMasUsados(minCantidadViajes, anio));
        }
        return ResponseEntity.status(404).body("Algo salio mal");
    }

    @GetMapping("/reporte-tiempo-uso")
    public ResponseEntity<?> traerReporteTiempoDeUsoConPausas() {
        if (viajeService.traerReporteTiempoDeUsoConPausas() != null) {
            return ResponseEntity.ok(viajeService.traerReporteTiempoDeUsoConPausas());
        }
        return ResponseEntity.status(404).body("Algo salio mal");
    }

    @GetMapping("/reporte-tiempo-uso-sin-pausas")
    public ResponseEntity<?> traerReporteTiempoDeUsoSinPausas() {
        if (viajeService.traerReporteTiempoDeUsoSinPausas() != null) {
            return ResponseEntity.ok(viajeService.traerReporteTiempoDeUsoSinPausas());
        }
        return ResponseEntity.status(404).body("Algo salio mal");
    }





}
