package com.pagos.servicios.repositorio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * PILAR: ABSTRACCIÓN + ENCAPSULAMIENTO -> implementa la lógica común del CRUD
 * en memoria (Map interno oculto) y deja a cada subclase solo la responsabilidad
 * de indicar cuál es el identificador de su entidad (método abstracto obtenerId).
 *
 * @param <T>  tipo de entidad
 * @param <ID> tipo del identificador
 */
public abstract class RepositorioEnMemoria<T, ID> implements Repositorio<T, ID> {

    protected final Map<ID, T> almacen = new LinkedHashMap<>();

    /** Cada repositorio concreto sabe cómo extraer el id de su propia entidad. */
    protected abstract ID obtenerId(T entidad);

    @Override
    public T crear(T entidad) {
        ID id = obtenerId(entidad);
        if (almacen.containsKey(id)) {
            throw new IllegalStateException("Ya existe un registro con id " + id);
        }
        almacen.put(id, entidad);
        return entidad;
    }

    @Override
    public Optional<T> obtenerPorId(ID id) {
        return Optional.ofNullable(almacen.get(id));
    }

    @Override
    public List<T> obtenerTodos() {
        return new ArrayList<>(almacen.values());
    }

    @Override
    public T actualizar(T entidad) {
        ID id = obtenerId(entidad);
        if (!almacen.containsKey(id)) {
            throw new IllegalStateException("No existe un registro con id " + id + " para actualizar");
        }
        almacen.put(id, entidad);
        return entidad;
    }

    @Override
    public boolean eliminar(ID id) {
        return almacen.remove(id) != null;
    }
}
