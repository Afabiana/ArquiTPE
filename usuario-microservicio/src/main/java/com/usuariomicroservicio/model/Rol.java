package com.usuariomicroservicio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Rol{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rol;
    private String tipo;

    public Rol() {
    }

    public Rol(String tipo) {
        this.tipo = tipo;
    }

    public Long getId_rol() {
        return id_rol;
    }

    public String getNombre() {
        return tipo;
    }
}
