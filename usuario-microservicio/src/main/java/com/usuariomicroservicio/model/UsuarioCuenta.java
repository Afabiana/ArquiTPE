package com.usuariomicroservicio.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class UsuarioCuenta {
    @EmbeddedId
    private UsuarioCuentaId id;

    public UsuarioCuenta() {
    }

    public UsuarioCuenta(UsuarioCuentaId id) {
        this.id = id;
    }

    public UsuarioCuentaId getId() {
        return id;
    }

}
