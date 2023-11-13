package com.usuariomicroservicio.repository;

import com.usuariomicroservicio.model.UsuarioCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioCuentaRepository extends JpaRepository<UsuarioCuenta, Long>{
}
