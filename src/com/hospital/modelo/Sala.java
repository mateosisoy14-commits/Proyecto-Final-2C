package com.hospital.modelo;

import java.util.ArrayList;

import com.hospital.excepciones.CamaNoDisponibleException;
import com.hospital.excepciones.PacienteNoEncontradoException;

public class Sala {

    private String nombre;
    private int capacidad;
    private ArrayList<Paciente> pacientes = new ArrayList<>();

    public Sala(String nombre, int capacidad) throws IllegalArgumentException {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0.");
        }

        this.nombre = nombre;
        this.capacidad = capacidad;
        this.pacientes = new ArrayList<>();
    }

    public String getnombre() {
        return nombre;
    }

    public int getcapacidad() {
        return capacidad;
    }

    public ArrayList<Paciente> getpacientes() {
        return pacientes;
    }

    public void agregarPaciente(Paciente paciente) throws CamaNoDisponibleException {
        if (pacientes.size() >= capacidad) {
            throw new CamaNoDisponibleException("Sin camas disponibles.");
        }

        pacientes.add(paciente);
        System.out.println("Paciente " + paciente.getnombre());
        System.out.println("Asignado a la sala: " + nombre);
    }

    public void eliminarPaciente(String codigo) throws PacienteNoEncontradoException {
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente paciente = pacientes.get(i);

            if (paciente.getcodigo().equals(codigo)) {
                pacientes.remove(i);
                System.out.println("Paciente dado de alta.");
                return;
            }
        }
        throw new PacienteNoEncontradoException(codigo);
    }



    public Paciente buscarPaciente(String codigo) throws PacienteNoEncontradoException {
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente paciente = pacientes.get(i);

            if (paciente.getcodigo().equals(codigo)) {
                return paciente;
            }
        }

        throw new PacienteNoEncontradoException(codigo);
    }

    public void listarPaciente() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes en esta sala tilin");
            return;
        }

        for (int i = 0; i < pacientes.size(); i++) {
            Paciente paciente = pacientes.get(i);
            paciente.obtenerInfo();
        }
    }

}
