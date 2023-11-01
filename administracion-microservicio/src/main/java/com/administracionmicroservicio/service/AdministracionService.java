package com.administracionmicroservicio.service;

import com.administracionmicroservicio.repository.TarifaRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service("administracionService")
public class AdministracionService {

    private TarifaRepository tarifaRepository;
    private final WebClient webClient;

    public AdministracionService(TarifaRepository tarifaRepository, WebClient.Builder webClientBuilder) {
        this.tarifaRepository = tarifaRepository;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }


    public ResponseEntity<?> anularCuenta(Long id) {

        //le pego al microservicio de usuario para deshabilitar la cuenta
        Mono<String> responseMono =  this.webClient.put()
                .uri("/usuario/deshabilitar/{id}",id)
                .retrieve()// Enviar la solicitud GET y recibir la respuesta como un Mono
                .bodyToMono(String.class);

        String responseString = responseMono.block();
        return ResponseEntity.ok(responseString);
    }

    @Transactional
    public ResponseEntity<?> getMonopatinesMasUsados(int minCantidad, int anio) {
         WebClient.ResponseSpec response = this.webClient.get()
                .uri("/monopatin/masUsados/{cantidad}/{anio}", minCantidad, anio)
                .retrieve()// Enviar la solicitud GET y recibir la respuesta como un Mono

        if (response.
            return ResponseEntity.ok(response.bodyToFlux(String.class).collectList().block());
        }
        return ResponseEntity.ok(monopatinesJson.block());
    }

}
