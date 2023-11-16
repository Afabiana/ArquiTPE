package com.usuariomicroservicio.model;

import com.usuariomicroservicio.service.DTO.cuenta.request.CuentaDTORequest;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cuenta;
    private LocalDate fecha_de_alta;
    private Double saldo;
    @ManyToMany(mappedBy = "cuentas")
    @JoinColumn(name = "id_usuario")
    private List<Usuario> usuarios;

    public Cuenta() {
    }

    public Cuenta(LocalDate fecha_de_alta, Double saldo) {
        this.fecha_de_alta = fecha_de_alta;
        this.saldo = saldo;
    }

    public Cuenta(CuentaDTORequest cuenta) {
        this.fecha_de_alta = cuenta.getFecha_alta();
        this.saldo = cuenta.getSaldo();
    }

    public Long getId_cuenta() {
        return id_cuenta;
    }

    public LocalDate getFecha_de_alta() {
        return fecha_de_alta;
    }

    public void setFecha_de_alta(LocalDate fecha_de_alta) {
        this.fecha_de_alta = fecha_de_alta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void cargarSaldo(Double monto){
        this.saldo += monto;
    }

    public void descontarSaldo(Double monto){
        this.saldo -= monto;
    }
}
