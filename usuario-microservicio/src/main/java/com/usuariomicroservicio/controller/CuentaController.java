package com.usuariomicroservicio.controller;

import com.usuariomicroservicio.model.Cuenta;
import com.usuariomicroservicio.model.Ubicacion;
import com.usuariomicroservicio.service.CuentaService;
import com.usuariomicroservicio.service.DTO.CuentaDTORequest;
import com.usuariomicroservicio.service.DTO.CuentaDTOResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static java.lang.Long.parseLong;

@RestController("CuentaController")
@RequestMapping("/cuenta")
public class CuentaController {
    private CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    // CRUD
    @GetMapping("")
    public ResponseEntity<?> traerTodasLasCuentas() {
        try{
            Stream<CuentaDTOResponse> cuentasDTO = cuentaService.getAll();
            return new ResponseEntity<>(cuentasDTO, HttpStatus.OK);
        }catch (Error err){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("no se hayan cuentas en el sistema");
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> traerCuentaPorId(@PathVariable Long id) {
        // Long parsedId = parseLong(id);
        try{
            CuentaDTOResponse cuentaDTO = cuentaService.traerPorId(id);
            if (cuentaDTO != null){
                return new ResponseEntity<>(cuentaDTO, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se encontró la cuenta con el ID proporcionado");
        }catch (Error err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("hubo un problema interno");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> agregarCuenta(@RequestBody CuentaDTORequest cuenta) {
        try{
            CuentaDTOResponse cuentaResponse = cuentaService.agregarCuenta(cuenta);
            return new ResponseEntity<>(cuentaResponse, HttpStatus.CREATED);
        }catch (RuntimeException err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(err.getMessage());
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

    /*
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarCuenta(@PathVariable Long id, @RequestBody CuentaDTORequest cuenta){
        try{
            CuentaDTOResponse c = cuentaService.modificarCuenta(new Cuenta(id, cuenta.getFecha_alta(), cuenta.getSaldo()));
            return ResponseEntity.ok(c);
        }catch (Error err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible eliminar la cuenta con id "+id);
        }
    }
    */
    // Funcionalidades extra
    // TODO-monopatin
    @GetMapping("/monopatines")
    public  ResponseEntity<?> getMonopatinesCercanos(@RequestBody Ubicacion ubicacion){
        // TODO - Comunicarse con microservicio de monopatin;
        return null;
    }

    @GetMapping("/saldo/{idCuenta}")
    public ResponseEntity<?> traerSaldo(@PathVariable Long idCuenta){
        Double saldo = cuentaService.traerSaldo(idCuenta);
        if (saldo != null){
            return ResponseEntity.ok(saldo);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario con el id "+idCuenta);
    }
    @PutMapping("cargarSaldo/{id}")
    public ResponseEntity<?> cargarSaldo(@PathVariable Long id, @RequestBody double monto) {
        if (cuentaService.cargarSaldo(id, monto) != null){
            return ResponseEntity.ok("se cargo con exito el saldo");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con el ID proporcionado");
    }

    @PutMapping("descontar/{id}")
    //endpoint de ejemplo: http://localhost:8081/cuenta/descontar/1
    public ResponseEntity<?> cobrarTarifa(@PathVariable Long id, @RequestBody Double monto) {
        // System.out.println("id: " + id);
        if (cuentaService.cobrarTarifa(id, monto) != null){
            return ResponseEntity.ok("se desconto con exito el saldo");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con el ID proporcionado");
    }

    // Habilitar/Deshabilitar cuenta
    @PutMapping("/habilitar/{id}")
    public ResponseEntity<?> habilitarCuenta(@PathVariable Long id) {
        CuentaDTOResponse cuenta = cuentaService.cambiarEstadoCuenta(id, true);
        if (cuenta != null){
            return ResponseEntity.ok(cuenta);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se encontro una cuenta con el id "+id);
    }

    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<?> deshabilitarCuenta(@PathVariable Long id) {
        CuentaDTOResponse cuenta = cuentaService.cambiarEstadoCuenta(id, false);
        if (cuenta != null){
            return ResponseEntity.ok(cuenta);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se encontro una cuenta con el id "+id);
    }



}
