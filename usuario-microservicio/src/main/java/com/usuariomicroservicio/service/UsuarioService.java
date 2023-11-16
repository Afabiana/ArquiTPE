package com.usuariomicroservicio.service;

import com.usuariomicroservicio.repository.UsuarioRepository;
import com.usuariomicroservicio.model.Usuario;
import com.usuariomicroservicio.service.DTO.usuario.request.UsuarioDTORequest;
import com.usuariomicroservicio.service.DTO.usuario.response.UsuarioDTOResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UsuarioService {
    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
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
    public UsuarioDTOResponse guardarUsuario(UsuarioDTORequest usuario) {
        Usuario usuarioNuevo= repository.save(new Usuario(usuario));
        return new UsuarioDTOResponse(usuarioNuevo);
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
    public boolean cambiarEstadoCuenta(Long id, boolean isHabilitada) {
        Optional<Usuario> optionalUsuario = repository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setHabilitada(isHabilitada);
            repository.save(usuario);
            return true;
        }
        return false;
    }
}
