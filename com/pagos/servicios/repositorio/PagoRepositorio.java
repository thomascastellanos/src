package com.pagos.servicios.repositorio;

import com.pagos.servicios.modelo.Pago;
import java.util.List;
import java.util.stream.Collectors;

public class PagoRepositorio extends RepositorioEnMemoria<Pago, Integer> {

    @Override
    protected Integer obtenerId(Pago entidad) {
        return entidad.getId();
    }

    public List<Pago> obtenerPorCliente(int clienteId) {
        return obtenerTodos().stream()
                .filter(p -> p.getClienteId() == clienteId)
                .collect(Collectors.toList());
    }
}
