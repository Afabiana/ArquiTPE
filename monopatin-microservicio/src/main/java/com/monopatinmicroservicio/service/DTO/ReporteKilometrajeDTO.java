package com.monopatinmicroservicio.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteKilometrajeDTO implements Serializable {
    private double kilometros_recorridos;
    private int minutos_uso;
    private Long cantidad_viajes;
}
