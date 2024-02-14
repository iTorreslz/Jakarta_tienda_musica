package org.malagamusic.dao;

import org.malagamusic.model.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl extends AbstractDAOImpl implements PedidoDAO {
    /**
     * Inserta en base de datos el nuevo pedido, actualizando el id en el bean pedido.
     */
    @Override
    public synchronized void create(Pedido pedido) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("INSERT INTO pedidos (idPedido,idUsuario,listaCompra,fecha) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, pedido.getIdPedido());
            ps.setInt(2, pedido.getIdUsuario());
            ps.setString(3, pedido.getListaCompra());
            ps.setString(4, pedido.getFecha());


            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de pedido con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                pedido.setIdPedido(rsGenKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    /**
     * Devuelve lista con todos los pedidos.
     */
    @Override
    public List<Pedido> getAll() {

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Pedido> listaPed = new ArrayList<>();

        try {
            conn = connectDB();

            // Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
            s = conn.createStatement();

            rs = s.executeQuery("SELECT * FROM pedidos");
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt(1));
                pedido.setIdUsuario(rs.getInt(2));
                pedido.setListaCompra(rs.getString(3));
                pedido.setFecha(rs.getString(4));
                listaPed.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listaPed;
    }

    /**
     * Borra pedido con ID proporcionado.
     */
    @Override
    public void delete(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("DELETE FROM pedidos WHERE idPedido = ?");

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de pedido con 0 registros eliminados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }
}
