package org.malagamusic.dao;


import org.malagamusic.model.Pedido;
import java.util.List;

public interface PedidoDAO {
    public void create(Pedido pedido);
    public List<Pedido> getAll();
    public void delete(int id);
}
