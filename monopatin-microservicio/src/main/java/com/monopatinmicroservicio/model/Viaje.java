package com.monopatinmicroservicio.model;

import com.monopatinmicroservicio.service.DTO.ViajeDTORequest;
import com.monopatinmicroservicio.service.DTO.ViajeDTOResponse;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_viaje;
    private LocalDateTime hora_inicio;
    private LocalDateTime hora_fin;
    private Double kms;
    private Long segundosEstacionado;
    private Long id_usuario; //necesito al menos una referencia para ir validando
    private Double costoTotal; //cuando viaje finalice voy a setear el costo total
    public Viaje() {
    }

    public Viaje(LocalDateTime hora_inicio,
                 Long id_usuario) {
        this.hora_inicio = hora_inicio;
        this.hora_fin = null;
        this.kms = 0.0;
        this.segundosEstacionado = 0L;
        this.id_usuario = id_usuario;
        this.costoTotal = 0.0;
    }

    public Viaje(ViajeDTORequest dto){
        this.hora_inicio = LocalDateTime.now();
        this.hora_fin = null;
        this.kms = 0.0;
        this.segundosEstacionado = 0L;
        this.id_usuario = dto.getId_usuario();
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

    public Long getId_usuario() {
        return id_usuario;
    }

}
