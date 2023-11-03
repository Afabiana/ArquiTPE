package com.usuariomicroservicio.service;

import com.usuariomicroservicio.model.Cuenta;
import com.usuariomicroservicio.repository.CuentaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.usuariomicroservicio.service.DTO.CuentaDTOResponse;
import com.usuariomicroservicio.service.DTO.CuentaDTORequest;

import java.util.Optional;
import java.util.stream.Stream;


@Service("CuentaService")
public class CuentaService {
    private CuentaRepository repository;

    public CuentaService(CuentaRepository repository) {
        this.repository = repository;
    }

    // public ResponseEntity<?> getAll() {
    //    return new ResponseEntity<>(this.repository.findAll().stream().map(CuentaDTOResponse::new), HttpStatus.OK);
    // }

    public Stream<CuentaDTOResponse> getAll() {
        return this.repository.findAll().stream().map(CuentaDTOResponse::new);
    }

    public CuentaDTOResponse getById(Long id) {
        Optional<Cuenta> optionalCuenta = repository.findById(id);
        if (optionalCuenta.isPresent()) {
            CuentaDTOResponse cuentaDTOResponse = new CuentaDTOResponse(optionalCuenta.get());
            return cuentaDTOResponse;
            //return new ResponseEntity<>(cuentaDTOResponse, HttpStatus.OK);
        }
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con el ID proporcionado");
        return null;
    }

    public Cuenta saveCuenta(CuentaDTORequest cuenta) {
        return (this.repository.save(new Cuenta(cuenta)));
        //return new ResponseEntity<>(repository.save(new Cuenta(cuenta)), HttpStatus.CREATED);
    }

    // public ResponseEntity<?> deleteCuenta(Long id) {
    //    repository.deleteById(id);
    //    return ResponseEntity.ok("se elimino con exito");
    // }

    public Long deleteCuenta(Long id) {
        repository.deleteById(id);
        return id;
    }

    public ResponseEntity<?> topUpSaldo(Long id, double monto) {
        Optional<Cuenta> optionalCuenta = repository.findById(id);
        if (optionalCuenta.isPresent()) {
            Cuenta cuenta = optionalCuenta.get();
            cuenta.setSaldo(cuenta.getSaldo() + monto);
            return new ResponseEntity<> (repository.save(cuenta), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con el ID proporcionado");
    }

    public ResponseEntity<?> chargeTarifa(Long id, double monto) {
        Optional<Cuenta> optionalCuenta = repository.findById(id);
        if (optionalCuenta.isPresent()) {
            Cuenta cuenta = optionalCuenta.get();
            cuenta.setSaldo(cuenta.getSaldo() - monto);
            return new ResponseEntity<> (repository.save(cuenta), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con el ID proporcionado");
    }
}
