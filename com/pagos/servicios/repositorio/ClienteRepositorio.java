package com.pagos.servicios.repositorio;

import com.pagos.servicios.modelo.Cliente;
import java.util.List;
import java.util.stream.Collectors;

public class ClienteRepositorio extends RepositorioEnMemoria<Cliente, Integer> {

    @Override
    protected Integer obtenerId(Cliente entidad) {
        return entidad.getId();
    }

    public List<Cliente> buscarPorNombre(String texto) {
        return obtenerTodos().stream()
                .filter(c -> c.getNombre().toLowerCase().contains(texto.toLowerCase()))
                .collect(Collectors.toList());
    }
}
