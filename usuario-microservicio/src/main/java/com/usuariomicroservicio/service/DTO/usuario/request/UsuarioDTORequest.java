package com.usuariomicroservicio.service.DTO.usuario.request;

import com.usuariomicroservicio.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTORequest implements Serializable{
    private String nombre;
    private String apellido;
    private String nro_celular;
    private String email;
    private Boolean habilitada;


}
