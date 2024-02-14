package org.malagamusic.dao;

import org.malagamusic.model.Clase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClaseDAOImpl extends AbstractDAOImpl implements ClaseDAO {
    /**
     * Inserta en base de datos la nueva Clase, actualizando el id en el bean Clase.
     */
    @Override
    public synchronized void create(Clase clase) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("INSERT INTO clase (nombre,idFamilia) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, clase.getNombre());
            ps.setDouble(2, clase.getIdFamilia());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de clase con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                clase.setIdClase(rsGenKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    /**
     * Devuelve lista con todas las clases.
     */
    @Override
    public List<Clase> getAll() {

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Clase> listaClases = new ArrayList<>();

        try {
            conn = connectDB();

            // Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
            s = conn.createStatement();

            rs = s.executeQuery("SELECT * FROM clase");
            while (rs.next()) {
                Clase clase = new Clase();

                clase.setIdClase(rs.getInt(1));
                clase.setNombre(rs.getString(2));
                clase.setIdFamilia(rs.getInt(3));
                listaClases.add(clase);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listaClases;
    }

    /**
     * Devuelve lista con todas las clases con filtro de Familia.
     */
    @Override
    public List<Clase> getByFamily(int idFamily) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Clase> listClases = new ArrayList<>();
        String sql;
        try {
            conn = connectDB();
            sql = "SELECT * FROM clase WHERE idFamilia LIKE ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + idFamily + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                Clase clase = new Clase();
                clase.setIdClase(rs.getInt(1));
                clase.setNombre(rs.getString(2));
                clase.setIdFamilia(rs.getInt(3));
                listClases.add(clase);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
        return listClases;
    }

    /**
     * Devuelve Optional de Clase con el ID dado.
     */
    @Override
    public Optional<Clase> find(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT * FROM clase WHERE idClase = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Clase clase = new Clase();
                clase.setIdClase(rs.getInt(1));
                clase.setNombre(rs.getString(2));
                clase.setIdFamilia(rs.getInt(3));

                return Optional.of(clase);
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
     * Actualiza Clase con campos del bean Clase según ID de la misma.
     */
    @Override
    public void update(Clase clase) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("UPDATE clase SET nombre = ?,idFamilia = ?  WHERE idClase = ?");
            ps.setString(1, clase.getNombre());
            ps.setInt(2, clase.getIdFamilia());
            ps.setInt(3, clase.getIdClase());
            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Update de clase con 0 registros actualizados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

    }

    /**
     * Borra Clase con ID proporcionado.
     */
    @Override
    public void delete(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("DELETE FROM clase WHERE idClase = ?");

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de clase con 0 registros eliminados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

    }
}