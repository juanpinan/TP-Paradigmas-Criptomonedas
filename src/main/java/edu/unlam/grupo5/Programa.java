package edu.unlam.grupo5;

import edu.unlam.grupo5.model.Criptomoneda;
import edu.unlam.grupo5.model.Mercado;
import edu.unlam.grupo5.model.Usuario;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

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

    public void listarCriptomonedas() {
        System.out.println(criptomonedas);
        System.out.println(mercados);
        System.out.println(usuarios);
    }
    
    public Usuario login() 
    {
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("-------------------Inicio de Sesion sistema Criptodivisas-------------------\n");
    	System.out.println("Por favor, ingrese su nombre de usuario: ");
    	String username = scanner.nextLine();
    	Usuario uTemp = new Usuario(username);
    	
    	while(!(this.usuarios.contains(uTemp)))
    	{
    		System.out.println("Nombre de usuario incorrecto!! Vuelva a ingresar:");
    		username = scanner.nextLine();
    		uTemp = new Usuario(username);
    	} //si paso este while, tengo el usuario.
    	
    	int indice = usuarios.lastIndexOf(uTemp);
    	uTemp=this.usuarios.get(indice);
    	
    	return uTemp;
    	
    }
}
