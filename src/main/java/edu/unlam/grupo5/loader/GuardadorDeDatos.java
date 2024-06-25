package edu.unlam.grupo5.loader;

import com.opencsv.CSVWriter;
import edu.unlam.grupo5.exception.GuardarDatosException;
import edu.unlam.grupo5.model.Criptomoneda;
import edu.unlam.grupo5.model.Historico;
import edu.unlam.grupo5.model.Mercado;
import edu.unlam.grupo5.model.Usuario;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GuardadorDeDatos {

    public static void guardarTodoYSalir(List<Criptomoneda> criptomonedas, List<Mercado> mercados, List<Usuario> usuarios,
                                         Map<Usuario, Historico> historicos) {
        String basePath = "src/main/resources/";
        try(FileWriter outputfile = new FileWriter(basePath + "criptomonedas.csv")) {
            CSVWriter writer = new CSVWriter(outputfile);
            for (Criptomoneda c : criptomonedas) {
                String [] data = {c.getNombre(), c.getSimbolo(), String.format(Locale.US,"%.2f", c.getPrecioUSD())};
                writer.writeNext(data);
            }
        } catch (IOException e) {
            throw new GuardarDatosException(e.getMessage());
        }

        try(FileWriter outputfile = new FileWriter(basePath + "mercados.csv")) {
            CSVWriter writer = new CSVWriter(outputfile);
            for (Mercado m : mercados) {
                String [] data = {m.getSimbolo(), String.format(Locale.US,"%.2f",m.getCapacidad()), m.getVolumen24hs(), m.getVariacionUltimos7dias()};
                writer.writeNext(data);
            }
        } catch (IOException e) {
            throw new GuardarDatosException(e.getMessage());
        }

        try(FileWriter outputfile = new FileWriter(basePath + "usuarios.csv")) {
            CSVWriter writer = new CSVWriter(outputfile);
            for (Usuario u : usuarios) {
                String [] data = {u.getNombreDeUsuario(), u.getRolONumeroDeCuenta(), u.getBanco(), String.format(Locale.US,"%.2f", u.getSaldo())};
                writer.writeNext(data);
            }
        } catch (IOException e) {
            throw new GuardarDatosException(e.getMessage());
        }
        for (Map.Entry<Usuario, Historico> entrada : historicos.entrySet()) {
            try(FileWriter outputfile = new FileWriter(basePath + entrada.getKey().getNombreDeUsuario() + "_historico.csv")) {
                CSVWriter writer = new CSVWriter(outputfile);
                for (Map.Entry<String, Integer> h : entrada.getValue().getHistorialDeCompras().entrySet()) {
                    String [] data = {h.getKey(), String.format("%d",h.getValue())};
                    writer.writeNext(data);
                }
            } catch (IOException e) {
                throw new GuardarDatosException(e.getMessage());
            }
        }
    }
}
