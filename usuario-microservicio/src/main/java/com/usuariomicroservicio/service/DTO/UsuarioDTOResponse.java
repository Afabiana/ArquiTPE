package com.usuariomicroservicio.service.DTO;

import com.usuariomicroservicio.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTOResponse implements Serializable{
    private String nombre;
    private String apellido;
    private String email;

    public UsuarioDTOResponse(Usuario usuario) {
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.email = usuario.getEmail();
    }

}
