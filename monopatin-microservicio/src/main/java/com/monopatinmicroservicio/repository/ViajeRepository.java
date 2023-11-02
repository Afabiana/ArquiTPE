package com.monopatinmicroservicio.repository;

import com.monopatinmicroservicio.model.Monopatin;
import com.monopatinmicroservicio.model.Viaje;
import com.monopatinmicroservicio.service.DTO.MonopatinMasUsadoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    @Query("""
        SELECT
            sum(v.costoTotal)
        FROM
            Viaje v
        WHERE
            EXTRACT(month from v.hora_fin) BETWEEN :fechaDesde AND :fechaHasta
            AND EXTRACT(year from v.hora_fin) = :anio
        """)
    //podria hacer un DTO para que devuelva el nombre del mes y el total facturado
    Double getTotalFacturado(int anio, int mesDesde, int mesHasta);

}
