package com.hospital.modelo;

public abstract class Paciente {
    private String nombre;
    private String codigo;
    private int edad;

    public Paciente(String nombre, String codigo, int edad) throws IllegalArgumentException {
        if (edad < 0 || edad > 120) {
            throw new IllegalArgumentException("Edad invalida: " + edad);
        }

        this.nombre = nombre;
        this.codigo = codigo;
        this.edad = edad;
    }

    public String getnombre() {
        return nombre;
    }

    public String getcodigo() {
        return codigo;
    }

    public int getedad() {
        return edad;
    }

    public abstract void obtenerInfo();
}
