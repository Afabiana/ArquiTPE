package com.example.viajemicroservicio.service.DTO.viaje;

import com.example.viajemicroservicio.model.Viaje;
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
    private Double valor_por_segundo;
    public ViajeDTOResponse(Viaje viaje) {
        this.hora_inicio = viaje.getHora_inicio();
        this.hora_fin = viaje.getHora_fin();
        this.valor_por_segundo = viaje.getTarifa().getValor_por_segundo();
    }
}
