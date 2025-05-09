package com.kosmos_test.asistente.consultorio.service;
import com.kosmos_test.asistente.consultorio.model.Cita;
import com.kosmos_test.asistente.consultorio.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public Cita saveCita(Cita cita) {
        return citaRepository.save(cita);
    }

    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }
}