package com.monopatinmicroservicio.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifaDTORequest implements Serializable {
    private String nombre;
    private Double precio;

}
