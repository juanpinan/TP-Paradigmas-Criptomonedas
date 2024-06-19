package edu.unlam.grupo5.model;

import java.util.HashMap;
import java.util.Map;

public class Historico {

    private final Map<String, Double> historialDeCompras;

    public Historico() {
        historialDeCompras = new HashMap<>();
    }

    public void agregarCompra(String simbolo, double cantidad) {
        if(!historialDeCompras.containsKey(simbolo)) {
            historialDeCompras.put(simbolo, cantidad);
        } else {
            Double cantidadActualizadaDelHistorico = historialDeCompras.get(simbolo);
            cantidadActualizadaDelHistorico += cantidad;
            // Capaz el replace no haga falta porque Double es un objeto y ya estariamos actualizando la referencia
            historialDeCompras.replace(simbolo, cantidadActualizadaDelHistorico);
        }
    }

    public void agregarVenta(String simbolo, double cantidad) {
        if(!historialDeCompras.containsKey(simbolo)) {
            throw new RuntimeException("La criptomoneda no existe.");
        } else {
            Double cantidadActualizadaDelHistorico = historialDeCompras.get(simbolo);
            cantidadActualizadaDelHistorico -= cantidad;
            if(cantidadActualizadaDelHistorico == 0) {
                historialDeCompras.remove(simbolo);
            } else {
                // Capaz el replace no haga falta porque Double es un objeto y ya estariamos actualizando la referencia
                historialDeCompras.replace(simbolo, cantidadActualizadaDelHistorico);
            }
        }
    }

    public Double getCantidadPorSimbolo(String simbolo) {
        return historialDeCompras.get(simbolo);
    }

    public Map<String, Double> getHistorialDeCompras() {
        return historialDeCompras;
    }

}

