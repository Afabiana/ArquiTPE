package com.usuariomicroservicio.controller;

import com.usuariomicroservicio.service.DTO.UsuarioDTORequest;
import com.usuariomicroservicio.service.DTO.UsuarioDTOResponse;
import com.usuariomicroservicio.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("UsuarioController")
@RequestMapping("/usuario")
public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // CRUD
    @GetMapping("")
    public ResponseEntity<?> traerUsuarios() {
        try{
            return ResponseEntity.ok(usuarioService.traerTodosUsuarios());
        }catch (Error err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("error interno");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerUsuario(@PathVariable Long id) {
        UsuarioDTOResponse usuario = usuarioService.traerUsuario(id);
        if (usuario != null){
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró un usuario con el id "+ id);
    }

    @PostMapping("")
    public ResponseEntity<?> agregarUsuario(@RequestBody UsuarioDTORequest usuario) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(usuario));
        }catch (Error err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("no fue posible guardar al usuario");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try{
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("se elimino con exito al usuario con id "+id);
        }catch (Error err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("no se pudo eliminar al usuario con id "+id);
        }
    }

    @GetMapping("/saldo={usuario_id}")
    public void traerSaldo(Long id){
        usuarioService.traerSaldo(id);
    }

}
