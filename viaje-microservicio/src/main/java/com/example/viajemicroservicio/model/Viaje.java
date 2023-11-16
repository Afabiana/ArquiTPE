package com.example.viajemicroservicio.model;

import com.example.viajemicroservicio.service.DTO.viaje.ViajeDTORequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_viaje;
    private LocalDateTime hora_inicio;
    private LocalDateTime hora_fin;
    private Long id_cuenta; //no estoy segura de si esto esta mooy bien pero necesito al menos una referencia para cobrar el viaje
    private Double costoTotal; //el costo total se va calculando a medida que se va cobrando el viaje
    private Long id_monopatin;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pausa_id")
    private Pausa pausa;
    //podria existir viajetarifa donde se especifique a que hora se aplico cada tarifa
    @ManyToOne
    @JoinColumn(name = "tarifa_id")
    private Tarifa tarifa;
    @ManyToOne
    @JoinColumn(name = "tarifa_extra_id")
    private Tarifa tarifaExtra;
    /*podria tener la ubicacion de inicio y la de fin entonces puedo tener un estimado de los kms recorridos
    * no seria exacto porque el usuario podria ir haciendo zigzag por ej pero es un estimado
    */
    public Pausa getPausa() {
        return pausa;
    }

    public void setPausa(Pausa pausa) {
        this.pausa = pausa;
    }

    public Viaje() {
    }

    public Viaje(LocalDateTime hora_inicio,
                 Long id_cuenta, Tarifa tarifa) {
        this.hora_inicio = hora_inicio;
        this.hora_fin = null;
        //this.segundosEstacionado = 0L;
        this.id_cuenta = id_cuenta;
        this.costoTotal = 0.0;
        this.pausa = null;
        this.tarifa = tarifa;
        this.tarifaExtra = null;
    }

    public Viaje(ViajeDTORequest dto){
        this.hora_inicio = LocalDateTime.now();
        this.hora_fin = null;
        //this.segundosEstacionado = 0L;
        this.id_cuenta = dto.getId_cuenta();
        this.costoTotal = 0.0;
    }





}
