package com.monopatinmicroservicio.service.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteEstadoMonopatinesDTO implements Serializable {
    private long aptos_para_uso;
    private long en_mantenimiento;




}
