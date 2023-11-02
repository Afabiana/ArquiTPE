package com.monopatinmicroservicio.repository;
import com.monopatinmicroservicio.model.MonopatinViaje;
import com.monopatinmicroservicio.service.DTO.MonopatinMasUsadoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MonopatinViajeRepository extends JpaRepository<MonopatinViaje, Long> {

    @Query("""
            SELECT
                mv.id.id_monopatin, count(v.hora_fin) as cantidadViajes
            FROM
                Viaje v
            JOIN
                MonopatinViaje mv
            WHERE
                cantidadViajes >= :minCantidadViajes
                AND
                year(v.hora_fin) = :anio
            GROUP BY
                mv.id.id_monopatin
            """)
    List<Object[]> getMonopatinesMasUsados(int minCantidadViajes, int anio);

}
