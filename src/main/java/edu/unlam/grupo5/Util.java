package edu.unlam.grupo5;

import java.util.Scanner;

public class Util {

    private static final Scanner scanner = new Scanner(System.in);

    public static String ingresoDeTexto() {
        String s = scanner.nextLine();
        boolean validez = s.matches("^[a-zA-Z0-9.]+$"); // Permitir espacios?
        if (validez){
            return s;
        } else {
            System.out.println("El texto ingresado no es valido. Por favor ingreselo de nuevo: ");
            ingresoDeTexto();
        }
        return s;
    }

    public static String ingresoDeOpcionNumerica() {
        String s = scanner.nextLine();
        boolean validez = s.matches("[0-9]");
        if (validez){
            return s;
        } else {
            System.out.println("La opcion ingresada no es valida. Por favor ingrese de nuevo una opcion: ");
            ingresoDeTexto();
        }
        return s;
    }

    public static void mostrarMenuAdministrador() {
        System.out.println("Menu de opciones");
        System.out.println("-----------------\n");
        System.out.println("1) Crear Criptomoneda");
        System.out.println("2) Modificar Criptomoneda");
        System.out.println("3) Eliminar Criptomoneda");
        System.out.println("4) Consultar Criptomoneda");
        System.out.println("5) Consultar estado actual del mercado");
        System.out.println("6) Salir y guardar");
        System.out.println();
        System.out.println("Ingrese una opcion (1-6): ");
    }
}
