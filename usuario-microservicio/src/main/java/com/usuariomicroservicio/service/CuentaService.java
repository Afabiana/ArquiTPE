package com.usuariomicroservicio.service;

import com.usuariomicroservicio.model.Cuenta;
import com.usuariomicroservicio.repository.CuentaRepository;
import org.springframework.stereotype.Service;
import com.usuariomicroservicio.service.DTO.cuenta.response.CuentaDTOResponse;
import com.usuariomicroservicio.service.DTO.cuenta.request.CuentaDTORequest;

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
            CuentaDTOResponse cuentaResponse = new CuentaDTOResponse(optionalCuenta.get());
            return cuentaResponse;
        }
        return null;
    }

    public CuentaDTOResponse agregarCuenta(CuentaDTORequest cuenta) {
        return (new CuentaDTOResponse(this.repository.save(new Cuenta(cuenta))));
    }

    public Long eliminarCuenta(Long id) {
        try{
            repository.deleteById(id);
            return id;
        }catch (Error err){
            return null;
        }
    }


    // Saldo
    public Double traerSaldo(Long id){
        Optional<Cuenta> optionalCuenta = repository.findById(id);
        return optionalCuenta.map(Cuenta::getSaldo).orElse(null);
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
