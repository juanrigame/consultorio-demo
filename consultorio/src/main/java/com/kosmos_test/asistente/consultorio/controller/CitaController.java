package com.kosmos_test.asistente.consultorio.controller;

import com.kosmos_test.asistente.consultorio.model.Cita;
import com.kosmos_test.asistente.consultorio.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @PostMapping("/crear")
    public ResponseEntity<Cita> createCita(@RequestBody Cita cita) {
        Cita savedCita = citaService.saveCita(cita);
        return new ResponseEntity<>(savedCita, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cita>> getAllCitas() {
        List<Cita> citas = citaService.getAllCitas();
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }
}
