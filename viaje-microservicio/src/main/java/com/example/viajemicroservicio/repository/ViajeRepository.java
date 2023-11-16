package com.example.viajemicroservicio.repository;

import com.example.viajemicroservicio.model.Viaje;
import com.example.viajemicroservicio.service.DTO.monopatin.MonopatinesMasUsadosDTO;
import com.example.viajemicroservicio.service.DTO.monopatin.ReporteTiempoDeUsoDTO;
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
            EXTRACT(month from v.hora_fin) BETWEEN :mesDesde AND :mesHasta
            AND EXTRACT(year from v.hora_fin) = :anio
        """
    )
    //podria hacer un DTO para que devuelva el nombre del mes y el total facturado
    Double traerTotalFacturado(int anio, int mesDesde, int mesHasta);

    @Query(
            """
                SELECT
                    new com.example.viajemicroservicio.service.DTO.monopatin.MonopatinesMasUsadosDTO(
                    v.id_monopatin,
                     :anio,
                     count(v))
                FROM
                    Viaje v
                WHERE
                    EXTRACT(year from v.hora_fin) = :anio
                GROUP BY
                    v.id_viaje
            """
    )
    List<MonopatinesMasUsadosDTO> traerMonopatinesMasUsados(int minCantidadViajes, int anio);

    @Query(value =
            """
                SELECT
                    new com.example.viajemicroservicio.service.DTO.ReporteTiempoDeUsoDTO(
                        v.id_monopatin,
                        SUM(TIMESTAMPDIFF(MINUTE, v.hora_inicio, v.hora_fin))
                    )
                FROM
                    Viaje v
                GROUP BY v.id_monopatin
            """,
            nativeQuery = true
    )
    List<ReporteTiempoDeUsoDTO> traerReporteTiempoDeUsoConPausas();

    @Query(
            value = """
            SELECT
                new com.example.viajemicroservicio.service.DTO.ReporteTiempoDeUsoDTO(
                    viaje.id_monopatin,
                    SUM(
                        TIMESTAMPDIFF(MINUTE, viaje.hora_inicio, viaje.hora_fin) -
                        COALESCE(TIMESTAMPDIFF(MINUTE, pausa.hora_inicio, pausa.hora_fin), 0)
                    )
                )
            FROM
                Viaje viaje
            LEFT JOIN Pausa pausa ON viaje.id_viaje = pausa.id_viaje
            """,
            nativeQuery = true
    )
    List<ReporteTiempoDeUsoDTO> traerReporteTiempoDeUsoSinPausas();
}
