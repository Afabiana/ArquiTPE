package com.monopatinmicroservicio.controller;

import com.monopatinmicroservicio.service.DTO.ViajeDTORequest;
import com.monopatinmicroservicio.service.ViajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("ViajeController")
@RequestMapping("/viaje")
public class ViajeController {
    private ViajeService viajeService;

    public ViajeController(ViajeService viajeService) {
        this.viajeService = viajeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getViaje(Long id){
        return viajeService.getViaje(id);
    }

    @PostMapping("/empezar")
    //aca voy a hacer un dto pero por ahora lo dejo asi
    public ResponseEntity<?> startViaje(ViajeDTORequest viaje){
        return viajeService.startViaje(viaje);
    }

    @GetMapping("/totalFacturado?anio={anio}&mesDesde={mesDesde}&mesHasta={mesHasta}")
    public ResponseEntity<?> getTotalFacturado(@PathVariable int anio, @PathVariable int mesDesde,
                                               @PathVariable int mesHasta) {
        return viajeService.getTotalFacturado(anio, mesDesde, mesHasta);
    }

}
