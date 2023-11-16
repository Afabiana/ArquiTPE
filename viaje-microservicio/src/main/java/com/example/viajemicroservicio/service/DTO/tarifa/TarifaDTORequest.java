package com.example.viajemicroservicio.service.DTO.tarifa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifaDTORequest implements Serializable {
    private String nombre;
    private Double valor_por_segundo;
    private LocalDate fecha_de_alta;
    private boolean habilitada;
}
