package com.usuariomicroservicio.service;

import com.usuariomicroservicio.model.Cuenta;
import com.usuariomicroservicio.model.Ubicacion;
import com.usuariomicroservicio.model.Usuario;
import com.usuariomicroservicio.repository.CuentaRepository;
import com.usuariomicroservicio.repository.UsuarioCuentaRepository;
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
    private UsuarioCuentaRepository usuarioCuentaRepository;


    public CuentaService(CuentaRepository repository, UsuarioCuentaRepository usuarioCuentaRepository) {
        this.repository = repository;
        this.usuarioCuentaRepository = usuarioCuentaRepository;

    }


    public Stream<CuentaDTOResponse> getAll() {
        return this.repository.findAll().stream().map(CuentaDTOResponse::new);
    }

    // CRUD
    public CuentaDTOResponse traerPorId(Long id) {
        Optional<Cuenta> optionalCuenta = repository.findById(id);
        if (optionalCuenta.isPresent()) {
            CuentaDTOResponse cuentaDTOResponse = new CuentaDTOResponse(optionalCuenta.get());
            return cuentaDTOResponse;
        }
        return null;
    }

    public CuentaDTOResponse agregarCuenta(CuentaDTORequest cuenta) {
        if (cuenta.getSaldo() == null || cuenta.getFecha_alta() == null){
            throw new RuntimeException("hay campos incompletos");
        }
        return (new CuentaDTOResponse(this.repository.save(new Cuenta(cuenta))));
    }

    public Long eliminarCuenta(Long id) {
        repository.deleteById(id);
        return id;
    }

    /*
    public CuentaDTOResponse modificarCuenta(Cuenta newCuenta){
        Long idCuenta = newCuenta.getId_cuenta();
        Optional<Cuenta> optionalCuenta = repository.findById(idCuenta);

        if (optionalCuenta.isPresent()){
            Cuenta cuenta = optionalCuenta.get();
            cuenta.setSaldo(newCuenta.getSaldo());
            cuenta.setFecha_de_alta(newCuenta.getFecha_de_alta());

            return (new CuentaDTOResponse(this.repository.save(cuenta)));
        }

        throw new RuntimeException("no se haya una cuenta con el id "+ idCuenta);
    }
    */

    // Funcionalidades extra
    public Stream<?> getAllMonopatinesByZona(Ubicacion ubicacion){
        // TODO - Comunicarse con microservicio de monopatin;
        return null;
    }
    // Saldo
    public Double traerSaldo(Long id) {
        Optional<Cuenta> optionalCuenta = repository.findById(id);
        if (optionalCuenta.isPresent()) {
            return usuarioCuentaRepository.getSaldoByUserId(id);
        }
        return null;
    }
    public Double cargarSaldo(Long id, double monto) {
        Optional<Cuenta> optionalCuenta = repository.findById(id);
        if (optionalCuenta.isPresent()) {
            Cuenta cuenta = optionalCuenta.get();

            cuenta.setSaldo(cuenta.getSaldo() + monto);
            repository.save(cuenta);

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
            cuenta.setSaldo(saldo - monto);

            if (saldo - monto == cuenta.getSaldo()){
                repository.save(cuenta);
                return cuenta.getSaldo();
            }
        }
        return null;
    }

    // Habilitar/Deshabilitar
    public CuentaDTOResponse cambiarEstadoCuenta(Long id, boolean isHabilitada) {
        Optional<Cuenta> optionalCuenta = repository.findById(id);
        if (optionalCuenta.isPresent()) {
            Cuenta cuenta = optionalCuenta.get();
            cuenta.setHabilitada(isHabilitada);
            return (new CuentaDTOResponse(this.repository.save(cuenta)));
        }
        return null;
    }
}
