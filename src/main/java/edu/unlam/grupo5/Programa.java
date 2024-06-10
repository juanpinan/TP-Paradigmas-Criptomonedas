package edu.unlam.grupo5;

import edu.unlam.grupo5.model.Criptomoneda;
import edu.unlam.grupo5.model.Mercado;
import edu.unlam.grupo5.model.Usuario;

import java.util.List;
import java.util.Scanner;

import static edu.unlam.grupo5.Util.ingresoDeOpcionNumerica;
import static edu.unlam.grupo5.Util.ingresoDeTexto;
import static edu.unlam.grupo5.Util.mostrarMenuAdministrador;
import static edu.unlam.grupo5.loader.CargadorDeDatos.cargarCriptomonedas;
import static edu.unlam.grupo5.loader.CargadorDeDatos.cargarMercados;
import static edu.unlam.grupo5.loader.CargadorDeDatos.cargarUsuarios;

public class Programa {

    private List<Criptomoneda> criptomonedas;
    private List<Mercado> mercados;
    private List<Usuario> usuarios;

    public Programa() {
        this.criptomonedas = cargarCriptomonedas();
        this.mercados = cargarMercados();
        this.usuarios = cargarUsuarios();
    }

    public void iniciar() {
        Usuario usuario = login(); // TODO Se podria armar dos clases SistemaAdministrador y SistemaTrader y se ejecuta uno de los dos dependiendo de este metodo
        System.out.println("Bienvenido %s al sistema de criptodivisias. Usted está identificado como %s");
        mostrarMenuAdministrador();
        String opcion = ingresoDeOpcionNumerica();
        while(!opcion.equals("6")){
            switch (opcion){
                case "1":
                    System.out.println("Usted selecciono crear criptomoneda.");
                    crearCriptomoneda();
                    break;
                case "2":
                    System.out.println("Usted selecciono modificar criptomoneda.");
                    System.out.println("Ingrese la moneda que quiere modificar: ");
                    String criptoAModificar = ingresoDeTexto();
                    modificarCriptomoneda(criptoAModificar);
                    break;
                case "3":
                    System.out.println("Usted selecciono eliminar criptomoneda.");
                    mostrarCriptomonedas();
                    // TODO: Hacer un check por si no hay criptomonedas en el sistema.
                    System.out.println("Ingrese el nombre de la moneda que quiere eliminar: ");
                    String criptoAEliminar = ingresoDeTexto();
                    while(!eliminarCriptomoneda(criptoAEliminar)) {
                        System.out.printf("La criptomoneda %s no existe. Ingrese el nombre nuevamente: %n", criptoAEliminar);
                        criptoAEliminar = ingresoDeTexto();
                    }
                    break;
                case "4":
                    System.out.println("Usted selecciono consultar criptomoneda.");
                    System.out.println("Ingrese el nombre de la moneda que quiere consultar:");
                    String criptoAConsultar = ingresoDeTexto();
                    Criptomoneda criptomoneda = buscarCriptomoneda(criptoAConsultar);
                    while(criptomoneda == null) {
                        System.out.printf("La criptomoneda %s no existe. Ingrese el nombre nuevamente: %n", criptoAConsultar);
                        criptoAConsultar = ingresoDeTexto();
                        criptomoneda = buscarCriptomoneda(criptoAConsultar);
                    }
                    Mercado mercado = buscarMercado(criptomoneda.getSimbolo());
                    System.out.println(criptomoneda);
                    System.out.println("Datos del mercado:");
                    System.out.println(mercado.toString());
                    // TODO: Estaria bueno agregar opcion para preguntar si quiere consultar alguna otra
                case "5":
                    System.out.println("Usted selecciono consultar el estado actual del mercado.");
                    mostrarMercado();
            }
            mostrarMenuAdministrador();
            opcion = ingresoDeOpcionNumerica();
        }
    }

    private void mostrarMercado() {
        System.out.println();
        System.out.println("Estado de los mercados");
        System.out.println("-----------------");
        for (Mercado mercado : mercados) {
            System.out.println(mercado.toString());
        }
    }

    private void mostrarCriptomonedas() {
        for (Criptomoneda cripto : criptomonedas) {
            System.out.println(cripto.toString());
        }
    }

