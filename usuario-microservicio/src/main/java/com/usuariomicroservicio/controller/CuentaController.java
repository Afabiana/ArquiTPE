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
        try{
            long deletedID = cuentaService.eliminarCuenta(id);
            return ResponseEntity.ok("se elimino con exito la cuenta con id "+ deletedID);
        }catch (Error err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible eliminar la cuenta con id "+id);
        }
    }

    // TODO...
    @PutMapping("cargarSaldo/{id}")
    public ResponseEntity<?> cargarSaldo(@PathVariable Long id, @PathVariable double monto) {
        if (cuentaService.cargarSaldo(id, monto) != null){
            return ResponseEntity.ok("se cargo con exito el saldo");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con el ID proporcionado");
    }

    @PutMapping("descontar/{id}")
    public ResponseEntity<?> cobrarTarifa(@PathVariable Long id, @PathVariable double monto) {
        if (cuentaService.cobrarTarifa(id, monto) != null){
            return ResponseEntity.ok("se desconto con exito el saldo");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con el ID proporcionado");
    }

}
