package org.malagamusic.model;

import java.util.Objects;

public class Marca {
    private String nombre;
    private int idMarca;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marca marca = (Marca) o;
        return idMarca == marca.idMarca;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMarca);
    }
}
