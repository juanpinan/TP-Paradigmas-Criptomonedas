package edu.unlam.grupo5;

import com.opencsv.CSVWriter;
import edu.unlam.grupo5.exception.GuardarDatosException;
import edu.unlam.grupo5.model.Criptomoneda;
import edu.unlam.grupo5.model.Historico;
import edu.unlam.grupo5.model.Mercado;
import edu.unlam.grupo5.model.Usuario;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static edu.unlam.grupo5.loader.GuardadorDeDatos.guardarTodoYSalir;
import static edu.unlam.grupo5.util.Util.ingresoDeOpcionNumerica;
import static edu.unlam.grupo5.util.Util.ingresoDeTexto;
import static edu.unlam.grupo5.util.Util.ingresoSoloDeNumeros;
import static edu.unlam.grupo5.util.Util.ingresoSoloDeNumerosConDecimal;
import static edu.unlam.grupo5.util.Util.mostrarMenuAdministrador;
import static edu.unlam.grupo5.util.Util.mostrarMenuTrader;
import static edu.unlam.grupo5.loader.CargadorDeDatos.cargarCriptomonedas;
import static edu.unlam.grupo5.loader.CargadorDeDatos.cargarHistoricos;
import static edu.unlam.grupo5.loader.CargadorDeDatos.cargarMercados;
import static edu.unlam.grupo5.loader.CargadorDeDatos.cargarUsuarios;
import static java.lang.Double.parseDouble;

public class Programa {

    private final List<Criptomoneda> criptomonedas;
    private final List<Mercado> mercados;
    private final List<Usuario> usuarios;
    private final Map<Usuario, Historico> historicos;

    public Programa() {
        this.criptomonedas = cargarCriptomonedas();
        this.mercados = cargarMercados();
        this.usuarios = cargarUsuarios();
        this.historicos = cargarHistoricos(usuarios);
    }

    public void iniciar() {
        Usuario usuario = login();
        if (usuario.getRolONumeroDeCuenta().equals("administrador")){
            this.sistemaAdmin();
        } else {
            this.sistemaTrader(usuario);
        }
        guardarTodoYSalir(this.criptomonedas, this.mercados, this.usuarios, this.historicos);
    }

    private void sistemaTrader(Usuario usuario) {
        System.out.printf("Bienvenido al sistema de criptodivisas. Usted está identificado como %s.%n", usuario.getNombreDeUsuario());
        mostrarMenuTrader();
        String opcion = ingresoDeOpcionNumerica();
        while(!opcion.equals("7")){
            switch (opcion) {
                case "1":
                    System.out.println("Usted selecciono comprar criptomonedas.");
                    comprarCriptomoneda(usuario);
                    break;
                case "2":
                    System.out.println("Usted selecciono vender criptomonedas.");
                    venderCriptomoneda(usuario);
                    break;
                case "3":
                    System.out.println("Usted selecciono consultar criptomonedas.");
                    menuConsultaCriptomonedas();
                    break;
                case "4":
                    System.out.println("Usted selecciono ver recomendaciones de criptomonedas.");
                    mostrarCriptomonedaRecomendada();
                    break;
                case "5":
                    System.out.println("Usted selecciono consultar el estado actual del mercado.");
                    mostrarMercado();
                    break;
                case "6":
                    System.out.println("Usted selecciono visualizar su archivo de transacciones historico.");
                    mostrarHistoricoDelUsuario(usuario);
                    break;
            }
            mostrarMenuTrader();
            opcion = ingresoDeOpcionNumerica();
        }
    }

    private void mostrarHistoricoDelUsuario(Usuario usuario) {
        Historico historico = historicos.get(usuario);
        if (historico == null) {
            System.out.println("Este usuario no posee un historico de transacciones.");
        } else {
            for (Map.Entry<String, Integer> entry : historico.getHistorialDeCompras().entrySet()) {
                String cripto = entry.getKey();
                Integer valor = entry.getValue();
                //TODO: Falta en orden descendente de cantidad.
                System.out.printf("Criptomoneda: %s, Cantidad: %d%n", cripto, valor);
            }
        }
    }

