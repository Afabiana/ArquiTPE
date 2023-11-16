package com.example.viajemicroservicio.repository;

import com.example.viajemicroservicio.model.Pausa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PausaRepository extends JpaRepository<Pausa, Long> {
}
