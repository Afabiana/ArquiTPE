package com.monopatinmicroservicio.service.DTO.estacion;

import lombok.Data;

import java.io.Serializable;

@Data
public class EstacionDTORequest implements Serializable {
        private String nombre;
        private double latitud;
        private double longitud;


}
