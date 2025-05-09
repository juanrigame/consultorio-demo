package com.kosmos_test.asistente.consultorio.repository;


import com.kosmos_test.asistente.consultorio.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
