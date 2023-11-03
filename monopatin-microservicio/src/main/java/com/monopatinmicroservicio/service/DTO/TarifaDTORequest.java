package com.monopatinmicroservicio.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifaDTORequest implements Serializable {
    private String nombre;
    private Double precio;
    private LocalDate fecha_de_alta;
    private boolean habilitada;
}
