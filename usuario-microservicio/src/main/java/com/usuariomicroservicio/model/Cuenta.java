package com.usuariomicroservicio.model;

import com.usuariomicroservicio.service.DTO.CuentaDTORequest;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cuenta;
    private LocalDate fecha_de_alta;
    private Double saldo;
    private Boolean habilitada;


    public Cuenta() {
    }

    public Cuenta(Long id_cuenta, LocalDate fecha_de_alta, Double saldo) {
        this.id_cuenta = id_cuenta;
        this.fecha_de_alta = fecha_de_alta;
        this.saldo = saldo;
    }

    public Cuenta(LocalDate fecha_de_alta, Double saldo) {
        this.fecha_de_alta = fecha_de_alta;
        this.saldo = saldo;
    }

    public Cuenta(CuentaDTORequest cuenta) {
        this.fecha_de_alta = cuenta.getFecha_alta();
        this.saldo = cuenta.getSaldo();
    }

    public void cargarSaldo(Double monto){
        this.saldo += monto;
    }

    public void descontarSaldo(Double monto){
        this.saldo -= monto;
    }
}
