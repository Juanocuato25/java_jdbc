package org.jarenas.ca.java.jdbc.repositorio;

import org.jarenas.ca.java.jdbc.model.Categoria;
import org.jarenas.ca.java.jdbc.model.Producto;
import org.jarenas.ca.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImpl implements Repositorio<Producto> {


    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstance();
    }

    @Override
    public List<Producto> listar() {
        //Creamos un arraylist para almacenar la lista de productos
        List<Producto> productos = new ArrayList<>();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.*, c.nombre as categoria FROM productos " +
                     "as p INNER JOIN categorias as c ON (p.categoria_id = c.id)")) {

            while (rs.next()) {
                Producto p = crearProdcuto(rs);
                productos.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }


    @Override
    public Producto porId(Long id) {
        Producto pId = null;

        //Se hace con preparedstamen porque es una sentencia preparada
        try (PreparedStatement stmt = getConnection().
                prepareStatement("SELECT p.*, c.nombre as categoria FROM productos as p " +
                        "INNER JOIN categorias as c ON (p.categoria_id = c.id) WHERE p.id = ?")) {
            //Pasamos como parametro el index de la tabla y el id a buscar
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    //Tenemos que llenar el objeto producto con todos los datos que tiene en la bd
                    pId = crearProdcuto(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pId;
    }

    @Override
    public void guardar(Producto producto) {
        String sql;
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "UPDATE productos SET nombre=?, precio=?, categoria_id=? WHERE id=?";
        } else {
            sql = "INSERT INTO productos(nombre, precio, categoria_id, fecha_registro) VALUES (?,?,?,?)";

        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setLong(2, producto.getPrecio());
            stmt.setLong(3, producto.getCategoria().getId());

            if (producto.getId() != null && producto.getId() > 0) {
                stmt.setLong(4, producto.getId());
            } else {
                stmt.setDate(4, new Date(producto.getFechaRegistro().getTime()));
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        try (PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM productos WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Con este metodo, llenamos el objeto de la clase con todos sus datos y lo retornamos
    private static Producto crearProdcuto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("fecha_registro"));
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("categoria_id"));
        categoria.setNombre(rs.getString("categoria"));
        p.setCategoria(categoria);
        return p;
    }
}
