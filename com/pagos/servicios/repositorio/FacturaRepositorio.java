package com.pagos.servicios.repositorio;

import com.pagos.servicios.modelo.Factura;
import java.util.List;
import java.util.stream.Collectors;

public class FacturaRepositorio extends RepositorioEnMemoria<Factura, Integer> {

    @Override
    protected Integer obtenerId(Factura entidad) {
        return entidad.getId();
    }

    public List<Factura> obtenerPorCliente(int clienteId) {
        return obtenerTodos().stream()
                .filter(f -> f.getClienteId() == clienteId)
                .collect(Collectors.toList());
    }
}
