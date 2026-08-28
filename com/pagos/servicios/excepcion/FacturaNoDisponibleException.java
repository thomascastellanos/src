package com.pagos.servicios.excepcion;

/** Corresponde a la excepción 5b del CU-01: Procesar Pago. */
public class FacturaNoDisponibleException extends RuntimeException {
    public FacturaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
