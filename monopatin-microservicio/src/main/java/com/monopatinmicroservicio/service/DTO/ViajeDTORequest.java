package com.monopatinmicroservicio.service.DTO;

public class ViajeDTORequest {
    private Long id_usuario;
    private Long id_monopatin;

    public ViajeDTORequest() {
    }

    public ViajeDTORequest(Long id_usuario, Long id_monopatin) {
        this.id_usuario = id_usuario;
        this.id_monopatin = id_monopatin;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public Long getId_monopatin() {
        return id_monopatin;
    }
}
