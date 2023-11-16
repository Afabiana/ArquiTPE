package com.monopatinmicroservicio.model;

import com.monopatinmicroservicio.service.DTO.estacion.EstacionDTORequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Estacion{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_estacion;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;
    private String nombre;

    public Estacion() {
    }

    public Estacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Estacion(EstacionDTORequest estacionDTORequest) {
        this.nombre = estacionDTORequest.getNombre();
        this.ubicacion = new Ubicacion(estacionDTORequest.getLatitud(), estacionDTORequest.getLongitud());
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }
}
