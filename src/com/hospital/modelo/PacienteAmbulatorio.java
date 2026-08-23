package com.hospital.modelo;

public class PacienteAmbulatorio extends Paciente {

    private String proximaCita;

    public PacienteAmbulatorio(
            String nombre, String codigo, int edad, String proximaCita) {
        super(nombre, codigo, edad);
        this.proximaCita = proximaCita;
    }

    public String getproximaCita() {
        return proximaCita;
    }

    @Override
    public void obtenerInfo() {
        System.out.println("\nPaciente Ambulatorio: " + getnombre());
        System.out.println("| \nCodigo: " + getcodigo());
        System.out.println("| \nEdad: " + getedad());
        System.out.println("| \nProximaCita" + getproximaCita());
    }
}
