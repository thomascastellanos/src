package com.pagos.servicios.modelo;

import java.time.LocalDateTime;

/**
 * Entidad Pago.
 * PILAR: ENCAPSULAMIENTO -> atributos privados con acceso controlado.
 */
public class Pago {

    private int id;
    private String numeroCuenta;
    private int facturaId;
    private int clienteId;
    private double monto;
    private LocalDateTime fecha;
    private EstadoPago estado;

    public Pago(int id, String numeroCuenta, int facturaId, int clienteId,
                double monto, EstadoPago estado) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.facturaId = facturaId;
        this.clienteId = clienteId;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return String.format("Pago{id=%d, cuenta='%s', facturaId=%d, monto=%.2f, estado=%s, fecha=%s}",
                id, numeroCuenta, facturaId, monto, estado, fecha);
    }
}
