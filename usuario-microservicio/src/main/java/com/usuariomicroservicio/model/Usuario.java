package com.usuariomicroservicio.model;

import com.usuariomicroservicio.service.DTO.UsuarioDTORequest;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    private String nombre;
    private String apellido;
    private String nro_celular;
    private String email;

    @ManyToMany
    private List<Rol> roles;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String nro_celular, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nro_celular = nro_celular;
        this.email = email;
    }

    public Usuario(UsuarioDTORequest dto){
        this.nombre = dto.getNombre();
        this.apellido = dto.getApellido();
        this.nro_celular = dto.getNro_celular();
        this.email = dto.getEmail();
    }
}
