package edu.unlam.grupo5.util;

import java.util.Scanner;

public class Util {

    private static final Scanner scanner = new Scanner(System.in);

    public static String ingresoDeTexto() {
        String s = scanner.nextLine();
        boolean validez = s.matches("[a-zA-Z ]+");
        if (validez){
            return s;
        } else {
            System.out.println("El texto ingresado no es valido. Por favor ingreselo de nuevo: ");
            return ingresoDeTexto();
        }
    }

    public static String ingresoDeOpcionNumerica() {
        String s = scanner.nextLine();
        boolean validez = s.matches("[1-9]");
        if (validez){
            return s;
        } else {
            System.out.println("La opcion ingresada no es valida. Por favor ingrese de nuevo una opcion: ");
            return ingresoDeOpcionNumerica();
        }
    }

    public static String ingresoSoloDeNumeros(){
        String s = scanner.nextLine();
        boolean validez = s.matches("[1-9]\\d*|0");
        if (validez){
            return s;
        } else {
            System.out.println("El numero ingresado no es valido. Por favor ingrese de nuevo: ");
            return ingresoSoloDeNumeros();
        }
    }


    public static String ingresoSoloDeNumerosConDecimal(){
        String s = scanner.nextLine();
        boolean validez = s.matches("([1-9]\\d*(\\.\\d+)?|0\\.\\d+|0)");
        if (validez){
            return s;
        } else {
            System.out.println("El numero ingresado no es valido. Por favor ingrese de nuevo: ");
            return ingresoSoloDeNumerosConDecimal();
        }
    }

    public static void mostrarMenuAdministrador() {
        System.out.println("Menu de opciones");
        System.out.println("-----------------\n");
        System.out.println("1) Crear criptomoneda.");
        System.out.println("2) Modificar criptomoneda.");
        System.out.println("3) Eliminar criptomoneda.");
        System.out.println("4) Consultar criptomoneda.");
        System.out.println("5) Consultar estado actual del mercado.");
        System.out.println("6) Salir y guardar.");
        System.out.println();
        System.out.println("Ingrese una opcion (1-6): ");
    }

    public static void mostrarMenuTrader() {
        System.out.println("Menu de opciones");
        System.out.println("-----------------\n");
        System.out.println("1) Comprar criptomonedas.");
        System.out.println("2) Vender criptomoneda.");
        System.out.println("3) Consultar criptomoneda.");
        System.out.println("4) Recomendar criptomoneda.");
        System.out.println("5) Consultar estado actual del mercado.");
        System.out.println("6) Visualizar archivo de transacciones historico.");
        System.out.println("7) Salir y guardar.");
        System.out.println();
        System.out.println("Ingrese una opcion (1-7): ");
    }
}
