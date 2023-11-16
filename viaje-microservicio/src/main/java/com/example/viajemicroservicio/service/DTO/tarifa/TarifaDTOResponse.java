package com.example.viajemicroservicio.service.DTO.tarifa;

import com.example.viajemicroservicio.model.Tarifa;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TarifaDTOResponse implements Serializable {
    private String nombre;
    private Double valor_por_segundo;
    private LocalDate fecha_de_alta;

    public TarifaDTOResponse(Tarifa tarifa){
        this.nombre = tarifa.getNombre();
        this.valor_por_segundo = tarifa.getValor_por_segundo();
        this.fecha_de_alta = tarifa.getFecha_habilitacion();
    }
}
