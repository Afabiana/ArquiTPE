package com.monopatinmicroservicio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
public class Estacion implements Serializable {
    //si las ubicaciones no son unicas voy a tener que hacer un join y comparar long y lat
    @Id
    private Long id_estacion;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Ubicacion ubicacion;

    public Estacion() {
    }

    public Estacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}