    private void crearCriptomoneda() {
        System.out.println("Ingrese el nombre de la moneda a crear (solo letras y numeros): ");
        String nombre = ingresoDeTexto();
        Criptomoneda criptomonedaTemp = buscarCriptomoneda(nombre);
        if (criptomonedaTemp != null) {
            System.out.println("La criptomoneda ya existe, desea modificarla?");
            System.out.println("1) Si.");
            System.out.println("2) Volver al menu principal.");
            String opcion = ingresoDeOpcionNumerica();
            while(!opcion.equals("2")) {
                if (opcion.equals("1")) {
                    modificarCriptomoneda(nombre);
                    return;
                } else {
                    System.out.println("Opcion no valida. Ingrese de nuevo una opcion: ");
                    opcion = ingresoDeOpcionNumerica();
                }
            }
            return;
        }
        System.out.println("Ingrese el simbolo de la moneda: ");
        String simbolo = ingresoDeTexto();
        System.out.println("Ingrese el precio de la moneda en dolares estadounidenses: ");
        Double precio = Double.valueOf(ingresoDeTexto());
        System.out.printf("Usted ingreso: Nombre: %s, Simbolo: %s, Precio: USD %f%n", nombre, simbolo, precio);
        System.out.println("Es correcto?");
        System.out.println("1) Si.");
        System.out.println("2) No.");
        // TODO: Poner un check de que las opciones sean validas
        String opcion = ingresoDeOpcionNumerica();
        if (opcion.equals("1")) {
            Criptomoneda criptomoneda = new Criptomoneda(nombre, simbolo, precio);
            this.criptomonedas.add(criptomoneda);
            agregarMercado(criptomoneda);
            System.out.println("Criptomoneda creada con exito.");
        } else {
            crearCriptomoneda();
        }
    }

    private void modificarCriptomoneda(String nombre) {
        Criptomoneda criptomoneda = buscarCriptomoneda(nombre);

        while (criptomoneda == null) {
            System.out.println("La criptomoneda que ingreso no existe, ingrese una criptomoneda que ya exista: ");
            nombre = ingresoDeTexto();
            criptomoneda = buscarCriptomoneda(nombre);
        }
        String simboloViejo = criptomoneda.getSimbolo();

        System.out.printf("Usted eligio la criptomoneda: %n Nombre: %s, Simbolo: %s, Precio: USD %f %n",
                criptomoneda.getNombre(), criptomoneda.getSimbolo(), criptomoneda.getPrecioUSD());
        System.out.println("Ingrese el nuevo nombre: ");
        String nuevoNombre = ingresoDeTexto();
        System.out.println("Ingrese el nuevo simbolo: ");
        String nuevoSimbolo = ingresoDeTexto();
        System.out.println("Ingrese el nuevo precio en dolares estadounidenses: ");
        Double nuevoPrecio = Double.valueOf(ingresoDeTexto());
        System.out.printf("Usted ingreso: %n Nombre: %s, Simbolo: %s, Precio: USD %f %n",
                nuevoNombre, nuevoSimbolo, nuevoPrecio);
        System.out.println("Es correcto?");
        System.out.println("1) Si.");
        System.out.println("2) No.");

        String opcion = ingresoDeOpcionNumerica();

        while(!opcion.equals("2")) {
            if (opcion.equals("1")) {
                criptomoneda.setNombre(nuevoNombre);
                criptomoneda.setSimbolo(nuevoSimbolo);
                criptomoneda.setPrecioUSD(nuevoPrecio);
                Mercado mercado = buscarMercado(simboloViejo);
                mercado.setSimbolo(nuevoSimbolo);
                System.out.println("Criptomoneda modificada con exito.");
                return;
            } else {
                System.out.println("Opcion no valida. Ingrese de nuevo una opcion: ");
                opcion = ingresoDeOpcionNumerica();
            }
        }
        modificarCriptomoneda(nombre);
    }

    private void agregarMercado(Criptomoneda criptomoneda) {
        Mercado mercado = new Mercado(criptomoneda.getSimbolo(), 500D, "1%", "+1%");
        this.mercados.add(mercado);
    }
    
    public Usuario login() {
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("-------------------Inicio de Sesion sistema Criptodivisas-------------------\n");
    	System.out.println("Por favor, ingrese su nombre de usuario: ");
    	String username = scanner.nextLine();

        Usuario uTemp = buscarUsuario(username);
    	while(uTemp == null) {
    		System.out.println("Ese usuario no se encuentra ingresado. Vuelva a ingresar un usuario valido:");// TODO: Cambiar a que si no existe crearlo (creo que era consigna del tp)
    		username = scanner.nextLine();
    		uTemp = buscarUsuario(username);
    	} //si paso este while, tengo el usuario.
    	return uTemp;
    	
    }

    private Usuario buscarUsuario(String username) {
        for (Usuario usuario : this.usuarios) {
            if (usuario.getNombreDeUsuario().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    private Criptomoneda buscarCriptomoneda(String nombre) {
        for (Criptomoneda criptomoneda : this.criptomonedas) {
            if (criptomoneda.getNombre().equals(nombre)) {
                return criptomoneda;
            }
        }
        return null;
    }
    private Mercado buscarMercado(String simbolo) {
        for (Mercado mercado : this.mercados) {
            if (mercado.getSimbolo().equals(simbolo)) {
                return mercado;
            }
        }
        return null;
    }


    private boolean eliminarCriptomoneda(String nombre) {
        for (Criptomoneda criptomoneda : this.criptomonedas) {
            if (criptomoneda.getNombre().equals(nombre)) {
                this.criptomonedas.remove(criptomoneda);
                eliminarMercado(criptomoneda.getSimbolo());
                return true;
            }
        }
        return false;
    }

    private void eliminarMercado(String simbolo) {
        for (Mercado mercado : this.mercados) {
            if (mercado.getSimbolo().equals(simbolo)) {
                this.mercados.remove(mercado);
                return;
            }
        }
    }
}
