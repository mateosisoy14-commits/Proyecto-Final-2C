package com.hospital.modelo;

import java.util.ArrayList;

import com.hospital.excepciones.PacienteNoEncontradoException;


public class Hospital {

    private String nombre;
    private ArrayList<Sala> salas;
    private ArrayList<Personal> personal;


    public Hospital(String nombre){

        this.nombre = nombre;
        salas = new ArrayList<>();
        personal = new ArrayList<>();

        salas.add(  new Sala("Urgencias", 10));
        salas.add(  new Sala("Pediatra", 8));
        salas.add(  new Sala("Cirugia", 6));

    }

    public ArrayList<Sala> getsalas() {
        return salas;
    }

    public ArrayList<Personal> getpersonal() {
        return personal;
    }

        public void agregarPersonal(Personal personal) {
            this.personal.add(personal);
            System.out.println(
                personal.getnombre() + " registrado en el hospital."
            );
        }

        public Sala buscarSala(String nombreSala) {

            for (int i = 0; i < salas.size(); i++) {
                Sala sala = salas.get(i);

                if (sala.getnombre().equals(nombreSala)) {
                    return sala;
                }
            }

            return null;
    }


    public Paciente buscarPaciente(String codigo) throws PacienteNoEncontradoException{
    for (int i = 0; i < salas.size(); i++) {
    Sala sala = salas.get(i);

    try {return sala.buscarPaciente(codigo);
    } catch (PacienteNoEncontradoException e) {
        System.out.println("No esta en esta sala, seguir buscando");
    }
     } throw new PacienteNoEncontradoException(codigo);}


     public void generarReporteGeneral(){

        System.out.println("=== REPORTE GENERAL : " + nombre + "===");

        for (int i = 0; i < salas.size(); i++) {
            Sala sala = salas.get(i);

            System.out.println("Sala: " + sala.getnombre());
            System.out.println("| Ocupacion: " + sala.getpacientes().size() + "/"  + sala.getcapacidad());

        }

        System.out.println("Personal registrado: " + personal.size());

        for (int i = 0; i < personal.size(); i++) {
            Personal p = personal.get(i);

            p.generarReporte();

        }




     }



    }
