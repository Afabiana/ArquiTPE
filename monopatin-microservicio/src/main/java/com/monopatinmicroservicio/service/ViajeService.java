package com.monopatinmicroservicio.service;

import com.monopatinmicroservicio.model.Tarifa;
import com.monopatinmicroservicio.model.Viaje;
import com.monopatinmicroservicio.repository.MonopatinViajeRepository;
import com.monopatinmicroservicio.repository.TarifaRepository;
import com.monopatinmicroservicio.repository.ViajeRepository;
import com.monopatinmicroservicio.service.DTO.TarifaDTORequest;
import com.monopatinmicroservicio.service.DTO.ViajeDTOResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;
import java.util.Optional;

@Service("ViajeService")
public class ViajeService {
    private WebClient webClient;
    private ViajeRepository repository;
    private MonopatinViajeRepository monopatinViajeRepository;
    private TarifaRepository tarifaRepository;

    public ViajeService(ViajeRepository viajeRepository,
                        TarifaRepository tarifaRepository,
                        MonopatinViajeRepository monopatinViajeRepository) {
        this.webClient = WebClient.create();
        this.repository = viajeRepository;
        this.tarifaRepository = tarifaRepository;
        this.monopatinViajeRepository = monopatinViajeRepository;
    }


    @Transactional
    public Optional<ViajeDTOResponse> traerViaje(Long id) {
        Optional<Viaje> optionalViaje = this.repository.findById(id);

        if (optionalViaje.isPresent()) {
            ViajeDTOResponse viaje = new ViajeDTOResponse(optionalViaje.get());
            return Optional.of(viaje);
        }

        return Optional.empty();
    }


    @Transactional
    public Double traerTotalFacturado(int anio, int mesDesde, int mesHasta) {
        if (repository.traerTotalFacturado(anio, mesDesde, mesHasta) != null) {
            return repository.traerTotalFacturado(anio, mesDesde, mesHasta);
        }
        return null;
    }

    //metodos que necesitarian rol de admin u otro
    @Transactional
    public Double actualizarTarifa(Long id, double tarifa) {
        Optional<Tarifa> optionalTarifa = tarifaRepository.findById(id);

        if (optionalTarifa.isPresent()) {
            optionalTarifa.get().setPrecio(tarifa);
            tarifaRepository.save(optionalTarifa.get());
            return optionalTarifa.get().getPrecio();
        }

        return null;
    }


    @Transactional
    public Double agregarTarifa(TarifaDTORequest tarifa) {
        Tarifa tarifa1 = new Tarifa(tarifa);
        tarifaRepository.save(tarifa1);
        return tarifa1.getPrecio();
    }

    @Transactional
    public List<ViajeDTOResponse> traerViajes() {
        return repository.findAll().stream().map(ViajeDTOResponse::new).toList();
    }

    @Transactional
    public boolean eliminarViaje(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    //public ResponseEntity<?> empezarViaje(ViajeDTORequest viaje)
}
