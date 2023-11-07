package com.monopatinmicroservicio.service;

import com.monopatinmicroservicio.model.Pausa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PausaRepository extends JpaRepository<Pausa, Long> {
}
