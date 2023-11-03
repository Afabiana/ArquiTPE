package com.usuariomicroservicio.controller;

import com.usuariomicroservicio.model.Cuenta;
import com.usuariomicroservicio.service.CuentaService;
import com.usuariomicroservicio.service.DTO.CuentaDTORequest;
import com.usuariomicroservicio.service.DTO.CuentaDTOResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController("CuentaController")
@RequestMapping("/cuenta")
public class CuentaController {
    private CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> getById(@PathVariable Long id) {
        // return cuentaService.getById(id);
    // }
    @GetMapping("")
    public ResponseEntity<?> getCuentas() {
        Stream<CuentaDTOResponse> cuentasDTO = cuentaService.getAll();
        return new ResponseEntity<>(cuentaService.getAll(), HttpStatus.OK);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> getById(@PathVariable Long id) {
    //    return cuentaService.getById(id);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        CuentaDTOResponse cuentaDTO = cuentaService.getById(id);
        if (cuentaDTO != null){
            return new ResponseEntity<>(cuentaDTO, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con el ID proporcionado");
    }

    @PostMapping("")
    public ResponseEntity<?> addCuenta(@RequestBody CuentaDTORequest cuenta) {
        try{
            Cuenta cuentaResponse = cuentaService.saveCuenta(cuenta);
            return new ResponseEntity<>(cuentaResponse, HttpStatus.CREATED);
        }catch (Error err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible agregar una cuenta");
        }
        //return cuentaService.saveCuenta(cuenta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCuenta(@PathVariable Long id) {
        try{
            long deletedID = cuentaService.deleteCuenta(id);
            return ResponseEntity.ok("se elimino con exito la cuenta con id "+ deletedID);
        }catch (Error err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible eliminar la cuenta con id "+id);
        }
    }

    // TODO...
    @PutMapping("cargarSaldo/{id}")
    public ResponseEntity<?> topUpSaldo(@PathVariable Long id, @PathVariable double monto) {
        return cuentaService.topUpSaldo(id, monto);
    }

    @PutMapping("descontar/{id}")
    public ResponseEntity<?> chargeTarifa(@PathVariable Long id, @PathVariable double monto) {
        return cuentaService.chargeTarifa(id, monto);
    }

}
