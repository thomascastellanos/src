package com.pagos.servicios.repositorio;

import com.pagos.servicios.modelo.Cuenta;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repositorio único para toda la jerarquía de Cuenta.
 * PILAR: POLIMORFISMO -> almacena y opera sobre Cuenta sin importar si el
 * objeto real es CuentaAhorros o CuentaCorriente.
 */
public class CuentaRepositorio extends RepositorioEnMemoria<Cuenta, String> {

    @Override
    protected String obtenerId(Cuenta entidad) {
        return entidad.getNumero();
    }

    public List<Cuenta> obtenerPorCliente(int clienteId) {
        return obtenerTodos().stream()
                .filter(c -> c.getClienteId() == clienteId)
                .collect(Collectors.toList());
    }
}
