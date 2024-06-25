package edu.unlam.grupo5.model;

import java.util.Locale;

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

    public void ejecutarCompra(Integer cantidad) {
        actualizarCapacidadPorCompra(cantidad);
        actualizarVolumen(1.05);
        actualizarVariacion(+5);
    }

    public void ejecutarVenta(Integer cantidad) {
        actualizarCapacidadPorVenta(cantidad);
        actualizarVolumen(0.93);
        actualizarVariacion(-7);
    }

    private void actualizarCapacidadPorVenta(Integer cantidad) {
        this.capacidad += cantidad;
    }

    private void actualizarCapacidadPorCompra(Integer cantidad) {
        if(cantidad > this.capacidad) {
            throw new RuntimeException("La compra excede la capacidad disponible de la moneda.");
        }
        this.capacidad -= cantidad;
    }

    private void actualizarVolumen(double porcentaje) {
        double volumen = Double.parseDouble(this.volumen24hs.substring(0, this.volumen24hs.length()-1));
        this.volumen24hs = String.format(Locale.US,"%.2f%%",volumen * porcentaje);
    }

    private void actualizarVariacion(double porcentaje) {
        double variacion = Double.parseDouble(this.variacionUltimos7dias.substring(1, this.variacionUltimos7dias.length()-1));
        double valorAntesDeGuardar = variacion + porcentaje;
        String valorAntesDeGuardarString = String.format(Locale.US,"%.2f%%",valorAntesDeGuardar);
        String signo = valorAntesDeGuardar < 0 ? "-" : "+";
        this.variacionUltimos7dias = signo + valorAntesDeGuardarString;
    }

    public String getVolumen24hs() {
        return volumen24hs;
    }

    public String getVariacionUltimos7dias() {
        return variacionUltimos7dias;
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

    @Override
    public String toString() {
        return String.format("Simbolo: %s, Capacidad: %.2f, Volumen Ultimas 24hs: %s, Variacion ultimos 7 dias: %s",
                this.simbolo, this.capacidad, this.volumen24hs, this.variacionUltimos7dias);
    }
}
