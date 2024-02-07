package org.jarenas.ca.java.jdbc.repositorio;

import org.jarenas.ca.java.jdbc.model.Categoria;
import org.jarenas.ca.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositorioImpl implements RepositorioCategoria<Categoria> {

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstance();
    }

    @Override
    public List<Categoria> listarCategoria() {
        List<Categoria> categorias = new ArrayList<>();

        try(Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM categorias")){
            while (rs.next()){
                Categoria c = crearCategoria(rs);
                categorias.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    private static Categoria crearCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setId(rs.getLong("id"));
        c.setNombre(rs.getString("nombre"));
        return c;
    }
}
