package com.pagos.servicios.modelo;

import java.time.LocalDate;

/**
 * Entidad Factura.
 * PILAR: ENCAPSULAMIENTO -> atributos privados con acceso controlado.
 */
public class Factura {

    private int id;
    private String numero;
    private String servicio;
    private double monto;
    private LocalDate fechaVencimiento;
    private EstadoFactura estado;
    private int clienteId;

    public Factura(int id, String numero, String servicio, double monto,
                    LocalDate fechaVencimiento, int clienteId) {
        this.id = id;
        this.numero = numero;
        this.servicio = servicio;
        setMonto(monto);
        this.fechaVencimiento = fechaVencimiento;
        this.clienteId = clienteId;
        this.estado = EstadoFactura.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getServicio() {
        return servicio;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto de la factura debe ser mayor a cero");
        }
        this.monto = monto;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public EstadoFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }

    public int getClienteId() {
        return clienteId;
    }

    public boolean estaVencida() {
        return LocalDate.now().isAfter(fechaVencimiento) && estado != EstadoFactura.PAGADA;
    }

    @Override
    public String toString() {
        return String.format("Factura{numero='%s', servicio='%s', monto=%.2f, estado=%s}",
                numero, servicio, monto, estado);
    }
}
