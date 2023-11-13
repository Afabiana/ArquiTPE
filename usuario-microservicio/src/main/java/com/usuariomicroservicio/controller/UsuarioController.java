package com.usuariomicroservicio.controller;

import com.usuariomicroservicio.model.Usuario;
import com.usuariomicroservicio.service.DTO.UsuarioDTORequest;
import com.usuariomicroservicio.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.usuariomicroservicio.service.DTO.UsuarioDTORequest;
import com.usuariomicroservicio.service.DTO.UsuarioDTOResponse;

import java.util.Optional;
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
            Usuario addedUsuario = usuarioService.guardarUsuario(usuario);
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
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("no se pudo eliminar al usuario con id "+id);
        }
    }

    @PutMapping("/habilitar/{id}")
    public ResponseEntity<?> habilitarCuenta(@PathVariable Long id) {
        return usuarioService.cambiarEstadoCuenta(id, true);
    }

    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<?> deshabilitarCuenta(@PathVariable Long id) {
        return usuarioService.cambiarEstadoCuenta(id, false);
    }

}
