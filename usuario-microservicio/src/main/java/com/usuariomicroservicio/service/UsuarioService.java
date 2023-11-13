package com.usuariomicroservicio.service;

import com.usuariomicroservicio.repository.UsuarioCuentaRepository;
import com.usuariomicroservicio.repository.UsuarioRepository;
import com.usuariomicroservicio.model.Usuario;
import com.usuariomicroservicio.service.DTO.UsuarioDTORequest;
import com.usuariomicroservicio.service.DTO.UsuarioDTOResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UsuarioService {
    private UsuarioRepository repository;
    private UsuarioCuentaRepository usuarioCuentaRepository;

    public UsuarioService(UsuarioRepository repository, UsuarioCuentaRepository usuarioCuentaRepository) {
        this.repository = repository;
        this.usuarioCuentaRepository = usuarioCuentaRepository;
    }

    @Transactional
    public UsuarioDTOResponse traerUsuario(Long id) {
        return repository.findById(id)
                .map(UsuarioDTOResponse::new)
                .orElse(null);
    }

    @Transactional
    public Stream<UsuarioDTOResponse> traerTodo() {
        return repository.findAll().stream().map(UsuarioDTOResponse::new);
    }

    @Transactional
    public Usuario guardarUsuario(UsuarioDTORequest usuario) {
        return repository.save(new Usuario(usuario));
    }

    @Transactional
    public boolean eliminarUsuario(Long id) {
        try {
            repository.deleteById(id);
            return true;
        }catch (Error err){
            return false;
        }
    }

    @Transactional
    public ResponseEntity<?> cambiarEstadoCuenta(Long id, boolean isHabilitada) {
        Optional<Usuario> optionalUsuario = repository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setHabilitada(isHabilitada);
            repository.save(usuario);
            return ResponseEntity.ok().body("Se modifico con exito");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario con el ID proporcionado");
    }
}
