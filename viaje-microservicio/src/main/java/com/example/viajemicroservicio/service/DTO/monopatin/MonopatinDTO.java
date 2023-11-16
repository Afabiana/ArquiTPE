package com.example.viajemicroservicio.service.DTO.monopatin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonopatinDTO implements Serializable {

    private Long id_monopatin;
    double latitud;
    double longitud;
    private String estado;
    private Long minutosDeUso;
    private Long kilometraje;


}
