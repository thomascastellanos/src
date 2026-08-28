package com.pagos.servicios.modelo;

/**
 * Clase abstracta Cuenta.
 * PILAR: ABSTRACCIÓN -> define el contrato común de toda cuenta
 * (saldo disponible, tipo) sin exponer cómo se calcula en cada caso concreto.
 * PILAR: ENCAPSULAMIENTO -> atributos privados/protegidos con acceso controlado.
 */
public abstract class Cuenta {

    private final String numero;
    private double saldo;
    private final int clienteId;
    private boolean activa;

    protected Cuenta(String numero, double saldoInicial, int clienteId) {
        this.numero = numero;
        this.saldo = saldoInicial;
        this.clienteId = clienteId;
        this.activa = true;
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    protected void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getClienteId() {
        return clienteId;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    /**
     * Debita un monto de la cuenta.
     * Cada subclase decide (vía calcularDisponible) cuánto puede debitar.
     */
    public void debitar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a debitar debe ser mayor a cero");
        }
        if (monto > calcularDisponible()) {
            throw new IllegalStateException("Saldo insuficiente para realizar el pago");
        }
        this.saldo -= monto;
    }

    // ---- Métodos abstractos: cada subtipo de cuenta los implementa a su manera ----

    /** PILAR: POLIMORFISMO -> cada subclase calcula el disponible de forma distinta. */
    public abstract double calcularDisponible();

    /** PILAR: POLIMORFISMO -> cada subclase retorna su propio tipo. */
    public abstract String getTipo();

    @Override
    public String toString() {
        return String.format("%s{numero='%s', saldo=%.2f, disponible=%.2f}",
                getTipo(), numero, saldo, calcularDisponible());
    }
}
