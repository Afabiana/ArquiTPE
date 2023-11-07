package com.monopatinmicroservicio.model;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;


import java.io.Serializable;


@Entity
public class MonopatinViaje implements Serializable {
    @EmbeddedId
    private MonopatinViajeId id;

    public MonopatinViaje() {
    }

    public MonopatinViaje(MonopatinViajeId id) {
        this.id = id;
    }

    public MonopatinViaje(Viaje viajeNuevo, Monopatin monopatinNuevo) {
        this.id = new MonopatinViajeId(monopatinNuevo, viajeNuevo);
    }

    public MonopatinViajeId getId() {
        return id;
    }
}
