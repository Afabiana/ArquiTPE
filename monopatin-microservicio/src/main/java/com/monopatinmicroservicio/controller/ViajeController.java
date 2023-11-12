package com.monopatinmicroservicio.controller;

import com.monopatinmicroservicio.model.Viaje;
import com.monopatinmicroservicio.service.DTO.TarifaDTORequest;
import com.monopatinmicroservicio.service.DTO.ViajeDTORequest;
import com.monopatinmicroservicio.service.DTO.ViajeDTOResponse;
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
    public ResponseEntity<?> empezarViaje(@RequestBody ViajeDTORequest viajeDTORequest){
       ViajeDTOResponse response = this.viajeService.empezarViaje(viajeDTORequest);
        if (response == null)
            return ResponseEntity.status(404).body("No se pudo iniciar el viaje");
        return ResponseEntity.ok(response);
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

    @PutMapping("tarifa/{id}")
    //quizas deberia pasar el valor de la tarifa en un JSON
    public ResponseEntity<?> actualizarTarifa(@PathVariable Long id, @RequestBody double tarifa) {
        Double tarifaActualizada = viajeService.actualizarTarifa(id, tarifa);
        if (tarifaActualizada != null) {
            return ResponseEntity.ok(tarifaActualizada);
        }
        return ResponseEntity.status(404).body("No se encontro el viaje");
    }

    /*endpoint de ejemplo: http://localhost:55255/viaje/tarifa FUNCIONA
    body de ejemplo:{
        "nombre": "estudiante",
        "valor_por_segundo": 8.50,
        "fecha_de_alta": "2023-11-12",
        "habilitada": true
    }
     */
    @PostMapping("/tarifa")
    public ResponseEntity<?> agregarTarifa(@RequestBody TarifaDTORequest tarifa) {
        Double tarifaAgregada = viajeService.agregarTarifa(tarifa);
        if (tarifaAgregada != null) {
            return new ResponseEntity<>(tarifaAgregada, HttpStatus.CREATED);
        }
        return ResponseEntity.status(404).body("Tarifa no agregada");
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








}
