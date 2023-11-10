package com.usuariomicroservicio.service.DTO;
import com.usuariomicroservicio.model.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDTORequest implements Serializable {
    private Long id_cuenta;
    private LocalDate fecha_alta;
    private Double saldo;
    private Boolean habilitada;

    public CuentaDTORequest(Cuenta cuenta){
        this.id_cuenta = cuenta.getId_cuenta();
        this.fecha_alta = cuenta.getFecha_de_alta();
        this.saldo = cuenta.getSaldo();
        this.habilitada = cuenta.getHabilitada();
    }
}
