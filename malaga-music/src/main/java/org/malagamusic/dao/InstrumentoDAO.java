package org.malagamusic.dao;

import org.malagamusic.model.Instrumento;
import java.util.List;
import java.util.Optional;

public interface InstrumentoDAO {

	public void create(Instrumento instrumento);
	public List<Instrumento> getAll();
	public List<Instrumento> getByClass(int idClass);
	public Optional<Instrumento> find(int id);
	public void update(Instrumento instrumento);
	public void delete(int id);
}
