package com.monopatinmicroservicio.repository;

import com.monopatinmicroservicio.model.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {

    @Query("""
        SELECT
          (SELECT COUNT(m) FROM Monopatin m WHERE m.mantenimiento = TRUE) AS monopatinesEnMantenimiento,
          (SELECT COUNT(m) FROM Monopatin m WHERE m.disponibilidad = TRUE) AS monopatinesDisponibles
        FROM Monopatin m
    """)
    List<Object[]> getReporteEstadoMonopatines();

    //ReporteEstadoMonopatinesDTO
}
