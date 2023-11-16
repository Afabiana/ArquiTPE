package com.example.viajemicroservicio.repository;

import com.example.viajemicroservicio.model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    @Query(
        """
            SELECT t
            FROM
                Tarifa t
            WHERE
                t.fecha_habilitacion <= :hoy
                AND
                t.habilitada = true
                AND
                t.nombre = 'extra'
            ORDER BY
                t.fecha_habilitacion DESC
            LIMIT 1
        """
    )
    Tarifa getTarifaExtra(LocalDate hoy);
}
