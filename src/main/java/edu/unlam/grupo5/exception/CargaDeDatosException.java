package edu.unlam.grupo5.exception;

import static java.lang.String.format;

public class CargaDeDatosException extends RuntimeException {

    public CargaDeDatosException(String mensaje) {
        super(format("Hubo un error al realizar la carga de datos: ", mensaje));
    }
}
