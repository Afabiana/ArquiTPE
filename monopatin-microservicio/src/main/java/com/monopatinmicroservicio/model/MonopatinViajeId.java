package com.monopatinmicroservicio.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class MonopatinViajeId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_monopatin")
    private Monopatin id_monopatin;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_viaje")
    private Viaje id_viaje;

    public MonopatinViajeId() {
    }

    public MonopatinViajeId(Monopatin monopatin, Viaje viaje) {
        this.id_monopatin = monopatin;
        this.id_viaje = viaje;
    }

    public Monopatin getMonopatin() {
        return id_monopatin;
    }

    public Viaje getViaje() {
        return id_viaje;
    }

}
