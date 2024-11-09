package org.example.ad_entrega2_tiendacosmetica_javafx.Util;


import java.sql.*;

public class conexionBBDD {
    public static Connection conectar() {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/tienda_cosmetica?serverTimezone=Europe/Madrid"; //me daba error: "problema con la configuración de la zona horaria en la conexión de tu base de datos MySQL", por lo que pongo "?serverTimezone=Europe/Madrid"
            String user = "root";
            String password = "toor";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("No se ha establecido la conexión con la base de datos.  " + e.getMessage());
        }
        return connection;
    }//conectar
}