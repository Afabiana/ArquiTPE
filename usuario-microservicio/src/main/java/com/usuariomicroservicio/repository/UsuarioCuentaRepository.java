package com.usuariomicroservicio.repository;

import com.usuariomicroservicio.model.UsuarioCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioCuentaRepository extends JpaRepository<UsuarioCuenta, Long>{
    @Query("""
            SELECT
                c.saldo
            FROM
                UsuarioCuenta uc
            JOIN
                uc.id.cuenta c
            WHERE
                uc.id.usuario.id_usuario = :id
        """)
    Double getSaldoByUserId(Long id);
}
