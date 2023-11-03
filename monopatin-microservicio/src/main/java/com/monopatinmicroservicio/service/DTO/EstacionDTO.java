package com.monopatinmicroservicio.service.DTO;
import com.monopatinmicroservicio.model.Estacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstacionDTO implements Serializable{
    private Long id;
    private double latitud;
    private double longitud;

    public EstacionDTO(Estacion estacion) {
        this.id = estacion.getId();
        this.latitud = estacion.getUbicacion().getLatitud();
        this.longitud = estacion.getUbicacion().getLongitud();
    }
}
