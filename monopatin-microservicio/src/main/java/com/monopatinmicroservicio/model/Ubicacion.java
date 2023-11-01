package com.monopatinmicroservicio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Ubicacion {

    @Id @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ubicacion;

    @Getter @Setter
    private double latitud;

    @Getter @Setter
    private double longitud;

    public Ubicacion() {
    }

    public Ubicacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

}
