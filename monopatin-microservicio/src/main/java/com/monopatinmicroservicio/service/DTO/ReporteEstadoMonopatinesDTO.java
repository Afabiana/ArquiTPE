package com.monopatinmicroservicio.service.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteEstadoMonopatinesDTO implements Serializable {
    private int aptos_para_uso;
    private int en_mantenimiento;




}
