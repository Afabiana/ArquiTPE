package com.usuariomicroservicio.service.DTO;

import com.usuariomicroservicio.model.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaDTOResponse implements Serializable {
    private Long id_cuenta;
    private Double saldo;

    public CuentaDTOResponse(Cuenta cuenta){
        this.id_cuenta = cuenta.getId_cuenta();
        this.saldo = cuenta.getSaldo();
    }
}
