package edu.unlam.grupo5.exception;

import static java.lang.String.format;

public class GuardarDatosException extends RuntimeException {

    public GuardarDatosException(String mensaje) {
        super(format("Hubo un error al realizar el guardado de datos: ", mensaje));
    }
}