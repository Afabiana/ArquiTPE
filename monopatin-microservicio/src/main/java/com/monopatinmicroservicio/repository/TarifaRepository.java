package com.monopatinmicroservicio.repository;

import com.monopatinmicroservicio.model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

}
