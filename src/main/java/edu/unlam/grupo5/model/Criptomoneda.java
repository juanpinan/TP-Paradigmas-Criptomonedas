package edu.unlam.grupo5.model;

public class Criptomoneda {

    private String nombre;
    private String simbolo;
    private Double precioUSD;

    public Criptomoneda(String nombre, String simbolo, Double precioUSD) {
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.precioUSD = precioUSD;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public Double getPrecioUSD() {
        return precioUSD;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public void setPrecioUSD(Double precioUSD) {
        this.precioUSD = precioUSD;
    }

    @Override
    public String toString() {
        return String.format("Nombre: %s, Simbolo: %s, Precio en dolares: %f", this.nombre, this.simbolo, this.precioUSD);
    }
}
