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

    @Query("""
        SELECT
            new com.monopatinmicroservicio.service.DTO.ReporteEstadoMonopatinesDTO(
            (SELECT COUNT(m) FROM Monopatin m WHERE m.disponibilidad = FALSE),
            (SELECT COUNT(m) FROM Monopatin m WHERE m.disponibilidad = TRUE)
            )
            FROM Monopatin m
        """
    )
    List<ReporteEstadoMonopatinesDTO> traerReporteEstadoMonopatines();

    @Query(value =
        """
            SELECT
                m
            FROM
                Monopatin m
            WHERE
                m.disponibilidad = TRUE
            AND
                ST_Distance(:latitud, :longitud, m.ubicacion.latitud, m.ubicacion.longitud) < :limite
        """
    )
    List<Monopatin> traerMonopatinesCercanos(double latitud, double longitud, double limite);

}
