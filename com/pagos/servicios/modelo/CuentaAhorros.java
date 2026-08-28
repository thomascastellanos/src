package com.pagos.servicios.modelo;

/**
 * PILAR: HERENCIA -> extiende el comportamiento común definido en Cuenta.
 */
public class CuentaAhorros extends Cuenta {

    private double tasaInteres; // ej: 0.02 = 2%

    public CuentaAhorros(String numero, double saldoInicial, int clienteId, double tasaInteres) {
        super(numero, saldoInicial, clienteId);
        this.tasaInteres = tasaInteres;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        if (tasaInteres < 0) {
            throw new IllegalArgumentException("La tasa de interés no puede ser negativa");
        }
        this.tasaInteres = tasaInteres;
    }

    public void aplicarInteres() {
        double interes = getSaldo() * tasaInteres;
        setSaldo(getSaldo() + interes);
    }

    // PILAR: POLIMORFISMO -> en una Cuenta de Ahorros el disponible es exactamente el saldo
    @Override
    public double calcularDisponible() {
        return getSaldo();
    }

    @Override
    public String getTipo() {
        return "Cuenta de Ahorros";
    }
}
