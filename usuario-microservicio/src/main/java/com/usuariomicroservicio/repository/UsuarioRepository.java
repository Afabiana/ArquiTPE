package com.usuariomicroservicio.repository;

import com.usuariomicroservicio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u.saldo FROM Usuario u JOIN WHERE u.id_usuario = ?1")
    Double getSaldoById(Long id);
}
