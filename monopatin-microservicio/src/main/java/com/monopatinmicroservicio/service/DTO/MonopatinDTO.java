package com.monopatinmicroservicio.service.DTO;

import com.monopatinmicroservicio.model.Monopatin;
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
    private Boolean disponibilidad;

    public MonopatinDTO(Monopatin m){
        this.id_monopatin = m.getId_monopatin();
        this.latitud = m.getUbicacion().getLatitud();
        this.longitud = m.getUbicacion().getLongitud();
        this.disponibilidad = m.isDisponible();
    }
}
