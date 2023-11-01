package com.monopatinmicroservicio.controller;

import com.monopatinmicroservicio.service.DTO.ViajeDTORequest;
import com.monopatinmicroservicio.service.ViajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
