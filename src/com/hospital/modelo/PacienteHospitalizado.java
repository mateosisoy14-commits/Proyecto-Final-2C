package com.hospital.modelo;

public final class PacienteHospitalizado extends Paciente {

    private int numeroCama;
    private int diasHospitalizado;

    public PacienteHospitalizado(String nombre, String codigo, int edad,
            int numeroCama, int diasHospitalizado) throws IllegalArgumentException {
        super(nombre, codigo, edad);

        if (numeroCama <= 0) {
            throw new IllegalArgumentException("Número de cama inválido.");
        }

        if (diasHospitalizado < 0) {
            throw new IllegalArgumentException("Los días hospitalizado no pueden ser negativos.");
        }

        this.numeroCama = numeroCama;
        this.diasHospitalizado = diasHospitalizado;
    }

    @Override
    public void obtenerInfo() {
        System.out.println("\n=== PACIENTE HOSPITALIZADO ===");
        System.out.println("Nombre: " + getnombre());
        System.out.println("Código: " + getcodigo());
        System.out.println("Edad: " + getedad());
        System.out.println("Número de cama: " + numeroCama);
        System.out.println("Días hospitalizado: " + diasHospitalizado);
    }

    public int getDiasHospitalizado() {
        return diasHospitalizado;
    }
}
