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

    public void ejecutarCompra(Criptomoneda criptomoneda, Double cantidad) {
        actualizarCapacidadPorCompra(cantidad);
        actualizarVolumen(1.05);
        actualizarVariacion(1.05);
        // TODO: Mover esto fuera de esta clase
        if (cantidad > 1000){
            criptomoneda.actualizarPrecioPorCompraGrande();
        }
    }

    public void ejecutarVenta(Double cantidad) {
        actualizarCapacidadPorVenta(cantidad);
        actualizarVolumen(0.97);
        actualizarVariacion(0.97);
    }

    private void actualizarCapacidadPorVenta(Double cantidad) {
        this.capacidad += cantidad;
    }

    private void actualizarCapacidadPorCompra(Double cantidad) {
        if(cantidad > this.capacidad) {
            throw new RuntimeException("La compra excede la capacidad disponible de la moneda.");
        }
        this.capacidad -= cantidad;
    }

    private void actualizarVolumen(double porcentaje) {
        double volumen = Double.parseDouble(this.volumen24hs.substring(0, this.volumen24hs.length()-1));
        this.volumen24hs = String.valueOf(volumen * porcentaje).substring(0,5) + "%";
    }

    private void actualizarVariacion(double porcentaje) {
        double variacion = Double.parseDouble(this.variacionUltimos7dias.substring(1, this.variacionUltimos7dias.length()-1));
        double valorAntesDeGuardar = variacion * porcentaje;
        String variacionActualizada =
                valorAntesDeGuardar > 10 ?
                        String.valueOf(valorAntesDeGuardar).substring(0,5) :
                        String.valueOf(valorAntesDeGuardar).substring(0,4);
        this.variacionUltimos7dias = "+" + variacionActualizada + "%";
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
        return String.format("Simbolo: %s, Capacidad: %f, Volumen Ultimas 24hs: %s, Variacion ultimos 7 dias: %s",
                this.simbolo, this.capacidad, this.volumen24hs, this.variacionUltimos7dias);
    }
}
