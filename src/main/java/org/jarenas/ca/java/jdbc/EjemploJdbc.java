package org.jarenas.ca.java.jdbc;

import java.sql.*;

public class EjemploJdbc {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/java_curso";
        String username = "root";
        String password = "root";


        //Instanciando los objetos en los parantesis del try, usamos el autoclose de estos
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet resultado = stmt.executeQuery("SELECT * FROM productos")) {

            while (resultado.next()) {
                System.out.print(resultado.getString("nombre"));
                System.out.print(" --> ");
                System.out.print(resultado.getInt("precio"));
                System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
