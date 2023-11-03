package com.monopatinmicroservicio.service;

import com.monopatinmicroservicio.model.Monopatin;
import com.monopatinmicroservicio.model.Ubicacion;
import com.monopatinmicroservicio.repository.MonopatinRepository;
import com.monopatinmicroservicio.repository.MonopatinViajeRepository;
import com.monopatinmicroservicio.service.DTO.MonopatinDTO;
import com.monopatinmicroservicio.service.DTO.MonopatinMasUsadoDTO;
import com.monopatinmicroservicio.service.DTO.ReporteEstadoMonopatinesDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service("monopatinService")
public class MonopatinService {

    private MonopatinRepository repository;
    private MonopatinViajeRepository monopatinViajeRepository;

    public MonopatinService(MonopatinRepository repository) {
        this.repository = repository;
        this.monopatinViajeRepository = monopatinViajeRepository;
    }

    @Transactional
    public Optional<MonopatinDTO> getMonopatin(Long id){
        Optional<Monopatin> monopatin = repository.findById(id);
        if (monopatin.isPresent()){
            return monopatin.map(MonopatinDTO::new);
        }
        return null;
        /* return ResponseEntity.ok(repository.findById(id)
                .map(MonopatinDTO::new)
                .orElse(null));
         */
    }
    @Transactional
    public ResponseEntity<Stream<MonopatinDTO>> getMonopatines() {
        return ResponseEntity.ok(repository.findAll().stream().map(MonopatinDTO::new));
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


    public ResponseEntity<?> getMonopatinesMasUsados(int minCantidadViajes, int anio) {
        List<Object[]> resultado = this.monopatinViajeRepository.getMonopatinesMasUsados(minCantidadViajes, anio);

        if (resultado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron monopatines con la cantidad de viajes mínima especificada");
        }

        List<MonopatinMasUsadoDTO> masUsados = resultado.stream().map(m ->{
            Long monopatinId = (Long) m[0];
            int cantidad = (int) m[1];
            return new MonopatinMasUsadoDTO(monopatinId, cantidad);
        }).toList();

        return ResponseEntity.ok(masUsados);
    }

    public ResponseEntity<?> getReporteEstadoMonopatines() {
        return ResponseEntity.ok(repository.getReporteEstadoMonopatines().stream().map(m -> {
            int cantEnMantenimeinto = (int) m[0];
            int cantDisponibles = (int) m[1];
            return new ReporteEstadoMonopatinesDTO(cantEnMantenimeinto, cantDisponibles);
        }).toList());
    }
}
