package com.monopatinmicroservicio.service;

import com.monopatinmicroservicio.model.Monopatin;
import com.monopatinmicroservicio.model.MonopatinViaje;
import com.monopatinmicroservicio.model.Viaje;
import com.monopatinmicroservicio.repository.MonopatinRepository;
import com.monopatinmicroservicio.repository.MonopatinViajeRepository;
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
    private MonopatinRepository monopatinRepository;
    private MonopatinViajeRepository monopatinViajeRepository;

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
    public ResponseEntity<?> startViaje(ViajeDTORequest viaje) {
        //inicio el saldo en null para poder usarlo en el subscribe
        Double saldoUsuario = 0.0;

        //pido el saldo del usuario
        Mono<Double> saldo = this.webClient.get()
                .uri("/medioDePago/saldo/{id}", viaje.getId_usuario())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // Agrega el encabezado de tipo de contenido JSON
                .retrieve()// Enviar la solicitud GET y recibir la respuesta como un Mono
                .bodyToMono(Double.class);
        saldoUsuario = saldo.block();

        if (saldoUsuario <= 0) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No tiene saldo suficiente");}

        //pido el monopatin
        Monopatin monopatin = this.monopatinRepository.findById(viaje.getId_monopatin()).get();

        if (!monopatin.isDisponible()) {
            //se podria usar codigo de respuesta 409 conflict
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El monopatin esta ocupado");
        }
        Viaje viajeNuevo = new Viaje(LocalDateTime.now(), null, 0.0, false, 0L, viaje.getId_monopatin());
        MonopatinViaje monopatinViaje = new MonopatinViaje(viajeNuevo, monopatin);

        return new ResponseEntity(this.monopatinViajeRepository.save(monopatinViaje), HttpStatus.CREATED);
    }

    public ResponseEntity<?> getTotalFacturado(int anio, int mesDesde, int mesHasta) {
        this.repository.getTotalFacturado(anio, mesDesde, mesHasta);
    }
}
