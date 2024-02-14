package org.malagamusic.dao;

import org.malagamusic.model.Marca;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MarcaDAOImpl extends AbstractDAOImpl implements MarcaDAO {
    /**
     * Inserta en base de datos la nueva marca, actualizando el id en el bean marca.
     */
    @Override
    public synchronized void create(Marca marca) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("INSERT INTO marca (nombre) VALUES (?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, marca.getNombre());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de marca con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                marca.setIdMarca(rsGenKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    /**
     * Devuelve lista con todas las marcas.
     */
    @Override
    public List<Marca> getAll() {

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Marca> listaMarcas = new ArrayList<>();

        try {
            conn = connectDB();

            // Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
            s = conn.createStatement();

            rs = s.executeQuery("SELECT * FROM marca");
            while (rs.next()) {
                Marca marca = new Marca();

                marca.setIdMarca(rs.getInt(1));
                marca.setNombre(rs.getString(2));
                listaMarcas.add(marca);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listaMarcas;
    }

    /**
     * Devuelve Optional de Marca con el ID dado.
     */
    @Override
    public Optional<Marca> find(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT * FROM marca WHERE idMarca = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Marca marca = new Marca();
                marca.setIdMarca(rs.getInt(1));
                marca.setNombre(rs.getString(2));

                return Optional.of(marca);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
        return Optional.empty();
    }

    /**
     * Actualiza marca con campos del bean marca según ID de la misma.
     */
    @Override
    public void update(Marca marca) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("UPDATE marca SET nombre = ? WHERE idMarca = ?");
            ps.setString(1, marca.getNombre());
            ps.setInt(2, marca.getIdMarca());
            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Update de marca con 0 registros actualizados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

    }

    /**
     * Borra marca con ID proporcionado.
     */
    @Override
    public void delete(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("DELETE FROM marca WHERE idMarca = ?");

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de marca con 0 registros eliminados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

    }
}