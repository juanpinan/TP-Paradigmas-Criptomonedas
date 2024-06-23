package edu.unlam.grupo5.model;

public class Usuario {

    private final String nombreDeUsuario;
    private final String rolONumeroDeCuenta;
    private final String banco;
    private Double saldo;

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

    public void actualizarSaldoPorCompra(double dineroGastado) {
        this.saldo = this.saldo - dineroGastado;
    }

    public void actualizarSaldoPorVenta(double dineroGanado) {
        this.saldo = this.saldo + dineroGanado;
    }
}
