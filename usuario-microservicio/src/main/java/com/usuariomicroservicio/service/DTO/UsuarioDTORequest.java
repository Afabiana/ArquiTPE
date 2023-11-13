package com.usuariomicroservicio.service.DTO;

import com.usuariomicroservicio.model.Cuenta;
import com.usuariomicroservicio.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTORequest implements Serializable{
    private String nombre;
    private String apellido;
    private String nro_celular;
    private String email;
    private Boolean habilitada;

    public UsuarioDTORequest(Usuario usuario){
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.nro_celular = usuario.getNro_celular();
        this.email = usuario.getEmail();
        this.habilitada = usuario.getHabilitada();
    }
}
