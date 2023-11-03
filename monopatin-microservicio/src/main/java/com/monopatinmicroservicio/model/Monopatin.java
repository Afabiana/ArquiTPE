package com.monopatinmicroservicio.model;

import com.monopatinmicroservicio.service.DTO.MonopatinDTO;
import jakarta.persistence.*;


@Entity
public class Monopatin {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id_monopatin;
    //seguramente haya ubicaciones repetidas peeero...
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //cascade para que inserte ubicacion tambien
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;
    @Column
    private Boolean disponibilidad; //este flag me va a ayudar a saber si esta estacionado

    public Monopatin(Ubicacion ubicacion, boolean disponibilidad) {
        this.ubicacion = ubicacion;
        this.disponibilidad = disponibilidad;
    }

    public Monopatin(MonopatinDTO monopatin) {
        this.ubicacion = new Ubicacion(monopatin.getLatitud(), monopatin.getLongitud());
        this.disponibilidad = monopatin.getDisponibilidad();
    }

    public Monopatin() {
    }

    public Long getId_monopatin() {
        return id_monopatin;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isDisponible() {
        return disponibilidad;
    }

    public void setDisponibilidad (boolean disponible) {
        this.disponibilidad = disponible;
    }

    public boolean estaEstacionado(Ubicacion ubi){
        return this.ubicacion.equals(ubi);
    }
}
