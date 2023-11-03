package com.monopatinmicroservicio.model;

import com.monopatinmicroservicio.service.DTO.TarifaDTORequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private Double precio;
    private LocalDate fecha_de_alta;
    private boolean habilitada;

    public Tarifa() {
    }

    public Tarifa (String nombre, Double precio, LocalDate fecha_de_alta) {
        this.nombre = nombre;
        this.precio = precio;
        this.habilitada = true;
    }

    public Tarifa (TarifaDTORequest tarifa) {
        this.nombre = tarifa.getNombre();
        this.precio = tarifa.getPrecio();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
