package com.monopatinmicroservicio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

@Getter
@Setter
@Entity
@Table(name = "pausa")
public class Pausa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Time hora_inicio;
    private Time hora_fin;


    public Pausa() {
        this.hora_inicio = null;
        this.hora_fin = null;
    }

    public Pausa(Long id) {
        this.id = id;
    }

    public void iniciarPausa() {
        this.hora_inicio = Time.valueOf(LocalDateTime.now().toLocalTime());
    }

    public void finalizarPausa() {
        this.hora_fin = Time.valueOf(LocalDateTime.now().toLocalTime());
    }


}