package com.kosmos_test.asistente.consultorio.repository;
import com.kosmos_test.asistente.consultorio.model.Cita;
import com.kosmos_test.asistente.consultorio.model.Consultorio;
import com.kosmos_test.asistente.consultorio.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findAll();
    boolean existsByHoraAndConsultorio(Date hora, Consultorio consultorio);
    boolean existsByDoctorAndHora(Doctor doctor, Date hora);
    long countByDoctorAndHoraBetween(Doctor doctor, Date start, Date end);
    boolean existsByPacienteAndHoraBetween(String paciente, Date lowerBound, Date upperBound);
}