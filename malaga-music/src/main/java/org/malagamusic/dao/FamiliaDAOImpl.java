package org.malagamusic.dao;

import org.malagamusic.model.Familia;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FamiliaDAOImpl extends AbstractDAOImpl implements FamiliaDAO {
    /**
     * Devuelve lista con todas las familias de instrumentos.
     */
    @Override
    public List<Familia> getAll() {

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Familia> listaFamilias = new ArrayList<>();

        try {
            conn = connectDB();

            // Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
            s = conn.createStatement();

            rs = s.executeQuery("SELECT * FROM familia");
            while (rs.next()) {
                Familia fam = new Familia();
                fam.setIdFamilia(rs.getInt(1));
                fam.setNombre(rs.getString(2));
                listaFamilias.add(fam);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listaFamilias;
    }
}
