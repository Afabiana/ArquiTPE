package com.administracionmicroservicio.repository;

import com.administracionmicroservicio.model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

}
