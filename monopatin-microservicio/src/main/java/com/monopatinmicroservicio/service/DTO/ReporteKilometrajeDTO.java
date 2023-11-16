package com.monopatinmicroservicio.service.DTO;

import com.monopatinmicroservicio.model.Monopatin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteKilometrajeDTO implements Serializable {
    private Long id_monopatin;
    private Long kilometros_recorridos;

    public ReporteKilometrajeDTO(Monopatin m) {
        this.id_monopatin = m.getId_monopatin();
        this.kilometros_recorridos = m.getKilometraje();
    }

}
