package com.usuariomicroservicio.repository;

import com.usuariomicroservicio.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

}
