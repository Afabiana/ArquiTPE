package com.monopatinmicroservicio.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonopatinMasUsadoDTO implements Serializable {
    private Long id_monopatin;
    private long cantidadViajes;
}
