package com.hospital.app;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.hospital.excepciones.CamaNoDisponibleException;
import com.hospital.excepciones.CitaInvalidaException;
import com.hospital.excepciones.PacienteNoEncontradoException;
import com.hospital.modelo.Enfermero;
import com.hospital.modelo.Hospital;
import com.hospital.modelo.Medico;
import com.hospital.modelo.Paciente;
import com.hospital.modelo.PacienteAmbulatorio;
import com.hospital.modelo.PacienteHospitalizado;
import com.hospital.modelo.Personal;
import com.hospital.modelo.Sala;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital("Hospital Regional San Juan");

        Medico m1 = new Medico("Dra. Laura Gomez", "M001", "Cardiologa");
        Medico m2 = new Medico("Dr. Pedro Ruiz", "M002", "Pediatra");
        Enfermero e1 = new Enfermero("Ana Torres", "E001", "Cuidados Intensivos", "Mañana");

        hospital.agregarPersonal(m1);
        hospital.agregarPersonal(m2);
        hospital.agregarPersonal(e1);

        ArrayList<Paciente> pacientesTemporales = new ArrayList<>();
        int opcion;

        do {
            mostrarMenu();
            String mensajeOpcion = "Elige una opción: ";
            opcion = leerEntero(scanner, mensajeOpcion);

            try {
                switch (opcion) {
                    case 1: {
                        Paciente nuevoPaciente = registrarPaciente(scanner);
                        if (nuevoPaciente != null) {
                            pacientesTemporales.add(nuevoPaciente);
                            System.out.println("Paciente registrado correctamente.");
                        }
                        break;
                    }
                    case 2:
                        asignarPaciente(scanner, hospital, pacientesTemporales);
                        break;
                    case 3:
                        agendarCita(scanner, hospital);
                        break;
                    case 4:
                        listarPacientesSala(scanner, hospital);
                        break;
                    case 5:
                        verAgendaMedico(scanner, hospital);
                        break;
                    case 6:
                        darDeAlta(scanner, hospital);
                        break;
                    case 7:
                        buscarPaciente(scanner, hospital);
                        break;
                    case 8:
                        hospital.generarReporteGeneral();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema.");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado en esta operación: " + e.getMessage());
                System.out.println("Volviendo al menú principal...");
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada;
            try {
                entrada = scanner.nextLine();
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("No se pudo leer la entrada. Se usará 0 por defecto.");
                return 0;
            }

            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: debes ingresar un número entero. Intenta de nuevo.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ DEL HOSPITAL ===");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Asignar paciente a sala");
        System.out.println("3. Agendar cita con médico");
        System.out.println("4. Ver pacientes de una sala");
        System.out.println("5. Ver agenda de un médico");
        System.out.println("6. Dar de alta");
        System.out.println("7. Buscar paciente por código");
        System.out.println("8. Reporte general del hospital");
        System.out.println("0. Salir");
    }

    private static Paciente registrarPaciente(Scanner scanner) {
        int tipo = leerEntero(scanner, "Tipo (1=Ambulatorio, 2=Hospitalizado): ");

        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo de paciente inválido. Debe ser 1 o 2.");
            return null;
        }

        String nombre = leerTexto(scanner, "Nombre: ");
        String codigo = leerTexto(scanner, "Código: ");
        int edad = leerEntero(scanner, "Edad: ");

        try {
            if (tipo == 1) {
                String proximaCita = leerTexto(scanner, "Próxima cita: ");
                return new PacienteAmbulatorio(nombre, codigo, edad, proximaCita);
            }

            int numeroCama = leerEntero(scanner, "Número de cama: ");
            int diasHospitalizado = leerEntero(scanner, "Días hospitalizado: ");
            return new PacienteHospitalizado(
                    nombre, codigo, edad, numeroCama, diasHospitalizado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar paciente: " + e.getMessage());
            return null;
        }
    }

    private static void asignarPaciente(
            Scanner scanner, Hospital hospital,
            ArrayList<Paciente> pacientesTemporales) {
        if (pacientesTemporales.isEmpty()) {
            System.out.println("Primero debes registrar un paciente.");
            return;
        }

        mostrarSalas(hospital);
        String nombreSala = leerTexto(scanner, "Nombre de la sala: ");
        Sala sala = hospital.buscarSala(nombreSala);

        if (sala == null) {
            System.out.println("Sala no encontrada.");
            return;
        }

        String codigo = leerTexto(scanner, "Código del paciente a asignar: ");
        Paciente paciente = null;
        for (int i = 0; i < pacientesTemporales.size(); i++) {
            if (pacientesTemporales.get(i).getcodigo().equals(codigo)) {
                paciente = pacientesTemporales.get(i);
                break;
            }
        }

        if (paciente == null) {
            System.out.println("No hay ningún paciente registrado (sin asignar) con ese código.");
            return;
        }

        try {
            sala.agregarPaciente(paciente);
            pacientesTemporales.remove(paciente);
        } catch (CamaNoDisponibleException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void agendarCita(Scanner scanner, Hospital hospital) {
        mostrarMedicos(hospital);
        String idMedico = leerTexto(scanner, "ID del médico: ");
        Medico medico = buscarMedico(hospital, idMedico);

        if (medico == null) {
            System.out.println("Médico no encontrado.");
            return;
        }

        String fecha = leerTexto(scanner, "Fecha: ");
        String respuesta = leerTexto(scanner, "¿Agregar motivo? (s/n): ");

        try {
            if (respuesta.equalsIgnoreCase("s")) {
                String motivo = leerTexto(scanner, "Motivo: ");
                medico.agendarCita(fecha, motivo);
            } else {
                medico.agendarCita(fecha);
            }
        } catch (CitaInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarPacientesSala(Scanner scanner, Hospital hospital) {
        String nombreSala = leerTexto(scanner, "Nombre de la sala: ");
        Sala sala = hospital.buscarSala(nombreSala);

        if (sala == null) {
            System.out.println("Sala no encontrada.");
            return;
        }

        sala.listarPaciente();
    }

    private static void verAgendaMedico(Scanner scanner, Hospital hospital) {
        String idMedico = leerTexto(scanner, "ID del médico: ");
        Medico medico = buscarMedico(hospital, idMedico);

        if (medico == null) {
            System.out.println("Médico no encontrado.");
            return;
        }

        if (medico.getcitas().isEmpty()) {
            System.out.println("Este médico no tiene citas agendadas.");
            return;
        }

        for (int i = 0; i < medico.getcitas().size(); i++) {
            medico.getcitas().get(i).getInfo();
        }
    }

    private static void darDeAlta(Scanner scanner, Hospital hospital) {
        String nombreSala = leerTexto(scanner, "Nombre de la sala: ");
        String codigo = leerTexto(scanner, "Código del paciente: ");
        Sala sala = hospital.buscarSala(nombreSala);

        if (sala == null) {
            System.out.println("Sala no encontrada.");
            return;
        }

        try {
            sala.eliminarPaciente(codigo);
        } catch (PacienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void buscarPaciente(Scanner scanner, Hospital hospital) {
        String codigo = leerTexto(scanner, "Código del paciente: ");

        try {
            Paciente paciente = hospital.buscarPaciente(codigo);
            paciente.obtenerInfo();
        } catch (PacienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarSalas(Hospital hospital) {
        for (int i = 0; i < hospital.getsalas().size(); i++) {
            Sala sala = hospital.getsalas().get(i);
            System.out.println("Sala: " + sala.getnombre()
                    + " (" + sala.getpacientes().size()
                    + "/" + sala.getcapacidad() + ")");
        }
    }

    private static void mostrarMedicos(Hospital hospital) {
        ArrayList<Personal> personal = hospital.getpersonal();

        for (int i = 0; i < personal.size(); i++) {
            if (personal.get(i) instanceof Medico) {
                Medico medico = (Medico) personal.get(i);
                System.out.println(medico.getid() + " - " + medico.getnombre());
            }
        }
    }

    private static Medico buscarMedico(Hospital hospital, String id) {
        ArrayList<Personal> personal = hospital.getpersonal();

        for (int i = 0; i < personal.size(); i++) {
            if (personal.get(i) instanceof Medico
                    && personal.get(i).getid().equals(id)) {
                return (Medico) personal.get(i);
            }
        }

        return null;
    }

    private static String leerTexto(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException | IllegalStateException e) {
            // No se pudo leer más entrada (por ejemplo, el flujo de entrada se cerró).
            // En lugar de detener el programa, se continúa con un valor vacío.
            System.out.println("No se pudo leer la entrada. Se usará un valor vacío.");
            return "";
        }
    }

}