    private void mostrarCriptomonedaRecomendada() {
        double mayor = 0;
        Criptomoneda criptomonedaARecomendar = null;
        for (Criptomoneda criptomoneda : this.criptomonedas) {
            double cantidadDisponible = buscarMercado(criptomoneda.getSimbolo()).getCapacidad();
            double precio = criptomoneda.getPrecioUSD();
            double estadistica = ( cantidadDisponible / precio ) * 100;
            if(estadistica > mayor){
                mayor = estadistica;
                criptomonedaARecomendar = criptomoneda;
            }
        }
        System.out.println("La criptomoneda recomendada en este momento es:");
        System.out.println(criptomonedaARecomendar.toString());
    }

    private void venderCriptomoneda(Usuario usuario) {
        List<String> listaDeCriptosDisponibles = mostrarCriptomonedasPoseidasPorUsuario(usuario);
        if(listaDeCriptosDisponibles.isEmpty()) {
            System.out.println("El usuario no posee ninguna criptomoneda.");
            return;
        }
        System.out.println("Elija el simbolo de la criptomoneda que desea vender: ");
        Criptomoneda criptomoneda = buscarCriptomonedaPorSimbolo(ingresoDeTexto());
        while(criptomoneda == null || !listaDeCriptosDisponibles.contains(criptomoneda.getSimbolo())){
            System.out.println("La criptomoneda que desea comprar no existe. Elija un nombre valido:");
            criptomoneda = buscarCriptomonedaPorSimbolo(ingresoDeTexto());
        }
        System.out.println("Elija la cantidad a vender");
        int cantAVender = Integer.parseInt(ingresoSoloDeNumeros());
        if(cantAVender > historicos.get(usuario).getCantidadPorSimbolo(criptomoneda.getSimbolo())){
            System.out.println("La cantidad que usted quiere comprar supera la que usted posee. Volviendo al menu principal.");
            return;
        }
        System.out.printf("Usted eligio vender %s, cantidad: %s . ¿Quiere confirmar la operacion?%n",
                criptomoneda.getSimbolo(),
                cantAVender);
        System.out.println("1) Si.");
        System.out.println("2) No. Volver al menu principal.");
        String opcion = ingresoDeOpcionNumerica();
        while(!opcion.equals("2")) {
            if (opcion.equals("1")) {
                ejecutarVenta(usuario, criptomoneda, cantAVender);
                return;
            } else {
                System.out.println("Opcion no valida. Ingrese de nuevo una opcion: ");
                opcion = ingresoDeOpcionNumerica();
            }
        }

    }

    private void ejecutarVenta(Usuario usuario, Criptomoneda criptomoneda, int cantAVender) {
        Mercado mercado = buscarMercado(criptomoneda.getSimbolo());
        mercado.ejecutarVenta(cantAVender);
        Historico historico = historicos.get(usuario);
        historico.agregarVenta(criptomoneda.getSimbolo(), cantAVender);
        double dineroGanado = cantAVender * criptomoneda.getPrecioUSD();
        usuario.actualizarSaldoPorVenta(dineroGanado);
        System.out.println("Operacion de venta exitosa. Volviendo al menu.");
    }

    private void comprarCriptomoneda(Usuario usuario) {
        mostrarCriptomonedas();
        System.out.println("Elija el simbolo de la criptomoneda que desea comprar: ");
        Criptomoneda criptomoneda = buscarCriptomonedaPorSimbolo(ingresoDeTexto());
        while(criptomoneda == null){
            System.out.println("La criptomoneda que desea comprar no existe. Elija un nombre valido:");
            criptomoneda = buscarCriptomonedaPorSimbolo(ingresoDeTexto());
        }
        System.out.println("Elija la cantidad a comprar: ");
        Integer cantidadAComprar = Integer.parseInt(ingresoSoloDeNumeros());
        double precioOrden = cantidadAComprar * criptomoneda.getPrecioUSD();
        System.out.printf("Usted eligio comprar %s con una cantidad de %d. Esto equivale a U$D %.2f. Quiere confirmar la operacion?%n",
                criptomoneda.getSimbolo(),
                cantidadAComprar,
                precioOrden);
        System.out.println("1) Si.");
        System.out.println("2) No. Volver al menu principal.");
        String opcion = ingresoDeOpcionNumerica();
        while(!opcion.equals("2")) {
            if (opcion.equals("1")) {
                if(usuario.getSaldo() < precioOrden){
                    System.out.println("Usted no posee el saldo suficiente para realizar esta compra. Operacion cancelada.");
                } else {
                    ejecutarCompra(usuario, criptomoneda, cantidadAComprar);
                }
                return;
            } else {
                System.out.println("Opcion no valida. Ingrese de nuevo una opcion: ");
                opcion = ingresoDeOpcionNumerica();
            }
        }
    }

