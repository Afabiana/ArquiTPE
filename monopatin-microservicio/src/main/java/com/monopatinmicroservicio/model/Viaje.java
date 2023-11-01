package com.monopatinmicroservicio.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_viaje;
    //LocalDateTime o Timestamp todavia no sé cual usar
    private LocalDateTime hora_inicio;
    private LocalDateTime hora_fin;
    private Double kms;
    private Long segundosEstacionado;
    @Column
    private Boolean finalizado;
    private Long id_usuario; //necesito al menos una referencia para ir validando
    private Double costoTotal; //cuando viaje finalice voy a setear el costo total
    public Viaje() {
    }

    public Viaje(LocalDateTime hora_inicio, LocalDateTime hora_fin, double kms,
                 boolean finalizado, Long segundosEstacionado, Long id_usuario) {
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.kms = kms;
        this.finalizado = finalizado;
        this.segundosEstacionado = segundosEstacionado;
        this.id_usuario = id_usuario;
        this.costoTotal = 0.0;
    }

    public Long getId_viaje() {
        return id_viaje;
    }

    public LocalDateTime getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(LocalDateTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public LocalDateTime getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(LocalDateTime hora_fin) {
        this.hora_fin = hora_fin;
    }

    public double getKms() {
        return kms;
    }

    public void setKms(double kms) {
        this.kms = kms;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }


    public Long getId_usuario() {
        return id_usuario;
    }

}
