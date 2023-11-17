package com.monopatinmicroservicio.model;

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
    private boolean prendido; //este flag me va a ayudar a saber si esta estacionado
    private boolean enMantenimiento;
    private Long kilometraje;

    public boolean isPrendido() {
        return prendido;
    }

    public void setPrendido(boolean prendido) {
        this.prendido = prendido;
    }

    public boolean isEnMantenimiento() {
        return enMantenimiento;
    }

    public void setEnMantenimiento(boolean enMantenimiento) {
        this.enMantenimiento = enMantenimiento;
    }

    public Monopatin(Ubicacion ubicacion, Long kilometraje) {
        this.ubicacion = ubicacion;
        this.prendido = false;
        this.enMantenimiento = false;
        this.kilometraje = kilometraje;
    }

    /*
    @TODO: crear dto para request y para response
     */
    public Monopatin(MonopatinDTORequest monopatin) {
        this.ubicacion = new Ubicacion(monopatin.getLatitud(), monopatin.getLongitud());
        this.prendido = false;
        this.enMantenimiento = false;
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
        return this.enMantenimiento == false && this.prendido == false;
    }


    public boolean estaEstacionado(Ubicacion ubi){
        return this.ubicacion.equals(ubi);
    }


    public Long getKilometraje() {
        return kilometraje;
    }

    public void actualizarKilometraje(Long kilometraje) {
        this.kilometraje += kilometraje;
    }
}
