package com.usuariomicroservicio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Rol{
    @Id
    private String tipo;

    public Rol() {
    }

    public Rol(String tipo) {
        this.tipo = tipo;
    }


    public String getTipo() {
        return tipo;
    }
}
