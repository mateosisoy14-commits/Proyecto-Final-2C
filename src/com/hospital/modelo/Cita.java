package com.hospital.modelo;

public class Cita {

    private String fecha;
    private String motivo;
    private Paciente paciente;
    private Medico medico;

    public Cita(String fecha, String motivo, Paciente paciente, Medico medico) {
        this.fecha = fecha;
        this.motivo = motivo;
        this.medico = medico;
        this.paciente = paciente;
    }

    public Paciente getpaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public String getfecha() {
        return fecha;
    }

    public void getInfo() {
        System.out.println("Cita: " + getfecha());
        System.out.println("| Motivo: " + motivo);
        System.out.println("| Medico: " + medico.getnombre());
        System.out.println("| Paciente" + (paciente != null ? paciente.getnombre() : "Sin asignar"));
    }
}
