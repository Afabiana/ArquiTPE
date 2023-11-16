package com.usuariomicroservicio.controller;


import com.usuariomicroservicio.service.DTO.usuario.request.UsuarioDTORequest;
import com.usuariomicroservicio.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.usuariomicroservicio.service.DTO.usuario.response.UsuarioDTOResponse;

import java.util.stream.Stream;

@RestController("UsuarioController")
@RequestMapping("/usuario")
public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerUsuario(@PathVariable Long id) {
        UsuarioDTOResponse usuario = usuarioService.traerUsuario(id);
        if(usuario != null){
           return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró el usuario con el id "+id);
    }

    @GetMapping("")
    public ResponseEntity<?> traerUsuarios() {
        Stream<UsuarioDTOResponse> usuarios = usuarioService.traerTodo();

        if (usuarios != null){
            return ResponseEntity.ok(usuarios);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("algo salio mal");
    }

    /**
     * ejemplo json:{
     *     "nombre": "nombre",
     *     "apellido": "apellido",
     *     "nro_celular": "nro_celular",
     *     "email": "email",
     *     "habilitada": true
     * }
     */
    @PostMapping("")
    public ResponseEntity<?> agregarUsuario(@RequestBody UsuarioDTORequest usuario) {
        try{
            UsuarioDTOResponse addedUsuario = usuarioService.guardarUsuario(usuario);
            return ResponseEntity.ok(addedUsuario);
        } catch (Error err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("algo salio mal");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        boolean isEliminado = usuarioService.eliminarUsuario(id);
        if (isEliminado){
            return ResponseEntity.ok("usuario eliminado con éxito");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("no se pudo eliminar al usuario con id "+id);
    }

    @PutMapping("/habilitar/{id}")
    public ResponseEntity<?> habilitarCuenta(@PathVariable Long id) {
       boolean isHabilitado = usuarioService.cambiarEstadoCuenta(id, true);
         if (isHabilitado)
              return ResponseEntity.ok("usuario habilitado con éxito");
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body("no se pudo habilitar al usuario con id "+id);
    }

    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<?> deshabilitarCuenta(@PathVariable Long id){
        boolean isDeshabilitado = usuarioService.cambiarEstadoCuenta(id, false);
        if (isDeshabilitado)
            return ResponseEntity.ok("usuario deshabilitado con éxito");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("no se pudo deshabilitar al usuario con id " + id);
    }
}