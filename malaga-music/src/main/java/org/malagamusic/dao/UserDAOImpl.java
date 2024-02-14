package org.malagamusic.dao;

import org.malagamusic.model.User;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl extends AbstractDAOImpl implements UserDAO {
    /**
     * Inserta en base de datos el nuevo usuario, actualizando el id en el bean usuario.
     */
    @Override
    public synchronized void create(User user) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("INSERT INTO usuarios (usuario,password,rol) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUser());
            ps.setString(2, User.hashPassword(user.getPasswd()));
            ps.setString(3, user.getRol());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de usuario con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                user.setIdUser(rsGenKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            closeDb(conn, ps, rs);
        }

    }

    /**
     * Devuelve lista con todos los usuarios.
     */
    @Override
    public List<User> getAll() {

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<User> listUser = new ArrayList<>();

        try {
            conn = connectDB();

            // Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
            s = conn.createStatement();

            rs = s.executeQuery("SELECT * FROM usuarios");
            while (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getInt(1));
                user.setUser(rs.getString(2));
                user.setPasswd(rs.getString(3));
                user.setRol(rs.getString(4));
                listUser.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listUser;

    }

    /**
     * Devuelve Optional de usuario con el ID dado.
     */
    @Override
    public Optional<User> find(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT * FROM usuarios WHERE idUsuario = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getInt(1));
                user.setUser(rs.getString(2));
                String shortHash = (rs.getString(3)).substring(0, 4);
                user.setPasswd(shortHash);
                user.setRol(rs.getString(4));
                return Optional.of(user);
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
     * Actualiza usuario con campos del bean usuario según ID del mismo.
     */
    @Override
    public void update(User user) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();
            String query = "UPDATE usuarios SET usuario = ?,rol = ?";
            if (user.getPasswd() != null && !user.getPasswd().isEmpty()) {
                query += ",password = ?";
            }
            query += " WHERE idUsuario = ?";

            ps = conn.prepareStatement(query);

            ps.setString(1, user.getUser());

            if (user.getPasswd() != null && !user.getPasswd().isEmpty()) {
                ps.setString(2, user.getRol());
                ps.setString(3, User.hashPassword(user.getPasswd()));
                ps.setInt(4, user.getIdUser());
            } else {
                ps.setString(2, user.getRol());
                ps.setInt(3, user.getIdUser());
            }

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Update de usuario con 0 registros actualizados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            closeDb(conn, ps, rs);
        }

    }

    /**
     * Borra usuario con ID proporcionado.
     */
    @Override
    public void delete(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("DELETE FROM usuarios WHERE idUsuario = ?");

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de usuario con 0 registros eliminados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }
}