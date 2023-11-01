package com.monopatinmicroservicio.repository;

import com.monopatinmicroservicio.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {

}
