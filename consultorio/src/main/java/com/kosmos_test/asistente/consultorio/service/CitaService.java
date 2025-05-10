package com.kosmos_test.asistente.consultorio.service;
import com.kosmos_test.asistente.consultorio.exception.CitaConflictException;
import com.kosmos_test.asistente.consultorio.exception.DoctorCitaLimitException;
import com.kosmos_test.asistente.consultorio.exception.PacienteCitaOverlapException;
import com.kosmos_test.asistente.consultorio.model.Cita;
import com.kosmos_test.asistente.consultorio.model.Doctor;
import com.kosmos_test.asistente.consultorio.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public Cita saveCita(Cita cita) throws CitaConflictException, DoctorCitaLimitException,  PacienteCitaOverlapException{
        // Limitation 1: No duplicated "hora" and "consultorio"
        boolean existsByHoraAndConsultorio = citaRepository.existsByHoraAndConsultorio(cita.getHora(), cita.getConsultorio());
        if (existsByHoraAndConsultorio) {
            throw new CitaConflictException("Ya existe una cita programada para esa hora en este consultorio.");
        }

        // Limitation 2: No duplicated "doctor" and "hora"
        boolean existsByDoctorAndHora = citaRepository.existsByDoctorAndHora(cita.getDoctor(), cita.getHora());
        if (existsByDoctorAndHora) {
            throw new CitaConflictException("Este doctor ya tiene una cita programada para esa hora.");
        }

        // Limitation 3: A doctor can't have more than 8 appointments a day
        LocalDate citaDate = cita.getHora().toInstant().atZone(ZoneId.of("America/Mexico_City")).toLocalDate();
        Date startOfDay = Date.from(citaDate.atStartOfDay(ZoneId.of("America/Mexico_City")).toInstant());
        Date endOfDay = Date.from(citaDate.atTime(LocalTime.MAX).atZone(ZoneId.of("America/Mexico_City")).toInstant());

        long doctorCitasTodayCount = citaRepository.countByDoctorAndHoraBetween(cita.getDoctor(), startOfDay, endOfDay);
        if (doctorCitasTodayCount >= 8) {
            throw new DoctorCitaLimitException("Este doctor ha alcanzado el límite de 8 citas para este día.");
        }
        // Limitation 4: Same "paciente" can't have an appointment 26 hours before or after
        Date lowerBound = new Date(cita.getHora().getTime() - TimeUnit.HOURS.toMillis(26));
        Date upperBound = new Date(cita.getHora().getTime() + TimeUnit.HOURS.toMillis(26));

        if (citaRepository.existsByPacienteAndHoraBetween(cita.getPaciente(), lowerBound, upperBound)) {
            throw new PacienteCitaOverlapException("Este paciente ya tiene una cita programada dentro de las 26 horas anteriores o posteriores.");
        }
        return citaRepository.save(cita);
    }

    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    public void deleteCita(Long id) {
        citaRepository.deleteById(id);
    }
}