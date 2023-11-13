package com.usuariomicroservicio.controller;

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

    @GetMapping("")
    public ResponseEntity<?> traerTodasLasCuentas() {
        Stream<CuentaDTOResponse> cuentasDTO = cuentaService.getAll();
        return new ResponseEntity<>(cuentasDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerCuentaPorId(@PathVariable Long id) {
        CuentaDTOResponse cuentaDTO = cuentaService.traerPorId(id);
        if (cuentaDTO != null){
            return new ResponseEntity<>(cuentaDTO, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con el ID proporcionado");
    }

    @PostMapping("")
    public ResponseEntity<?> agregarCuenta(@RequestBody CuentaDTORequest cuenta) {
        try{
            CuentaDTOResponse cuentaResponse = cuentaService.agregarCuenta(cuenta);
            return new ResponseEntity<>(cuentaResponse, HttpStatus.CREATED);
        }catch (Error err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible agregar una cuenta");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable Long id) {
        Long deletedID = cuentaService.eliminarCuenta(id);
        if (deletedID != null){
            return ResponseEntity
                    .ok("se elimino con exito la cuenta con id "+ deletedID);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("algo salio mal");
    }

    // Saldo
    @GetMapping("/saldo/{idCuenta}")
    public ResponseEntity<?> traerSaldo(@PathVariable Long idCuenta){
        Double saldo = cuentaService.traerSaldo(idCuenta);
        if (saldo != null) {
            return ResponseEntity.ok(saldo);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró el usuario con el ID proporcionado");
    }

    @PutMapping("/saldo/{idCuenta}")
    public ResponseEntity<?> cargarSaldo(
            @PathVariable Long idCuenta, @RequestBody double monto
    ){
        Double saldo = cuentaService.cargarSaldo(idCuenta, monto);
        if (saldo != null) {
            return ResponseEntity.ok(saldo);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró el usuario con el id "+idCuenta);
    }

    @PutMapping("descontar/{id}")
    //endpoint de ejemplo: http://localhost:8081/cuenta/descontar/1
    public ResponseEntity<?> cobrarTarifa(
            @PathVariable Long id, @RequestBody double monto
    ) {
        Double saldo = cuentaService.cobrarTarifa(id, monto);
        if (saldo != null){
            return ResponseEntity.ok(saldo);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró la cuenta con el id "+id);
    }


}
