package com.pagos.servicios.app;

import com.pagos.servicios.excepcion.SaldoInsuficienteException;
import com.pagos.servicios.modelo.*;
import com.pagos.servicios.repositorio.ClienteRepositorio;
import com.pagos.servicios.repositorio.CuentaRepositorio;
import com.pagos.servicios.repositorio.FacturaRepositorio;
import com.pagos.servicios.repositorio.PagoRepositorio;
import com.pagos.servicios.servicio.SistemaPagos;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ---------- Repositorios (CRUD de cada entidad) ----------
        ClienteRepositorio clienteRepo = new ClienteRepositorio();
        CuentaRepositorio cuentaRepo = new CuentaRepositorio();
        FacturaRepositorio facturaRepo = new FacturaRepositorio();
        PagoRepositorio pagoRepo = new PagoRepositorio();

        SistemaPagos sistema = new SistemaPagos(cuentaRepo, facturaRepo, pagoRepo);

        System.out.println("=== CRUD: Cliente ===");
        Cliente cliente = new Cliente(1, "Maria Gomez", "1053000111", "maria@correo.com");
        clienteRepo.crear(cliente);
        System.out.println("Creado -> " + cliente);

        cliente.setEmail("maria.gomez@correo.com"); // update en memoria
        clienteRepo.actualizar(cliente);
        System.out.println("Actualizado -> " + clienteRepo.obtenerPorId(1).get());

        System.out.println();
        System.out.println("=== CRUD: Cuenta (Herencia + Polimorfismo) ===");
        Cuenta ahorros = new CuentaAhorros("AH-001", 150000, cliente.getId(), 0.02);
        Cuenta corriente = new CuentaCorriente("CC-001", 20000, cliente.getId(), 50000);
        cuentaRepo.crear(ahorros);
        cuentaRepo.crear(corriente);

        // PILAR: POLIMORFISMO -> misma referencia Cuenta, comportamiento distinto según el tipo real
        List<Cuenta> cuentasDelCliente = cuentaRepo.obtenerPorCliente(cliente.getId());
        for (Cuenta c : cuentasDelCliente) {
            System.out.printf("%s -> disponible: %.2f%n", c.getTipo(), c.calcularDisponible());
        }

        System.out.println();
        System.out.println("=== CRUD: Factura ===");
        Factura facturaAgua = new Factura(1, "F-AGUA-001", "Acueducto", 45000,
                LocalDate.now().plusDays(10), cliente.getId());
        Factura facturaLuz = new Factura(2, "F-LUZ-001", "Energía", 80000,
                LocalDate.now().plusDays(15), cliente.getId());
        facturaRepo.crear(facturaAgua);
        facturaRepo.crear(facturaLuz);
        System.out.println("Facturas creadas para el cliente:");
        sistema.obtenerFacturasPorCliente(cliente.getId()).forEach(System.out::println);

        System.out.println();
        System.out.println("=== Funcionalidad: procesarPago (CU-01) ===");
        Pago pago1 = sistema.procesarPago("AH-001", facturaAgua.getId());
        System.out.println("Pago realizado -> " + pago1);

        System.out.println();
        System.out.println("=== Funcionalidad: obtenerSaldoCuenta (CU-02) ===");
        System.out.printf("Saldo disponible AH-001: %.2f%n", sistema.obtenerSaldoCuenta("AH-001"));
        System.out.printf("Saldo disponible CC-001: %.2f%n", sistema.obtenerSaldoCuenta("CC-001"));

        System.out.println();
        System.out.println("=== Funcionalidad: obtenerPagosPorCliente (CU-03) ===");
        sistema.obtenerPagosPorCliente(cliente.getId()).forEach(System.out::println);

        System.out.println();
        System.out.println("=== Funcionalidad: obtenerFacturasPorCliente (CU-04) ===");
        sistema.obtenerFacturasPorCliente(cliente.getId()).forEach(System.out::println);

        System.out.println();
        System.out.println("=== Excepción: Saldo insuficiente (excepción 5a del CU-01) ===");
        try {
            // La cuenta corriente tiene disponible 70.000 (20.000 saldo + 50.000 sobregiro)
            Factura facturaCostosa = new Factura(3, "F-GAS-001", "Gas", 999999,
                    LocalDate.now().plusDays(5), cliente.getId());
            facturaRepo.crear(facturaCostosa);
            sistema.procesarPago("CC-001", facturaCostosa.getId());
        } catch (SaldoInsuficienteException e) {
            System.out.println("Error controlado -> " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== CRUD: Eliminar Pago ===");
        boolean eliminado = pagoRepo.eliminar(pago1.getId());
        System.out.println("¿Pago eliminado?: " + eliminado);
    }
}