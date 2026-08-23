package com.hospital.modelo;

public abstract class Personal {
    private String nombre;
    private String id;
    private String especialidad;

    public Personal(String nombre, String id, String especialidad) {
        this.nombre = nombre;
        this.id = id;
        this.especialidad = especialidad;
    }

    public String getnombre() {
        return nombre;
    }

    public String getid() {
        return id;
    }

    public String getespecialidad() {
        return especialidad;
    }

    public abstract void generarReporte();
}
