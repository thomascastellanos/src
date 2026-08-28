package com.pagos.servicios.servicio;

import com.pagos.servicios.excepcion.CuentaInactivaException;
import com.pagos.servicios.excepcion.FacturaNoDisponibleException;
import com.pagos.servicios.excepcion.SaldoInsuficienteException;
import com.pagos.servicios.modelo.*;
import com.pagos.servicios.repositorio.CuentaRepositorio;
import com.pagos.servicios.repositorio.FacturaRepositorio;
import com.pagos.servicios.repositorio.PagoRepositorio;

import java.util.List;
import java.util.Optional;

/**
 * Implementa las 4 funcionalidades pedidas en el Ejercicio 1:
 * procesarPago, obtenerSaldoCuenta, obtenerPagosPorCliente, obtenerFacturasPorCliente.
 *
 * PILAR: POLIMORFISMO -> trabaja siempre con el tipo Cuenta (abstracto);
 * el comportamiento real (calcularDisponible) depende del objeto concreto
 * (CuentaAhorros o CuentaCorriente) en tiempo de ejecución.
 */
public class SistemaPagos {

    private final CuentaRepositorio cuentaRepositorio;
    private final FacturaRepositorio facturaRepositorio;
    private final PagoRepositorio pagoRepositorio;

    private int siguienteIdPago = 1;

    public SistemaPagos(CuentaRepositorio cuentaRepositorio,
                         FacturaRepositorio facturaRepositorio,
                         PagoRepositorio pagoRepositorio) {
        this.cuentaRepositorio = cuentaRepositorio;
        this.facturaRepositorio = facturaRepositorio;
        this.pagoRepositorio = pagoRepositorio;
    }

    /**
     * CU-01: Procesar Pago.
     */
    public Pago procesarPago(String numeroCuenta, int facturaId) {
        Cuenta cuenta = cuentaRepositorio.obtenerPorId(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada: " + numeroCuenta));

        Factura factura = facturaRepositorio.obtenerPorId(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada: " + facturaId));

        // Excepción 5b: factura ya pagada o no disponible
        if (factura.getEstado() == EstadoFactura.PAGADA) {
            throw new FacturaNoDisponibleException("La factura no está disponible para pago");
        }

        try {
            // Excepción 5a: saldo insuficiente (lanzada dentro de cuenta.debitar)
            cuenta.debitar(factura.getMonto());
        } catch (IllegalStateException saldoInsuficiente) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar el pago");
        }

        factura.setEstado(EstadoFactura.PAGADA);
        cuentaRepositorio.actualizar(cuenta);
        facturaRepositorio.actualizar(factura);

        Pago pago = new Pago(siguienteIdPago++, cuenta.getNumero(), factura.getId(),
                factura.getClienteId(), factura.getMonto(), EstadoPago.EXITOSO);
        return pagoRepositorio.crear(pago);
    }

    /**
     * CU-02: Obtener Saldo de Cuenta.
     * PILAR: POLIMORFISMO -> calcularDisponible() se resuelve según el tipo real de cuenta.
     */
    public double obtenerSaldoCuenta(String numeroCuenta) {
        Cuenta cuenta = cuentaRepositorio.obtenerPorId(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada: " + numeroCuenta));

        // Excepción 3b: cuenta bloqueada/inactiva
        if (!cuenta.isActiva()) {
            throw new CuentaInactivaException("La cuenta no se encuentra activa");
        }

        return cuenta.calcularDisponible();
    }

    /**
     * CU-03: Obtener Pagos por Cliente.
     */
    public List<Pago> obtenerPagosPorCliente(int clienteId) {
        return pagoRepositorio.obtenerPorCliente(clienteId);
    }

    /**
     * CU-04: Obtener Facturas por Cliente.
     */
    public List<Factura> obtenerFacturasPorCliente(int clienteId) {
        return facturaRepositorio.obtenerPorCliente(clienteId);
    }
}
