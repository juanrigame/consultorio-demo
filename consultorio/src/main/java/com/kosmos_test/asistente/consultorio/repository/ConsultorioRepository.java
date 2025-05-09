package com.kosmos_test.asistente.consultorio.repository;

import com.kosmos_test.asistente.consultorio.model.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultorioRepository extends JpaRepository<Consultorio, Integer> {
}
