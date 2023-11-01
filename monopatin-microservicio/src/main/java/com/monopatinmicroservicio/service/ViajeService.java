package com.monopatinmicroservicio.service;

import com.monopatinmicroservicio.model.Monopatin;
import com.monopatinmicroservicio.model.MonopatinViaje;
import com.monopatinmicroservicio.model.Viaje;
import com.monopatinmicroservicio.repository.ViajeRepository;
import com.monopatinmicroservicio.service.DTO.ViajeDTORequest;
import com.monopatinmicroservicio.service.DTO.ViajeDTOResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.time.LocalDateTime;
import java.util.Optional;

@Service("ViajeService")
public class ViajeService {
    private WebClient webClient;
    private ViajeRepository repository;

    public ViajeService(WebClient.Builder webClientBuilder, ViajeRepository viajeRepository) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:55255/viaje").build();
        this.repository = viajeRepository;
    }



    public ResponseEntity<?> getViaje(Long id) {
        Optional<Viaje> optionalViaje = this.repository.findById(id);
        if (optionalViaje.isPresent()){
            Viaje viaje = optionalViaje.get();
            return new ResponseEntity<>(new ViajeDTOResponse(viaje), HttpStatus.OK);
        }
        return ResponseEntity.ok("viaje");
    }


    //TODO: manejar excepciones y codigo de respuesta
    //seria como matricularEstudiante. bala. Vamos a tener que hacer un controller de viajemonopatin
    public ResponseEntity<?> startViaje(ViajeDTORequest viaje) {
        // Creo un HttpHeaders para configurar las cabeceras de la solicitud
        // y le digo que nos vamos a comunicar mediante JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //
        Mono<Double> saldo = this.webClient.get()
                .uri("/medioDePago/saldo/{id}", viaje.getId_usuario())
                .retrieve()// Enviar la solicitud GET y recibir la respuesta como un Mono
                .bodyToMono(Double.class);

        saldo.subscribe()
        if (saldo.block() < 0) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No tiene saldo suficiente");}

        //
        Mono<Monopatin> monopatin = this.webClient.get()
                .uri("/monopatin/{id}", viaje.getId_monopatin())
                .retrieve()
                .bodyToMono(Monopatin.class);

        Monopatin monopatinNuevo = null;
        //cuando llega el monopatin, lo guardo en la variable monopatinNuevo
        monopatin.subscribe(monopatinRequest -> {monopatinNuevo = monopatinRequest;} );

        if (!monopatinNuevo.isDisponible()) {
            //se podria usar codigo de respuesta 409 conflict
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El monopatin esta ocupado");
        }
        Viaje viajeNuevo = new Viaje(LocalDateTime.now(), null, 0.0, false, 0L, viaje.getId_monopatin());
        MonopatinViaje monopatinViaje = new MonopatinViaje(viajeNuevo, monopatinNuevo);

        return new ResponseEntity(this.repository.save(viajeNuevo), HttpStatus.CREATED);
    }
}
