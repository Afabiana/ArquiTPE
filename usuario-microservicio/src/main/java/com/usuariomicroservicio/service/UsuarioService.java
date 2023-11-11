package com.usuariomicroservicio.service;

import com.usuariomicroservicio.repository.UsuarioCuentaRepository;
import com.usuariomicroservicio.repository.UsuarioRepository;
import com.usuariomicroservicio.model.Usuario;
import com.usuariomicroservicio.service.DTO.UsuarioDTORequest;
import com.usuariomicroservicio.service.DTO.UsuarioDTOResponse;
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

    public UsuarioDTOResponse traerUsuario(Long id) {
        return repository.findById(id)
                .map(UsuarioDTOResponse::new)
                .orElse(null);
    }

    public Stream<UsuarioDTOResponse> traerTodo() {
        return repository.findAll().stream().map(UsuarioDTOResponse::new);
    }

    public Usuario guardarUsuario(UsuarioDTORequest usuario) {
        return repository.save(new Usuario(usuario));
    }

    public boolean eliminarUsuario(Long id) {
        try {
            repository.deleteById(id);
            return true;
        }catch (Error err){
            return false;
        }
    }
}
