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
    public ResponseEntity<?> disableCuenta(@PathVariable Long id) {
        return usuarioService.disableCuenta(id, true);
    }

    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<?> enableCuenta(@PathVariable Long id) {
        return usuarioService.disableCuenta(id, false);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable Long id) {
        return usuarioService.getUsuario(id);
    }

    @GetMapping("")
    public ResponseEntity<?> getUsuarios() {
        return usuarioService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        return usuarioService.deleteUsuario(id);
    }

    @PostMapping("")
    public ResponseEntity<?> addUsuario(@RequestBody UsuarioDTORequest usuario) {
        return usuarioService.saveUsuario(usuario);
    }

    @GetMapping("/saldo={usuario_id}")
    public void getSaldo(Long id){
        usuarioService.getSaldo(id);
    }

}
