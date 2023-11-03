package com.monopatinmicroservicio.repository;
import com.monopatinmicroservicio.model.MonopatinViaje;
import com.monopatinmicroservicio.service.DTO.MonopatinMasUsadoDTO;
import com.monopatinmicroservicio.service.DTO.ReporteKilometrajeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface MonopatinViajeRepository extends JpaRepository<MonopatinViaje, Long> {

    @Query(
        """
            SELECT
                new com.monopatinmicroservicio.service.DTO.MonopatinMasUsadoDTO(
                mv.id.id_monopatin.id_monopatin,
                count(v.hora_fin)
                )
            FROM
                Viaje v
            JOIN
                MonopatinViaje mv
            WHERE
                count(v.hora_fin) >= :minCantidadViajes
                AND
                year(v.hora_fin) = :anio
            GROUP BY
                mv.id.id_monopatin
        """)
    List<MonopatinMasUsadoDTO> traerMonopatinesMasUsados(int minCantidadViajes, int anio);


    //se cuenta el tiempo total del viaje incluido el tiempo estacionado
    //como los de mantenimiento necesitan saber cuales hay que arreglar, se incluyen solo los monopatines disponibles
    @Query(
        """
            SELECT
                new com.monopatinmicroservicio.service.DTO.ReporteKilometrajeDTO(
                    SUM(viaje.kms),
                    SUM((viaje.hora_inicio - viaje.hora_fin) / 60),
                    COUNT(viaje.id_viaje)
            )
            FROM
                MonopatinViaje mv
            JOIN
                mv.id.id_viaje viaje
            JOIN
                mv.id.id_monopatin m
            WHERE m.disponibilidad = true
            GROUP BY m.id_monopatin
            ORDER BY sum(viaje.kms) DESC
        """
    )
    List<ReporteKilometrajeDTO> traerReporteKilometrajeConPausas();

    //el tiempo estacionado no se cuenta como tiempo de uso
    @Query(
        """
            SELECT
                new com.monopatinmicroservicio.service.DTO.ReporteKilometrajeDTO(
                    SUM(viaje.kms),
                    SUM(((viaje.hora_inicio - viaje.hora_fin) - viaje.segundosEstacionado) / 60),
                    COUNT(viaje.id_viaje)
                )
            FROM
                MonopatinViaje mv
            JOIN
                mv.id.id_viaje viaje
            JOIN
                mv.id.id_monopatin m
            WHERE m.disponibilidad = true
            GROUP BY m.id_monopatin
            ORDER BY sum(viaje.kms) DESC
        """
    )
    List<ReporteKilometrajeDTO> traerReporteKilometrajeSinPausas();


}
