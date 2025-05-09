package com.kosmos_test.asistente.consultorio.repository;
import com.kosmos_test.asistente.consultorio.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findAll();
}