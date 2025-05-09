package com.kosmos_test.asistente.consultorio.controller;

import com.kosmos_test.asistente.consultorio.model.Cita;
import com.kosmos_test.asistente.consultorio.model.Consultorio;
import com.kosmos_test.asistente.consultorio.model.Doctor;
import com.kosmos_test.asistente.consultorio.service.CitaService;
import com.kosmos_test.asistente.consultorio.service.ConsultorioService;
import com.kosmos_test.asistente.consultorio.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class WebController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ConsultorioService consultorioService;

    @GetMapping("/")
    public String showMenu() {
        return "menu";
    }

    @GetMapping("/citas/alta")
    public String showAltaCitaForm(Model model) {

        List<Doctor> doctors = doctorService.findAll();
        List<Consultorio> consultorios = consultorioService.getAllConsultorios();
        model.addAttribute("doctors", doctors);
        model.addAttribute("consultorios", consultorios);
        return "cita_alta";
    }

    @PostMapping("/citas/guardar")
    public String guardarCita(@RequestParam("hora") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date hora,
                              @RequestParam("doctor") Doctor doctor,
                              @RequestParam("consultorio") Consultorio consultorio) {
        Cita cita = new Cita();
        cita.setHora(hora);
        cita.setDoctor(doctor);
        cita.setConsultorio(consultorio);
        System.out.println(cita);

        citaService.saveCita(cita);
        return "redirect:/citas/alta";
    }

    @GetMapping("/citas/consultar")
    public String mostrarCitas(@RequestParam(value = "filterDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date filterDate,
                               @RequestParam(value = "filterConsultorio", required = false) Integer filterConsultorioId,
                               Model model) {
        List<Cita> allCitas = citaService.getAllCitas();
        List<Consultorio> allConsultorios = consultorioService.getAllConsultorios();
        List<Cita> filteredCitas = allCitas;

        if (filterDate != null) {
            filteredCitas = filteredCitas.stream()
                    .filter(cita -> {
                        // Using LocalDate for comparison, considering Mexico City time zone
                        ZoneId mexicoCityZone = ZoneId.of("America/Mexico_City");
                        LocalDate citaLocalDate = cita.getHora().toInstant().atZone(mexicoCityZone).toLocalDate();
                        LocalDate filterLocalDate = filterDate.toInstant().atZone(mexicoCityZone).toLocalDate();
                        return citaLocalDate.equals(filterLocalDate);
                    })
                    .collect(Collectors.toList());
        }

        if (filterConsultorioId != null) {
            filteredCitas = filteredCitas.stream()
                    .filter(cita -> cita.getConsultorio().getId().equals(filterConsultorioId))
                    .collect(Collectors.toList());
        }

        model.addAttribute("citas", filteredCitas);
        model.addAttribute("consultorios", allConsultorios);
        model.addAttribute("filterDate", filterDate);
        model.addAttribute("filterConsultorio", filterConsultorioId);
        return "cita_consultar";
    }

    @PostMapping("/citas/eliminar/{id}")
    public String eliminarCita(@PathVariable Long id) {
        citaService.deleteCita(id);
        return "redirect:/citas/consultar";
    }
}