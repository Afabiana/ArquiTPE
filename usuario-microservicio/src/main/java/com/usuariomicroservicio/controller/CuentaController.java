package com.usuariomicroservicio.controller;

import com.usuariomicroservicio.service.CuentaService;
import com.usuariomicroservicio.service.DTO.CuentaDTORequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("CuentaController")
@RequestMapping("/cuenta")
public class CuentaController {
    private CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

   @GetMapping("")
    public ResponseEntity<?> getCuentas() {
        return cuentaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return cuentaService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<?> addCuenta(@RequestBody CuentaDTORequest cuenta) {
        return cuentaService.saveCuenta(cuenta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCuenta(@PathVariable Long id) {
        return cuentaService.deleteCuenta(id);
    }

    @PutMapping("cargarSaldo/{id}")
    public ResponseEntity<?> topUpSaldo(@PathVariable Long id, @PathVariable double monto) {
        return cuentaService.topUpSaldo(id, monto);
    }

    @PutMapping("descontar/{id}")
    public ResponseEntity<?> chargeTarifa(@PathVariable Long id, @PathVariable double monto) {
        return cuentaService.chargeTarifa(id, monto);
    }

}
