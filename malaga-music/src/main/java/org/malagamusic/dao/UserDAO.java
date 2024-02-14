package org.malagamusic.dao;

import org.malagamusic.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    public void create(User user);
    public List<User> getAll();
    public Optional<User> find(int id);
    public void update(User user);
    public void delete(int id);
}
