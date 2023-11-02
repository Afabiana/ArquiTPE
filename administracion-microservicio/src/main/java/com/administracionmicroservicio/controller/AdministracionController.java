package com.administracionmicroservicio.controller;

import com.administracionmicroservicio.service.DTO.*;
import com.administracionmicroservicio.service.AdministracionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("AdministracionController")
@RequestMapping("/administracion")
public class AdministracionController {
    private AdministracionService administracionService;
    //deberia crear un servicio por cada microservicio que necesite consumir?

    public AdministracionController(AdministracionService administracionService) {
        this.administracionService = administracionService;
    }

    //anular cuentas para inhabilitar el uso momentáneo de la misma.
    //endpoint http://localhost:8080/administracion/deshabilitarCuenta/3
    @PutMapping("/deshabilitarCuenta/{id}")
    public Mono<String> anularCuenta(@PathVariable Long id) {
        System.out.println("llego a deshabilitar cuenta");
        return administracionService.anularCuenta(id);
    }

    //los monopatines con más de X viajes en un cierto año
    @GetMapping("/monopatinesMasUsados/minCantidadViajes={minCantidadViajes}&anio={anio}")
    public Flux<MonopatinDTO> getMonopatinesMasUsados(@PathVariable int minCantidadViajes, @PathVariable int anio) {
        return administracionService.getMonopatinesMasUsados(minCantidadViajes, anio);
    }

    //total facturado en un rango de meses de cierto año.
    @GetMapping("/totalFacturado?anio={anio}&mesDesde={mesDesde}&mesHasta={mesHasta}")
    public Flux<MonopatinFacturacionDTO> getTotalFacturado(@PathVariable int anio, @PathVariable int mesDesde, @PathVariable int mesHasta) {
        return administracionService.getTotalFacturado(anio, mesDesde, mesHasta);
    }

    //la cantidad de monopatines actualmente en operación, versus la cantidad de monopatines actualmente en mantenimiento.
    @GetMapping("/reporteEstadoMonopatines")
    public Flux<ReporteEstadoMonopatinesDTO> getReporteEstadoMonopatines() {
        return administracionService.getReporteEstadoMonopatines();
    }

    //un ajuste de precios, y que a partir de cierta fecha el sistema
    //habilite los nuevos precios
    @PutMapping("/ajustarTarifas")
    public Mono<TarifaDTOResponse> ajustarTarifas(@RequestBody TarifaDTORequest tarifa) {
        return administracionService.ajustarTarifas(tarifa);
    }

}
