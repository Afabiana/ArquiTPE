package com.monopatinmicroservicio.service;

import com.monopatinmicroservicio.model.Monopatin;
import com.monopatinmicroservicio.model.Ubicacion;
import com.monopatinmicroservicio.repository.MonopatinRepository;
import com.monopatinmicroservicio.service.DTO.MonopatinDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service("monopatinService")
public class MonopatinService {

    private MonopatinRepository repository;

    public MonopatinService(MonopatinRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ResponseEntity<MonopatinDTO> getMonopatin(Long id){
        return ResponseEntity.ok(repository.findById(id)
                .map(MonopatinDTO::new)
                .orElse(null));
    }

    @Transactional
    public ResponseEntity<?> deleteMonopatin(Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("se elimino con exito");
    }

    @Transactional
    public ResponseEntity<?> updateDisponibilidad(Long id, boolean disponible){
        Optional<Monopatin> optionalMonopatin = repository.findById(id);
        if(optionalMonopatin.isPresent()){
            Monopatin monopatin = optionalMonopatin.get();
            monopatin.setDisponibilidad(disponible);
            repository.save(monopatin);
            return ResponseEntity.ok().body("Se modifico con exito");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el monopatín con el ID proporcionado");
    }

    @Transactional
    public ResponseEntity<?> updateUbicacion(Long id, Ubicacion ubicacion) {
        Optional<Monopatin> optionalMonopatin = repository.findById(id);
        if (optionalMonopatin.isPresent()) {
            Monopatin monopatin = optionalMonopatin.get();
            monopatin.setUbicacion(ubicacion);
            return ResponseEntity.ok(repository.save(monopatin));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el monopatín con el ID proporcionado");
    }

    @Transactional
    public ResponseEntity<MonopatinDTO> saveMonopatin(MonopatinDTO monopatin){
        Monopatin savedMonopatin = repository.save(new Monopatin(monopatin));
        MonopatinDTO savedMonopatinDTO = new MonopatinDTO(savedMonopatin);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMonopatinDTO);
    }

    @Transactional
    public ResponseEntity<Stream<MonopatinDTO>> getMonopatines() {
        return ResponseEntity.ok(repository.findAll().stream().map(MonopatinDTO::new));
    }
}
