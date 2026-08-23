package com.hospital.modelo;

public class Enfermero extends Personal {

    private String turno;

    public Enfermero(String nombre, String id, String especialidad, String turno) {
        super(nombre, id, especialidad);
        this.turno = turno;
    }

    @Override
    public void generarReporte() {
        System.out.println("\n=== ENFERMERO ===");
        System.out.println("\nNombre: " + getnombre());
        System.out.println("\nID: " + getid());
        System.out.println("\nEspecialidad: " + getespecialidad());
        System.out.println("\nTurno: " + turno);
    }
}
