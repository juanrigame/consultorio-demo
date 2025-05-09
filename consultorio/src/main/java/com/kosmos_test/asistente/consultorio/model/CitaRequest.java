package com.kosmos_test.asistente.consultorio.model;

import lombok.Data;

import java.util.Date;

@Data
public class CitaRequest {
    private Date hora;
    private Long doctorId;
    private Integer consultorioId;
}