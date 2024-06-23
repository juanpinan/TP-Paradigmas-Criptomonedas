package edu.unlam.grupo5.model;

import java.util.HashMap;
import java.util.Map;

public class Historico {

    private final Map<String, Integer> historialDeCompras;

    public Historico() {
        historialDeCompras = new HashMap<>();
    }

    public Historico(Map<String, Integer> historialDeCompras) {
        this.historialDeCompras = historialDeCompras;
    }

    public void agregarCompra(String simbolo, int cantidad) {
        if(!historialDeCompras.containsKey(simbolo)) {
            historialDeCompras.put(simbolo, cantidad);
        } else {
            Integer cantidadActualizadaDelHistorico = historialDeCompras.get(simbolo);
            cantidadActualizadaDelHistorico += cantidad;
            // Capaz el replace no haga falta porque Double es un objeto y ya estariamos actualizando la referencia
            historialDeCompras.replace(simbolo, cantidadActualizadaDelHistorico);
        }
    }

    public void agregarVenta(String simbolo, int cantidad) {
        if(!historialDeCompras.containsKey(simbolo)) {
            throw new RuntimeException("La criptomoneda no existe.");
        } else {
            Integer cantidadActualizadaDelHistorico = historialDeCompras.get(simbolo);
            cantidadActualizadaDelHistorico -= cantidad;
            if(cantidadActualizadaDelHistorico == 0) {
                historialDeCompras.remove(simbolo);
            } else {
                // Capaz el replace no haga falta porque Double es un objeto y ya estariamos actualizando la referencia
                historialDeCompras.replace(simbolo, cantidadActualizadaDelHistorico);
            }
        }
    }

    public Integer getCantidadPorSimbolo(String simbolo) {
        return historialDeCompras.get(simbolo);
    }

    public Map<String, Integer> getHistorialDeCompras() {
        return historialDeCompras;
    }

}

