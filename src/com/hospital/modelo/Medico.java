package com.hospital.modelo;

import java.util.ArrayList;
import java.util.List;

import com.hospital.excepciones.CitaInvalidaException;

public class Medico extends Personal {

    private List<Cita> citas = new ArrayList<>();

    public Medico(String nombre, String id, String especialidad) {
        super(nombre, id, especialidad);
    }

    public List<Cita> getcitas() {
        return citas;
    }

    public void agendarCita(String fecha) throws CitaInvalidaException {
        if (fecha == null || fecha.isEmpty()) {
            throw new CitaInvalidaException("La fecha de la cita no puede estar vacía");
        }

        Cita cita = new Cita(fecha, "Consulta general", null, this);
        citas.add(cita);

        System.out.println("Cita agendada para " + fecha);
    }

    public void agendarCita(String fecha, String motivo) throws CitaInvalidaException {
        if (fecha == null || fecha.isEmpty()) {
            throw new CitaInvalidaException("La fecha de la cita no puede estar vacía");
        }

        if (motivo == null || motivo.isEmpty()) {
            throw new CitaInvalidaException("El motivo no puede estar vacio");
        }

        Cita cita = new Cita(fecha, motivo, null, this);
        citas.add(cita);

        System.out.println("Cita agendada para " + fecha + " por: " + motivo);
    }

    @Override
    public void generarReporte() {
        System.out.println("\n=== MEDICO ===");
        System.out.println("\nNombre: " + getnombre());
        System.out.println("\nID: " + getid());
        System.out.println("\nEspecialidad: " + getespecialidad());
        System.out.println("\nCitas Agendadas: " + citas.size());

        for (int i = 0; i < citas.size(); i++) {
            Cita cita = citas.get(i);
            cita.getInfo();
        }
    }
}
