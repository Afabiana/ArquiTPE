package com.example.viajemicroservicio.model;

import com.example.viajemicroservicio.service.DTO.tarifa.TarifaDTORequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@ToString
@Getter
@Setter
@Entity
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre; //nombre seria el tipo de tarifa
    private Double valor_por_segundo;
    private LocalDate fecha_habilitacion;
    private boolean habilitada;

    public Tarifa() {
    }

    public Tarifa (String nombre, Double valor_por_segundo, LocalDate fecha_de_alta) {
        this.nombre = nombre;
        this.valor_por_segundo = valor_por_segundo;
        this.fecha_habilitacion = fecha_de_alta;
        this.habilitada = true;
    }

    public Tarifa (TarifaDTORequest tarifa) {
        this.nombre = tarifa.getNombre();
        this.valor_por_segundo = tarifa.getValor_por_segundo();
        this.fecha_habilitacion = tarifa.getFecha_de_alta();
        this.habilitada = tarifa.isHabilitada();
    }

}
