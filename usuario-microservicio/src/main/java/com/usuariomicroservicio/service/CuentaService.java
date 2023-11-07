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


    public Stream<CuentaDTOResponse> getAll() {
        return this.repository.findAll().stream().map(CuentaDTOResponse::new);
    }

    public CuentaDTOResponse traerPorId(Long id) {
        Optional<Cuenta> optionalCuenta = repository.findById(id);
        if (optionalCuenta.isPresent()) {
            CuentaDTOResponse cuentaDTOResponse = new CuentaDTOResponse(optionalCuenta.get());
            return cuentaDTOResponse;
        }
        return null;
    }

    public CuentaDTOResponse agregarCuenta(CuentaDTORequest cuenta) {
        return (new CuentaDTOResponse(this.repository.save(new Cuenta(cuenta))));
    }

    public Long eliminarCuenta(Long id) {
        repository.deleteById(id);
        return id;
    }

    public Double cargarSaldo(Long id, double monto) {
        Optional<Cuenta> optionalCuenta = repository.findById(id);
        if (optionalCuenta.isPresent()) {
            Cuenta cuenta = optionalCuenta.get();
            cuenta.setSaldo(cuenta.getSaldo() + monto);
            return cuenta.getSaldo();
        }
        return null;
    }

    public Double cobrarTarifa(Long id, Double monto) {
        Optional<Cuenta> optionalCuenta = repository.findById(id);
        if (optionalCuenta.isPresent()) {
            // seteo el saldo de la cuenta y le asigno el nuevo saldo restandole el monto
            Cuenta cuenta = optionalCuenta.get();
            Double saldo = cuenta.getSaldo();
            cuenta.setSaldo(cuenta.getSaldo() - monto);

            if (saldo - monto == cuenta.getSaldo()){
                repository.save(cuenta);
                return cuenta.getSaldo();
            }
        }
        return null;
    }
}
