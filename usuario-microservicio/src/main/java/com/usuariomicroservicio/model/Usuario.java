package com.usuariomicroservicio.model;

import com.usuariomicroservicio.service.DTO.usuario.request.UsuarioDTORequest;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    private String nombre;
    private String apellido;
    private String nro_celular;
    private String email;
    private Boolean habilitada;
    @ManyToMany
    @JoinColumn(name = "rol")
    private List<Rol> roles;

    @ManyToMany(mappedBy = "usuarios")
    @JoinColumn(name = "id_cuenta")
    private List<Cuenta> cuentas;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String nro_celular, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nro_celular = nro_celular;
        this.email = email;
        this.habilitada = true;
    }

    public Usuario(UsuarioDTORequest dto){
        this.nombre = dto.getNombre();
        this.apellido = dto.getApellido();
        this.nro_celular = dto.getNro_celular();
        this.email = dto.getEmail();
        this.habilitada = dto.getHabilitada();
    }
    public Long getId_usuario() {
        return id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNro_celular() {
        return nro_celular;
    }

    public void setNro_celular(String nro_celular) {
        this.nro_celular = nro_celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getHabilitada() {
        return habilitada;
    }

    public void setHabilitada(Boolean habilitada) {
        this.habilitada = habilitada;
    }

    public List<Rol> getRoles() {
        return new ArrayList<>(this.roles);
    }

    public void addRoles(Rol rol) {
        this.roles.add(rol);
    }
}
