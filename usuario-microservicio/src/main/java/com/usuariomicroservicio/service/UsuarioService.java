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

    public ResponseEntity<UsuarioDTOResponse> traerUsuario(Long id) {
        return ResponseEntity.ok(repository.findById(id)
                .map(UsuarioDTOResponse::new)
                .orElse(null));
    }

    public ResponseEntity<Stream<UsuarioDTOResponse>> traerTodo() {
        return ResponseEntity.ok(repository.findAll().stream().map(UsuarioDTOResponse::new));
    }

    //aca si dejamos que devuelva el usuario para que se vea si todos los atributos estan bien
    public ResponseEntity<?> guardarUsuario(UsuarioDTORequest usuario) {
        return new ResponseEntity<>(repository.save(new Usuario(usuario)), HttpStatus.CREATED);
    }

    public ResponseEntity<?> eliminarUsuario(Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("se elimino con exito");
    }

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


    public ResponseEntity<?> traerSaldo(Long id) {
        Optional<Usuario> optionalUsuario = repository.findById(id);
        if (optionalUsuario.isPresent()) {
            return new ResponseEntity<>(usuarioCuentaRepository.getSaldoByUserId(id), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario con el ID proporcionado");
    }
}
