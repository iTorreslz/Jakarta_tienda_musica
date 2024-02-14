package org.malagamusic.model;

import java.util.Objects;

public class Familia {
    private String nombre;
    private int idFamilia;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Familia familia = (Familia) o;
        return idFamilia == familia.idFamilia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFamilia);
    }
}
