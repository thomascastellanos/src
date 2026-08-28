package com.pagos.servicios.excepcion;

/** Corresponde a la excepción 5a del CU-01: Procesar Pago. */
public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
