package com.vargas.forohub.exception;


public class NoAutorizadoException extends RuntimeException {
    public NoAutorizadoException(String mensaje) {
        super(mensaje);
    }
}