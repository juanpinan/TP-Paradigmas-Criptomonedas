package edu.unlam.grupo5;

import edu.unlam.grupo5.model.Criptomoneda;
import edu.unlam.grupo5.model.Mercado;
import edu.unlam.grupo5.model.Usuario;

import java.util.List;

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
}