    private void ejecutarCompra(Usuario usuario, Criptomoneda criptomoneda, Integer cantidadAComprar) {
        Mercado mercado = buscarMercado(criptomoneda.getSimbolo());
        double precioFinal = criptomoneda.getPrecioUSD() * cantidadAComprar;
        mercado.ejecutarCompra(cantidadAComprar);

        if (cantidadAComprar > 1000){
            criptomoneda.actualizarPrecioPorCompraGrande();
        }
        usuario.actualizarSaldoPorCompra(precioFinal);

        Historico historicoUsuario;
        if (!historicos.containsKey(usuario)) {
            historicos.put(usuario, new Historico());
        }
        historicoUsuario = historicos.get(usuario);
        historicoUsuario.agregarCompra(criptomoneda.getSimbolo(), cantidadAComprar);
        System.out.println("Compra finalizada con exito. Volviendo al menu principal.");

    }

    private void sistemaAdmin() {
        System.out.println("Bienvenido al sistema de criptodivisas. Usted está identificado como administrador.");
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
                    mostrarCriptomonedas();
                    System.out.println("Ingrese la moneda que quiere modificar: ");
                    String criptoAModificar = ingresoDeTexto();
                    modificarCriptomoneda(criptoAModificar);
                    break;
                case "3":
                    System.out.println("Usted selecciono eliminar criptomoneda.");
                    if(criptomonedas.isEmpty()){
                        System.out.println("No hay criptomonedas en el sistema.");
                        break;
                    }
                    mostrarCriptomonedas();
                    System.out.println("Ingrese el nombre de la moneda que quiere eliminar: ");
                    String criptoAEliminar = ingresoDeTexto();
                    while(!eliminarCriptomoneda(criptoAEliminar)) {
                        System.out.printf("La criptomoneda %s no existe. Ingrese el nombre nuevamente: %n", criptoAEliminar);
                        criptoAEliminar = ingresoDeTexto();
                    }
                    break;
                case "4":
                    System.out.println("Usted selecciono consultar criptomoneda.");
                    menuConsultaCriptomonedas();
                    break;
                case "5":
                    System.out.println("Usted selecciono consultar el estado actual del mercado.");
                    mostrarMercado();
                    break;
            }
            mostrarMenuAdministrador();
            opcion = ingresoDeOpcionNumerica();
        }
    }

    private void menuConsultaCriptomonedas() {
        listarCriptomonedas();
        System.out.println("Ingrese el nombre de la moneda que quiere consultar:");
        String criptoAConsultar = ingresoDeTexto();
        Criptomoneda criptomoneda = buscarCriptomonedaPorNombre(criptoAConsultar);
        while(criptomoneda == null) {
            System.out.printf("La criptomoneda %s no existe. Ingrese el nombre nuevamente: %n", criptoAConsultar);
            criptoAConsultar = ingresoDeTexto();
            criptomoneda = buscarCriptomonedaPorNombre(criptoAConsultar);
        }
        Mercado mercado = buscarMercado(criptomoneda.getSimbolo());
        System.out.println(criptomoneda);
        System.out.println("Datos del mercado:");
        System.out.println(mercado.toString());
    }

    private void listarCriptomonedas() {
        System.out.println("Lista de criptomonedas disponibles: ");
        for(Criptomoneda criptomoneda: criptomonedas){
            System.out.println(criptomoneda.getNombre());
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

    private List<String> mostrarCriptomonedasPoseidasPorUsuario(Usuario usuario) {
        Historico historicoUsuario = this.historicos.get(usuario);
        Map<String, Integer> historialDeCompras = historicoUsuario.getHistorialDeCompras();
        if (historialDeCompras.isEmpty()){
            System.out.println("El usuario no posee Criptomonedas compradas.");
            return Collections.emptyList();
        } else {
            List<String> listaDeCriptosDisponibles = new ArrayList<>();
            System.out.println("Usted posee:");
            for (Map.Entry<String, Integer> entry : historialDeCompras.entrySet()) {
                String cripto = entry.getKey();
                Integer valor = entry.getValue();
                System.out.printf("Criptomoneda: %s, Cantidad: %d%n", cripto, valor);
                listaDeCriptosDisponibles.add(cripto);
            }
            return listaDeCriptosDisponibles;
        }
    }

    private void crearCriptomoneda() {
        System.out.println("Ingrese el nombre de la moneda a crear (solo letras): ");
        String nombre = ingresoDeTexto();
        Criptomoneda criptomonedaTemp = buscarCriptomonedaPorNombre(nombre);
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
        Double precio = Double.valueOf(ingresoSoloDeNumerosConDecimal());
        System.out.printf("Usted ingreso: Nombre: %s, Simbolo: %s, Precio: USD %.2f%n", nombre, simbolo, precio);
        System.out.println("Es correcto?");
        System.out.println("1) Si.");
        System.out.println("2) No.");
        String opcion = ingresoDeOpcionNumerica();
        while(!opcion.equals("2")) {
            if (opcion.equals("1")) {
                Criptomoneda criptomoneda = new Criptomoneda(nombre, simbolo, precio);
                this.criptomonedas.add(criptomoneda);
                agregarMercado(criptomoneda);
                System.out.println("Criptomoneda creada con exito.");
                return;
            } else {
                System.out.println("Opcion no valida. Ingrese de nuevo una opcion: ");
                opcion = ingresoDeOpcionNumerica();
            }
        }

    }

    private void modificarCriptomoneda(String nombre) {
        Criptomoneda criptomoneda = buscarCriptomonedaPorNombre(nombre);

        while (criptomoneda == null) {
            System.out.println("La criptomoneda que ingreso no existe, ingrese una criptomoneda que ya exista: ");
            nombre = ingresoDeTexto();
            criptomoneda = buscarCriptomonedaPorNombre(nombre);
        }
        String simboloViejo = criptomoneda.getSimbolo();

        System.out.printf("Usted eligio la criptomoneda: %n Nombre: %s, Simbolo: %s, Precio: USD %.2f %n",
                criptomoneda.getNombre(), criptomoneda.getSimbolo(), criptomoneda.getPrecioUSD());
        System.out.println("Ingrese el nuevo nombre: ");
        String nuevoNombre = ingresoDeTexto();
        System.out.println("Ingrese el nuevo simbolo: ");
        String nuevoSimbolo = ingresoDeTexto();
        System.out.println("Ingrese el nuevo precio en dolares estadounidenses: ");
        Double nuevoPrecio = Double.valueOf(ingresoSoloDeNumerosConDecimal());
        System.out.printf("Usted ingreso: %n Nombre: %s, Simbolo: %s, Precio: USD %.2f %n",
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
    	System.out.println("-------------------Inicio de Sesion sistema Criptodivisas-------------------\n");
    	System.out.println("Por favor, ingrese su nombre de usuario: ");
    	String username = ingresoDeTexto();

        Usuario uTemp = buscarUsuario(username);
    	while(uTemp == null) {
            String opcion;
    		System.out.println("Ese usuario no se encuentra ingresado. Quiere crear un usuario?");
            System.out.println("1) Si.");
            System.out.println("2) No.");
            opcion = ingresoDeOpcionNumerica();
            while(!opcion.equals("2")) {
                if (opcion.equals("1")) {
                    System.out.println("Ingrese su nombre de usuario: ");
                    String nombre = ingresoDeTexto();
                    System.out.println("Ingrese su numero de cuenta bancaria: ");
                    String cuenta = ingresoSoloDeNumeros();
                    System.out.println("Ingrese su nombre de banco: ");
                    String banco = ingresoDeTexto();
                    System.out.println("Ingrese su saldo: ");
                    Double saldo = parseDouble(ingresoSoloDeNumerosConDecimal());
                    Usuario nuevoUsuario = new Usuario(nombre, cuenta, banco, saldo);
                    usuarios.add(nuevoUsuario);
                    return nuevoUsuario;
                } else {
                    System.out.println("Opcion no valida. Ingrese de nuevo una opcion: ");
                    opcion = ingresoDeOpcionNumerica();
                }
            }
            System.out.println("Por favor, ingrese su nombre de usuario: ");
    		username = ingresoDeTexto();
    		uTemp = buscarUsuario(username);
    	}

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

    private Criptomoneda buscarCriptomonedaPorNombre(String nombre) {
        for (Criptomoneda criptomoneda : this.criptomonedas) {
            if (criptomoneda.getNombre().equals(nombre)) {
                return criptomoneda;
            }
        }
        return null;
    }

    private Criptomoneda buscarCriptomonedaPorSimbolo(String simbolo) {
        for (Criptomoneda criptomoneda : this.criptomonedas) {
            if (criptomoneda.getSimbolo().equals(simbolo)) {
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
