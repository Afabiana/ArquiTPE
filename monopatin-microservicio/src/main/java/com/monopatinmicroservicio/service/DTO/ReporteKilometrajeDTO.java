package com.monopatinmicroservicio.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteKilometrajeDTO implements Serializable {
    private Double kilometros_recorridos;
    private BigDecimal minutos_uso;
    private Long cantidad_viajes;

}
