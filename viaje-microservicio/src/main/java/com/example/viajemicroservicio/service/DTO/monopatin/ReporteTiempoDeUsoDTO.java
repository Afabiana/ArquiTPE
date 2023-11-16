package com.example.viajemicroservicio.service.DTO.monopatin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteTiempoDeUsoDTO implements Serializable {
    private Long id_monopatin;
    private Long minutos_uso;



}
