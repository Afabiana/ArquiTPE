package com.monopatinmicroservicio.service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.io.Serializable;
@Data
public class ViajeDTORequest implements Serializable {
    @NotNull(message = "El id del cuenta no puede ser nulo")
    private Long id_cuenta;
    @NotNull(message = "El id del monopatin no puede ser nulo")
    private Long id_monopatin;
    @NotNull(message = "El id de la tarifa no puede ser nulo")
    private Long id_tarifa;

    public ViajeDTORequest() {
    }

    public ViajeDTORequest(Long id_cuenta, Long id_monopatin, Long id_tarifa) {
        this.id_cuenta = id_cuenta;
        this.id_monopatin = id_monopatin;
        this.id_tarifa = id_tarifa;
    }

}
