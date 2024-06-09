package edu.unlam.grupo5.model;

public class Usuario {

    private final String nombreDeUsuario;
    private final String rolONumeroDeCuenta;
    private final String banco;
    private Double saldo; // TODO: Ver de cambiar a BigDecimal

    public Usuario(String nombreDeUsuario, String rolONumeroDeCuenta, String banco, Double saldo) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.rolONumeroDeCuenta = rolONumeroDeCuenta;
        this.banco = banco;
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "\nUsuario\n{" +
                "nombreDeUsuario='" + nombreDeUsuario + '\'' +
                ", rolONumeroDeCuenta='" + rolONumeroDeCuenta + '\'' +
                ", banco='" + banco + '\'' +
                ", saldo=" + saldo +
                "\n}";
    }
}
