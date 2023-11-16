package com.example.viajemicroservicio.service.DTO.monopatin;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class MonopatinesMasUsadosDTO implements Serializable {
    private Long id_monopatin;
    private int anio;
    private BigInteger cantidad;
}
