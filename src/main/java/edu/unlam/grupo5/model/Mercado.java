package edu.unlam.grupo5.model;

public class Mercado {

    private final String simbolo;
    private Double capacidad;
    private String volumen24hs;
    private String variacionUltimos7dias;

    public Mercado(String simbolo, Double capacidad, String volumen24hs, String variacionUltimos7dias) {
        this.simbolo = simbolo;
        this.capacidad = capacidad;
        this.volumen24hs = volumen24hs;
        this.variacionUltimos7dias = variacionUltimos7dias;
    }

    @Override
    public String toString() {
        return "\nMercado\n{" +
                "simbolo='" + simbolo + '\'' +
                ", capacidad=" + capacidad +
                ", volumen24hs=" + volumen24hs +
                ", variacionUltimos7dias=" + variacionUltimos7dias +
                "}";
    }
}
