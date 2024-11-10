package com.example.clientesempresarepasogeneral.Clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionBD {
    private static Connection connection;

    public static Connection conectar() throws ClassNotFoundException, SQLException, IOException, IOException {
        if (connection == null || connection.isClosed()) {
            Properties configuration = new Properties();
            configuration.load(new FileInputStream(new File("src/main/resources/configuration/database.properties")));

            String host = configuration.getProperty("host");
            String port = configuration.getProperty("port");
            String name = configuration.getProperty("name");
            String username = configuration.getProperty("username");
            String password = configuration.getProperty("password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
                    username, password);
        }
        return connection;
    }
}
