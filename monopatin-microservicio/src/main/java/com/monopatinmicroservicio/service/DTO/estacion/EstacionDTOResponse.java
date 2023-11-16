package com.monopatinmicroservicio.service.DTO.estacion;
import com.monopatinmicroservicio.model.Estacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstacionDTOResponse implements Serializable{
    private Long id;
    private String nombre;
    private double latitud;
    private double longitud;

    public EstacionDTOResponse(Estacion estacion) {
        this.id = estacion.getId();
        this.nombre = estacion.getNombre();
        this.latitud = estacion.getUbicacion().getLatitud();
        this.longitud = estacion.getUbicacion().getLongitud();
    }
}
