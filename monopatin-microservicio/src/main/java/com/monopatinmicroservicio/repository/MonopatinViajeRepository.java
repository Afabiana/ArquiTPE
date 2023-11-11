package com.monopatinmicroservicio.repository;
import com.monopatinmicroservicio.model.Monopatin;
import com.monopatinmicroservicio.model.MonopatinViaje;
import com.monopatinmicroservicio.model.MonopatinViajeId;
import com.monopatinmicroservicio.model.Viaje;
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
                MonopatinViaje mv
            JOIN
                mv.id.id_viaje v
            WHERE
                year(v.hora_fin) = :anio
            GROUP BY
                mv.id.id_monopatin
            HAVING
                count(v.hora_fin) >= :minCantidadViajes
        """)
    List<MonopatinMasUsadoDTO> traerMonopatinesMasUsados(int minCantidadViajes, int anio);


    //se cuenta el tiempo total del viaje incluido el tiempo estacionado
    //como los de mantenimiento necesitan saber cuales hay que arreglar, se incluyen solo los monopatines disponibles
    @Query(value =
        """
            SELECT
                SUM(viaje.kms) AS totalKilometraje,
                SUM(TIMESTAMPDIFF(MINUTE, viaje.hora_inicio, viaje.hora_fin)) AS diferenciaMinutos,
                COUNT(viaje.id_viaje) AS totalViajes
            FROM
                monopatin_viaje mv
            JOIN
                viaje viaje ON mv.id_viaje = viaje.id_viaje
            JOIN
                monopatin m ON mv.id_monopatin = m.id_monopatin
            WHERE
                m.disponibilidad = true
            GROUP BY
                m.id_monopatin
            ORDER BY
                totalKilometraje DESC
        """, nativeQuery = true
    )
    List<Object[]> traerReporteKilometrajeConPausas();

    //el tiempo estacionado no se cuenta como tiempo de uso
    @Query(
        value = """
            SELECT
                    SUM(viaje.kms) as kilometros_recorridos,
                    SUM(
                        TIMESTAMPDIFF(MINUTE, viaje.hora_inicio, viaje.hora_fin) -
                        COALESCE(TIMESTAMPDIFF(MINUTE, pausa.hora_inicio, pausa.hora_fin), 0)
                    ) as minutos_uso,
                    COUNT(viaje.id_viaje) as cantidad_viajes
            FROM
                monopatin_viaje mv
            JOIN
                viaje viaje ON mv.id_viaje = viaje.id_viaje
            LEFT JOIN
                pausa pausa ON viaje.pausa_id = pausa.id
            JOIN
                monopatin m ON mv.id_monopatin = m.id_monopatin
            WHERE
                m.disponibilidad = true
            GROUP BY
                m.id_monopatin
            ORDER BY
                kilometros_recorridos DESC
        """, nativeQuery = true
    )

    List<Object[]> traerReporteKilometrajeSinPausas();

    @Query("""
        SELECT
            mv.id.id_monopatin
        FROM
            MonopatinViaje mv
        WHERE
            mv.id.id_viaje.id_viaje = :id_viaje
    """)
    Monopatin findByViaje(Long id_viaje);
}
