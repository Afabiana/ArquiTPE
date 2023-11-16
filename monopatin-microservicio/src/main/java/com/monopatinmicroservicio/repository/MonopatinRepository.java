package com.monopatinmicroservicio.repository;

import com.monopatinmicroservicio.model.Monopatin;
import com.monopatinmicroservicio.service.DTO.ReporteEstadoMonopatinesDTO;
import com.monopatinmicroservicio.service.DTO.ReporteKilometrajeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {

    @Query(
        """
            SELECT new com.monopatinmicroservicio.service.DTO.ReporteEstadoMonopatinesDTO(
                (SELECT COUNT(m) FROM Monopatin m WHERE m.estado = 'PRENDIDO' OR m.estado = 'APAGADO'),
                (SELECT COUNT(m) FROM Monopatin m WHERE m.estado = 'EN_MANTENIMIENTO')
            )
        """
    )
    ReporteEstadoMonopatinesDTO traerReporteEstadoMonopatines();

    @Query(value =
        """
            SELECT
                m
            FROM
                Monopatin m
            WHERE
                m.estado = 'APAGADO'
            ORDER BY
                ST_Distance_Sphere(POINT(:longitud, :latitud), POINT(m.ubicacion.longitud, m.ubicacion.latitud)) ASC
        """
    )
    List<Monopatin> traerMonopatinesCercanos(double latitud, double longitud);

    @Query(
        """
            SELECT
                new com.monopatinmicroservicio.service.DTO.ReporteKilometrajeDTO(
                    m.id_monopatin,
                    m.kilometraje
                )
            FROM
                Monopatin m
            ORDER BY
                m.kilometraje DESC
        """
    )
    List<ReporteKilometrajeDTO> traerReporteKilometraje();
}
