package org.jarenas.ca.java.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {

    //Vamos a implementar un singleton

    private static String url = "jdbc:mysql://localhost:3306/java_curso";
    private static String username = "root";
    private static String password = "root";

    private static Connection connection;
    //Se crea el metodo para hacer la conexion a la BD
    public static Connection getInstance() throws SQLException {
        if (connection == null){
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
}
