package com.monopatinmicroservicio.service.DTO.monopatin;

import com.monopatinmicroservicio.model.Monopatin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonopatinDTORequest implements Serializable {
    private Long id_monopatin;
    private double latitud;
    private double longitud;
    private String estado;
    private Long kilometraje;




}
