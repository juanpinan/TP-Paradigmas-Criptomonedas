package edu.unlam.grupo5.model;

public class Criptomoneda {

    private final String nombre;
    private final String simbolo;
    private Double precioUSD;

    public Criptomoneda(String nombre, String simbolo, Double precioUSD) {
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.precioUSD = precioUSD;
    }

    @Override
    public String toString() {
        return "\nCriptomoneda\n{" +
                "nombre='" + nombre + '\'' +
                ", simbolo='" + simbolo + '\'' +
                ", precioUSD=" + precioUSD +
                "\n}";
    }
}
