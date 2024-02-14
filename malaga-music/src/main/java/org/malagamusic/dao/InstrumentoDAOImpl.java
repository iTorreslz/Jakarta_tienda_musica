package org.malagamusic.dao;

import org.malagamusic.model.Instrumento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InstrumentoDAOImpl extends AbstractDAOImpl implements InstrumentoDAO {
    /**
     * Inserta en base de datos el nuevo instrumento, actualizando el id en el bean instrumento.
     */
    @Override
    public synchronized void create(Instrumento instrumento) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("INSERT INTO instrumentos (nombre,precio,descripcion,idClase,idMarca,stock) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, instrumento.getNombre());
            ps.setDouble(2, instrumento.getPrecio());
            ps.setString(3, instrumento.getDescripcion());
            ps.setInt(4, instrumento.getIdClase());
            ps.setInt(5, instrumento.getIdMarca());
            ps.setInt(6, instrumento.getStock());


            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de instrumento con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                instrumento.setIdInstrumento(rsGenKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    /**
     * Devuelve lista con todos los instrumentos.
     */
    @Override
    public List<Instrumento> getAll() {

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Instrumento> listaInst = new ArrayList<>();

        try {
            conn = connectDB();

            // Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
            s = conn.createStatement();

            rs = s.executeQuery("SELECT * FROM instrumentos");
            while (rs.next()) {
                Instrumento inst = new Instrumento();
                inst.setIdInstrumento(rs.getInt(1));
                inst.setNombre(rs.getString(2));
                inst.setPrecio(rs.getDouble(3));
                inst.setDescripcion(rs.getString(4));
                inst.setIdClase(rs.getInt(5));
                inst.setIdMarca(rs.getInt(6));
                inst.setStock (rs.getInt(7));
                listaInst.add(inst);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listaInst;
    }

    /**
     * Devuelve lista con todos los instrumentos con filtro de clase.
     */
    @Override
    public List<Instrumento> getByClass(int idClass) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Instrumento> listInst = new ArrayList<>();
        String sql;
        try {
            conn = connectDB();
            sql = "SELECT * FROM instrumentos WHERE idClase LIKE ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + idClass + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                Instrumento inst = new Instrumento();
                inst.setIdInstrumento(rs.getInt(1));
                inst.setNombre(rs.getString(2));
                inst.setPrecio(rs.getDouble(3));
                inst.setDescripcion(rs.getString(4));
                inst.setIdClase(rs.getInt(5));
                inst.setIdMarca(rs.getInt(6));
                inst.setStock(rs.getInt(7));
                listInst.add(inst);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
        return listInst;
    }

    /**
     * Devuelve Optional de Instrumento con el ID dado.
     */
    @Override
    public Optional<Instrumento> find(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT * FROM instrumentos WHERE idInstrumento = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Instrumento inst = new Instrumento();
                inst.setIdInstrumento(rs.getInt(1));
                inst.setNombre(rs.getString(2));
                inst.setPrecio(rs.getDouble(3));
                inst.setDescripcion(rs.getString(4));
                inst.setIdClase(rs.getInt(5));
                inst.setIdMarca(rs.getInt(6));
                inst.setStock(rs.getInt(7));

                return Optional.of(inst);
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
     * Actualiza instrumento con campos del bean instrumento según ID del mismo.
     */
    @Override
    public void update(Instrumento instrumento) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("UPDATE instrumentos SET nombre = ?,precio = ?,descripcion = ?,idClase = ?,idMarca = ?,stock = ?  WHERE idInstrumento = ?");
            ps.setString(1, instrumento.getNombre());
            ps.setDouble(2, instrumento.getPrecio());
            ps.setString(3, instrumento.getDescripcion());
            ps.setInt(4, instrumento.getIdClase());
            ps.setInt(5, instrumento.getIdMarca());
            ps.setInt(6, instrumento.getStock());
            ps.setInt(7, instrumento.getIdInstrumento());
            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Update de instrumento con 0 registros actualizados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

    }

    /**
     * Borra instrumento con ID proporcionado.
     */
    @Override
    public void delete(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("DELETE FROM instrumentos WHERE idInstrumento = ?");

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de instrumento con 0 registros eliminados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }
}
