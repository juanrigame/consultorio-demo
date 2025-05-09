package com.kosmos_test.asistente.consultorio.service;

import com.kosmos_test.asistente.consultorio.model.Doctor;
import com.kosmos_test.asistente.consultorio.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> findAll() {

        return doctorRepository.findAll();

    }
    public List<Doctor> findDoctorById(Long idDoctor) {

        return doctorRepository.findAll().stream().filter(doctor -> doctor.getId().equals(idDoctor)).collect(Collectors.toList());

    }


    public Doctor saveDoctor(Doctor doctor) { return doctorRepository.save(doctor); }
    public void deleteDoctor(Doctor doctor) {doctorRepository.delete(doctor); }
}
