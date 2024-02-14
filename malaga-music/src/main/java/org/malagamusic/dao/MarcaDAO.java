package org.malagamusic.dao;

import org.malagamusic.model.Instrumento;
import org.malagamusic.model.Marca;
import java.util.List;
import java.util.Optional;

public interface MarcaDAO {

    public void create(Marca marca);
    public List<Marca> getAll();
    public Optional<Marca> find(int id);
    public void update(Marca marca);
    public void delete(int id);
}
