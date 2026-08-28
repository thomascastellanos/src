package com.pagos.servicios.modelo;

/**
 * Entidad Cliente.
 * PILAR: ENCAPSULAMIENTO -> atributos privados, acceso controlado
 * mediante getters/setters con validación.
 */
public class Cliente {

    private int id;
    private String nombre;
    private String documento;
    private String email;

    public Cliente(int id, String nombre, String documento, String email) {
        this.id = id;
        setNombre(nombre);
        setDocumento(documento);
        setEmail(email);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("El documento del cliente no puede estar vacío");
        }
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("El email del cliente no es válido");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nombre='" + nombre + "', documento='" + documento + "'}";
    }
}
