package com.monopatinmicroservicio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Estacion{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_estacion;
    @Getter @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;

    public Estacion() {
    }

    public Estacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Long getId() {
        return id_estacion;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}
