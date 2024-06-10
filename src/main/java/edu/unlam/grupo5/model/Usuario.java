package edu.unlam.grupo5.model;
import java.math.BigDecimal;

public class Usuario {

    private final String nombreDeUsuario;
    private final String rolONumeroDeCuenta;
    private final String banco;


    private Double saldo; // TODO: Ver de cambiar a BigDecimal
    private BigDecimal saldoB; // sumo este dato, yo diría de ir haciendo las cosas con double que sabemos cómo funciona
    //y despues rompernos la cabeza para hacerlo funcionar con BigDecimal, que tiene sus propios métodos.
    

    public Usuario(String nombreDeUsuario, String rolONumeroDeCuenta, String banco, Double saldo) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.rolONumeroDeCuenta = rolONumeroDeCuenta;
        this.banco = banco;
        this.saldo = saldo;
    }

    public Double getSaldo() {
        return saldo;
    }

    public String getBanco() {
        return banco;
    }

    public String getRolONumeroDeCuenta() {
        return rolONumeroDeCuenta;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }


}
