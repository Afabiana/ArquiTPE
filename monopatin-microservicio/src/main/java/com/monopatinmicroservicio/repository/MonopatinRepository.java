package com.monopatinmicroservicio.repository;

import com.monopatinmicroservicio.model.Monopatin;
import com.monopatinmicroservicio.service.DTO.MonopatinDTO;
import com.monopatinmicroservicio.service.DTO.ReporteEstadoMonopatinesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {

    @Query(
        """
            SELECT new com.monopatinmicroservicio.service.DTO.ReporteEstadoMonopatinesDTO(
                (SELECT COUNT(m) FROM Monopatin m WHERE m.disponibilidad = TRUE),
                (SELECT COUNT(m) FROM Monopatin m WHERE m.disponibilidad = FALSE)
            )
        """
    )
    ReporteEstadoMonopatinesDTO traerReporteEstadoMonopatines();

    @Query(value =
        """
            SELECT
                m,
                ST_Distance(POINT(:longitud, :latitud), POINT(m.ubicacion.longitud, m.ubicacion.latitud)) AS distancia
            FROM
                Monopatin m
            WHERE
                m.disponibilidad = TRUE
            ORDER BY
                ST_Distance_Sphere(POINT(:longitud, :latitud), POINT(m.ubicacion.longitud, m.ubicacion.latitud)) ASC
        """
    )
    List<Object[]> traerMonopatinesCercanos(double latitud, double longitud);

}
