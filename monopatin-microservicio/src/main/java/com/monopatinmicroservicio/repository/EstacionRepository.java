package com.monopatinmicroservicio.repository;

import com.monopatinmicroservicio.model.Estacion;
import com.monopatinmicroservicio.service.DTO.EstacionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstacionRepository extends JpaRepository<Estacion, Long> {

    @Query(
        """
            SELECT
                new com.monopatinmicroservicio.service.DTO.EstacionDTO(e.id_estacion, e.ubicacion.latitud, e.ubicacion.longitud)
            FROM
                Estacion e
            ORDER BY
                ST_Distance(POINT(e.ubicacion.latitud, e.ubicacion.longitud), POINT(:latitud, :longitud)) ASC            LIMIT 5
        """
    )
    List<EstacionDTO> traerEstacionesMasCercanas(double latitud, double longitud);
}
