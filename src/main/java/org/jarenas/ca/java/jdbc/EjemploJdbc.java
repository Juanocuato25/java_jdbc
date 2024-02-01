package org.jarenas.ca.java.jdbc;

import org.jarenas.ca.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;

public class EjemploJdbc {
    public static void main(String[] args) {

        //Instanciando los objetos en los parantesis del try, usamos el autoclose de estos
        try (Connection conn = ConexionBaseDatos.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet resultado = stmt.executeQuery("SELECT * FROM productos")) {

            while (resultado.next()) {
                System.out.print(resultado.getInt(1));
                System.out.print(" -> ");
                System.out.print(resultado.getString("nombre"));
                System.out.print(" -> ");
                System.out.println(resultado.getInt("precio"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
