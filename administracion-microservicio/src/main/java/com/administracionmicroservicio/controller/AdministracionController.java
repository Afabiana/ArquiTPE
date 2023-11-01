package com.administracionmicroservicio.controller;

import com.administracionmicroservicio.service.AdministracionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("AdministracionController")
@RequestMapping("/administracion")
public class AdministracionController {
    private AdministracionService administracionService;

    public AdministracionController(AdministracionService administracionService) {
        this.administracionService = administracionService;
    }

    //anular cuentas para inhabilitar el uso momentáneo de la misma.
    @PutMapping("/anularCuenta/{id}")
    public ResponseEntity<?> anularCuenta(@PathVariable Long id) {
        return administracionService.anularCuenta(id);
    }

    //los monopatines con más de X viajes en un cierto año
    @GetMapping("/monopatinesMasUsados/{cantidad}/{anio}")
    public ResponseEntity<?> getMonopatinesMasUsados(@PathVariable int minCantidadViajes, @PathVariable int anio) {
        return administracionService.getMonopatinesMasUsados(minCantidadViajes, anio);
    }

    //total facturado en un rango de meses de cierto año.
    @GetMapping("/totalFacturado?anio={anio}&mesDesde={mesDesde}&mesHasta={mesHasta}")
    public ResponseEntity<?> getTotalFacturado(@PathVariable int anio, @PathVariable int mesDesde, @PathVariable int mesHasta) {
        return administracionService.getTotalFacturado(anio, mesDesde, mesHasta);
    }

    //la cantidad de monopatines actualmente en operación, versus la cantidad de monopatines actualmente en mantenimiento.
    @GetMapping("/reporteEstadoMonopatines")
    public ResponseEntity<?> getReporteEstadoMonopatines() {
        return administracionService.getReporteEstadoMonopatines();
    }

    //un ajuste de precios, y que a partir de cierta fecha el sistema
    //habilite los nuevos precios
    @PutMapping("/ajustarTarifas")
    public ResponseEntity<?> ajustarTarifas(@RequestBody TarifaDTORequest tarifa) {
        return administracionService.ajustarTarifas(tarifa);
    }



}
