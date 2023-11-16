package com.monopatinmicroservicio.model;

import com.monopatinmicroservicio.model.enums.EstadoMonopatin;
import com.monopatinmicroservicio.service.DTO.monopatin.MonopatinDTORequest;
import jakarta.persistence.*;
import lombok.ToString;

@ToString
@Entity
public class Monopatin {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id_monopatin;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //cascade para que inserte ubicacion tambien
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;
    @Column
    @Enumerated(EnumType.STRING)
    private EstadoMonopatin estado; //este flag me va a ayudar a saber si esta estacionado
    private Long kilometraje;


    public Monopatin(Ubicacion ubicacion, EstadoMonopatin estado) {
        this.ubicacion = ubicacion;
        this.estado = estado;
    }

    /*
    @TODO: crear dto para request y para response
     */
    public Monopatin(MonopatinDTORequest monopatin) {
        this.ubicacion = new Ubicacion(monopatin.getLatitud(), monopatin.getLongitud());
        this.estado = EstadoMonopatin.valueOf(monopatin.getEstado());
        this.kilometraje = monopatin.getKilometraje();

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
        return this.estado == EstadoMonopatin.APAGADO;
    }

    public void setDisponibilidad (EstadoMonopatin estado) {
        this.estado = estado;
    }

    public boolean estaEstacionado(Ubicacion ubi){
        return this.ubicacion.equals(ubi);
    }


    public EstadoMonopatin getEstado() {
        return estado;
    }

    public Long getKilometraje() {
        return kilometraje;
    }

    public void actualizarKilometraje(Long kilometraje) {
        this.kilometraje += kilometraje;
    }
}
