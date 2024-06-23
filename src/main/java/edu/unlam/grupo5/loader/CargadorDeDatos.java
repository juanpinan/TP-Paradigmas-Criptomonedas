package edu.unlam.grupo5.loader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.unlam.grupo5.exception.CargaDeDatosException;
import edu.unlam.grupo5.model.Criptomoneda;
import edu.unlam.grupo5.model.Historico;
import edu.unlam.grupo5.model.Mercado;
import edu.unlam.grupo5.model.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public class CargadorDeDatos {

    public static List<Criptomoneda> cargarCriptomonedas() {
        try {
            CSVReader reader = getReader("criptomonedas.csv");
            List<Criptomoneda> listaDeCriptomonedas = new ArrayList<>();
            String[] linea;
            while ((linea = reader.readNext()) != null) {
               listaDeCriptomonedas.add(new Criptomoneda(
                       linea[0],
                       linea[1],
                       Double.parseDouble(linea[2])));
            }
            return listaDeCriptomonedas;
        } catch (IOException | CsvValidationException | URISyntaxException e) {
            throw new CargaDeDatosException(e.getMessage());
        }
    }

    public static List<Mercado> cargarMercados() {
        try {
            CSVReader reader = getReader("mercados.csv");
            List<Mercado> listaDeMercados = new ArrayList<>();
            String[] linea;

            while ((linea = reader.readNext()) != null) {
                listaDeMercados.add(new Mercado(
                        linea[0],
                        Double.valueOf(linea[1]),
                        linea[2],
                        linea[3]));
            }

            return listaDeMercados;
        } catch (IOException | CsvValidationException | URISyntaxException e) {
            throw new CargaDeDatosException(e.getMessage());
        }
    }

    public static List<Usuario> cargarUsuarios() {
        try {
            CSVReader reader = getReader("usuarios.csv");
            List<Usuario> listaDeCriptomonedas = new ArrayList<>();
            String[] linea;
            while ((linea = reader.readNext()) != null) {
                listaDeCriptomonedas.add(new Usuario(
                        linea[0],
                        linea[1],
                        linea[2],
                        Double.valueOf( !isEmpty(linea[3]) ? linea[3] : "0")));
            }
            return listaDeCriptomonedas;
        } catch (IOException | CsvValidationException | URISyntaxException e) {
            throw new CargaDeDatosException(e.getMessage());
        }
    }
    public static Map<Usuario, Historico> cargarHistoricos(List<Usuario> usuarios) {
        Map<Usuario, Historico> historicos = new HashMap<>();
        for (Usuario usuario : usuarios) {
            String file = usuario.getNombreDeUsuario().concat("_historico.csv");
            try {
                CSVReader reader = getReader(file);
                String[] linea;
                Map<String, Integer> historialDeCompras = new HashMap<>();
                while ((linea = reader.readNext()) != null) {
                    historialDeCompras.put(linea[0], Integer.valueOf(linea[1]));
                }
                historicos.put(usuario, new Historico(historialDeCompras));
            } catch (FileNotFoundException e) {
                if(!usuario.getRolONumeroDeCuenta().equals("administrador")){
                    System.out.printf("Aviso: El usuario %s no tiene historico.%n", usuario.getNombreDeUsuario());
                }
            } catch (CsvValidationException | IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return historicos;
    }

    private static CSVReader getReader(String path) throws URISyntaxException, FileNotFoundException {
        try {
            File file = new File(ClassLoader.getSystemResource(path).toURI());
            return new CSVReader(new FileReader(file));
        } catch (NullPointerException | FileNotFoundException e) {
            throw new FileNotFoundException(String.format("El path %s no existe.", path));
        }
    }
}
