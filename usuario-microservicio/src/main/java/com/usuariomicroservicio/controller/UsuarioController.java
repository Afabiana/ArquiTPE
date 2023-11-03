package com.usuariomicroservicio.controller;

import com.usuariomicroservicio.service.DTO.UsuarioDTORequest;
import com.usuariomicroservicio.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("UsuarioController")
@RequestMapping("/usuario")
public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PutMapping("/habilitar/{id}")
    public ResponseEntity<?> habilitarCuenta(@PathVariable Long id) {
        return usuarioService.cambiarEstadoCuenta(id, true);
    }

    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<?> deshabilitarCuenta(@PathVariable Long id) {
        return usuarioService.cambiarEstadoCuenta(id, false);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> traerUsuario(@PathVariable Long id) {
        return usuarioService.traerUsuario(id);
    }

    @GetMapping("")
    public ResponseEntity<?> traerUsuarios() {
        return usuarioService.traerTodo();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        return usuarioService.eliminarUsuario(id);
    }

    @PostMapping("")
    public ResponseEntity<?> agregarUsuario(@RequestBody UsuarioDTORequest usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    @GetMapping("/saldo={usuario_id}")
    public void traerSaldo(Long id){
        usuarioService.traerSaldo(id);
    }

}
