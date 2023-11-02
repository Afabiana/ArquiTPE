package com.monopatinmicroservicio.service.DTO;

import com.monopatinmicroservicio.model.Viaje;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViajeDTOResponse implements Serializable {
    private LocalDateTime hora_inicio;
    private LocalDateTime hora_fin;
    private Double kms;
    private Long id_usuario;
    public ViajeDTOResponse(Viaje viaje) {
        this.hora_inicio = viaje.getHora_inicio();
        this.hora_fin = viaje.getHora_fin();
        this.kms = viaje.getKms();
        this.id_usuario = viaje.getId_usuario();
    }
}
