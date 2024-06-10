package edu.unlam.grupo5.model;

public class Mercado {

    private String simbolo;
    private Double capacidad;
    private String volumen24hs;
    private String variacionUltimos7dias;

    public Mercado(String simbolo, Double capacidad, String volumen24hs, String variacionUltimos7dias) {
        this.simbolo = simbolo;
        this.capacidad = capacidad;
        this.volumen24hs = volumen24hs;
        this.variacionUltimos7dias = variacionUltimos7dias;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Double capacidad) {
        this.capacidad = capacidad;
    }

    public String getVolumen24hs() {
        return volumen24hs;
    }

    public void setVolumen24hs(String volumen24hs) {
        this.volumen24hs = volumen24hs;
    }

    public String getVariacionUltimos7dias() {
        return variacionUltimos7dias;
    }

    public void setVariacionUltimos7dias(String variacionUltimos7dias) {
        this.variacionUltimos7dias = variacionUltimos7dias;
    }

    @Override
    public String toString() {
        return String.format("Simbolo: %s, Capacidad: %f, Volumen Ultimas 24hs: %s, Variacion ultimos 7 dias: %s",
                this.simbolo, this.capacidad, this.volumen24hs, this.variacionUltimos7dias);
    }
}
