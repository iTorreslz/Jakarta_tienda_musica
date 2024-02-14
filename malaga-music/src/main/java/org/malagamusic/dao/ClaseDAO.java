package org.malagamusic.dao;

import org.malagamusic.model.Clase;
import java.util.List;
import java.util.Optional;

public interface ClaseDAO {

    public void create(Clase clase);
    public List<Clase> getAll();
    public List<Clase> getByFamily(int idFamily);
    public Optional<Clase> find(int id);
    public void update(Clase clase);
    public void delete(int id);
}
