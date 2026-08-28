package com.pagos.servicios.excepcion;

/** Corresponde a la excepción 3b del CU-02: Obtener Saldo de Cuenta. */
public class CuentaInactivaException extends RuntimeException {
    public CuentaInactivaException(String mensaje) {
        super(mensaje);
    }
}
