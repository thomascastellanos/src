package com.pagos.servicios.modelo;

/**
 * PILAR: HERENCIA -> extiende el comportamiento común definido en Cuenta.
 */
public class CuentaCorriente extends Cuenta {

    private double cupoSobregiro;

    public CuentaCorriente(String numero, double saldoInicial, int clienteId, double cupoSobregiro) {
        super(numero, saldoInicial, clienteId);
        this.cupoSobregiro = cupoSobregiro;
    }

    public double getCupoSobregiro() {
        return cupoSobregiro;
    }

    public void setCupoSobregiro(double cupoSobregiro) {
        if (cupoSobregiro < 0) {
            throw new IllegalArgumentException("El cupo de sobregiro no puede ser negativo");
        }
        this.cupoSobregiro = cupoSobregiro;
    }

    // PILAR: POLIMORFISMO -> en una Cuenta Corriente el disponible incluye el cupo de sobregiro
    @Override
    public double calcularDisponible() {
        return getSaldo() + cupoSobregiro;
    }

    @Override
    public String getTipo() {
        return "Cuenta Corriente";
    }
}
