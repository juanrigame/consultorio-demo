package com.kosmos_test.asistente.consultorio.service;

import com.kosmos_test.asistente.consultorio.model.Consultorio;
import com.kosmos_test.asistente.consultorio.repository.ConsultorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorioService {

    @Autowired
    private ConsultorioRepository consultorioRepository;

    public List<Consultorio> getAllConsultorios() {
        return consultorioRepository.findAll();
    }

    public void deleteConsultorio(Integer id) {
        consultorioRepository.deleteById(id);
    }

    public Consultorio saveConsultorio(Consultorio consultorio) {
        return consultorioRepository.save(consultorio);
    }
}
