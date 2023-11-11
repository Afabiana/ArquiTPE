package com.monopatinmicroservicio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Ubicacion{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ubicacion;

    @Setter
    private double latitud;

    @Setter
    private double longitud;

    public Ubicacion() {
    }

    public Ubicacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String toString() {
        return "Ubicacion(id_ubicacion=" + this.getId_ubicacion() + ", latitud=" + this.getLatitud() + ", longitud=" + this.getLongitud() + ")";
    }
}
