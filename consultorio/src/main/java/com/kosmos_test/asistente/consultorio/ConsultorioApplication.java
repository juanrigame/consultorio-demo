package com.kosmos_test.asistente.consultorio;

import com.kosmos_test.asistente.consultorio.model.Consultorio;
import com.kosmos_test.asistente.consultorio.model.Doctor;
import com.kosmos_test.asistente.consultorio.repository.ConsultorioRepository;
import com.kosmos_test.asistente.consultorio.repository.DoctorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class ConsultorioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultorioApplication.class, args);
	}

	@Bean
	public CommandLineRunner initConsultorios(ConsultorioRepository consultorioRepository) {
		return args -> {
			if (consultorioRepository.count() == 0) {
				List<Consultorio> consultorios = List.of(
						new Consultorio(null, "101", "1st Floor"),
						new Consultorio(null, "102", "1st Floor"),
						new Consultorio(null, "201", "2nd Floor"),
						new Consultorio(null, "202", "2nd Floor"),
						new Consultorio(null, "301", "3rd Floor")
				);
				consultorioRepository.saveAll(consultorios);
			}
		};
	}

	@Bean
	public CommandLineRunner initDoctors(DoctorRepository doctorRepository) {
		return args -> {
			if (doctorRepository.count() == 0) {
				List<Doctor> doctors = List.of(
						new Doctor(null, "Juan", "Pérez", "Gómez", "Cardiología"),
						new Doctor(null, "María", "López", "Rodríguez", "Pediatría"),
						new Doctor(null, "Carlos", "Sánchez", "Vargas", "Dermatología"),
						new Doctor(null, "Ana", "Martínez", "Ruiz", "Neurología"),
						new Doctor(null, "Pedro", "Ramírez", "Torres", "Oftalmología")
				);
				doctorRepository.saveAll(doctors);
			}
		};
	}
}
