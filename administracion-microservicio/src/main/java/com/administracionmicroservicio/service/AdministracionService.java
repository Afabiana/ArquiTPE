package com.administracionmicroservicio.service;

import com.administracionmicroservicio.service.DTO.*;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;

import java.util.List;


@Service("administracionService")
public class AdministracionService {

    private final WebClient webClient;


    public AdministracionService() {
        this.webClient = WebClient.create();
    }


    @Transactional
    @ResponseStatus(HttpStatus.BAD_REQUEST) //va a devolver un 400 si el status es distinto de 200
    public Mono<String> anularCuenta(Long id) {
        //le pego al microservicio de usuario para deshabilitar la cuenta
        Mono<String> responseMono =  this.webClient.put()
                .uri("http://localhost:8081/usuario/deshabilitar/{id}",id)
                .retrieve()// Enviar la solicitud GET y recibir la respuesta como un Mono
                .bodyToMono(String.class);
        return responseMono.map(s -> "se modifico con exito");
    }

    public Flux<MonopatinDTO> getMonopatinesMasUsados(int minCantidad, int anio) {
        return webClient.get()
                .uri("http://localhost:55255/monopatin/monopatinesMasUsados/minCantidadViajes={minCantidadViajes}&anio={anio}", minCantidad, anio)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(), clientResponse -> {
                    return Mono.error(new Exception("Error al obtener los monopatines"));
                })
                .bodyToFlux(MonopatinDTO.class);
                //.collectList();
    }


    public Flux<ReporteEstadoMonopatinesDTO> getReporteEstadoMonopatines() {
        return webClient.get()
                .uri("http://localhost:55255/monopatin/reporteEstadoMonopatines")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(), clientResponse -> {
                    return Mono.error(new Exception("Error al obtener el reporte"));
                })
                .bodyToFlux(ReporteEstadoMonopatinesDTO.class);
    }

    public Flux<MonopatinFacturacionDTO> getTotalFacturado(int anio, int mesDesde, int mesHasta) {
        return webClient.get()
                .uri("http://localhost:55255/viaje/totalFacturado?anio={anio}&mesDesde={mesDesde}&mesHasta={mesHasta}", anio, mesDesde, mesHasta)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(), clientResponse -> {
                    return Mono.error(new Exception("Error al obtener el reporte"));
                })
                .bodyToFlux(MonopatinFacturacionDTO.class);
                //.collectList();
    }


    //si tarifa me la traigo para este microservicio puedo llamar al repo nomas
    public Mono<TarifaDTOResponse> ajustarTarifas(TarifaDTORequest tarifa) {
        return webClient.put()
                .uri("http://localhost:tarifa/ajustarTarifas")
                .bodyValue(tarifa)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(), clientResponse -> {
                    return Mono.error(new Exception("Error al ajustar las tarifas"));
                })
                .bodyToMono(TarifaDTOResponse.class);
    }
}
