package com.pagos.servicios.repositorio;

import java.util.List;
import java.util.Optional;

/**
 * PILAR: ABSTRACCIÓN -> define el contrato CRUD que toda entidad debe
 * cumplir, sin exponer cómo se almacena internamente (memoria, BD, etc.).
 *
 * @param <T>  tipo de entidad
 * @param <ID> tipo del identificador de la entidad
 */
public interface Repositorio<T, ID> {

    T crear(T entidad);

    Optional<T> obtenerPorId(ID id);

    List<T> obtenerTodos();

    T actualizar(T entidad);

    boolean eliminar(ID id);
}
