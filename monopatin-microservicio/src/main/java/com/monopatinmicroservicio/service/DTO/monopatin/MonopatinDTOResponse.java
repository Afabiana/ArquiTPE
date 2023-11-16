package com.monopatinmicroservicio.service.DTO.monopatin;

import com.monopatinmicroservicio.model.Monopatin;
import lombok.Data;

import java.io.Serializable;

@Data
public class MonopatinDTOResponse implements Serializable {
    private Long id_monopatin;
    private double latitud;
    private double longitud;
    private String estado;


    public MonopatinDTOResponse(Monopatin m){
        this.id_monopatin = m.getId_monopatin();
        this.latitud = m.getUbicacion().getLatitud();
        this.longitud = m.getUbicacion().getLongitud();
        this.estado = m.getEstado().toString();
    }

}
