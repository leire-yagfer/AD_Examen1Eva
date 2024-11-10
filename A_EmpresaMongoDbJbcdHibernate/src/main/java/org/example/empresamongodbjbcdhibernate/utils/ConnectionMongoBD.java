package org.example.empresamongodbjbcdhibernate.utils;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.io.FileInputStream;
import java.util.Properties;

public class ConnectionMongoBD {
    public static MongoClient conectar(){
        Properties configuration =new Properties();
        try{
            FileInputStream fis = new FileInputStream("src/main/resources/configuration/database.properties");
            configuration.load(fis);
            String username = configuration.getProperty("username");
            String password = configuration.getProperty("password");
            String host = configuration.getProperty("host");
            String port = configuration.getProperty("port");
            String author = configuration.getProperty("author");

            com.mongodb.client.MongoClient conexion = MongoClients.create("mongodb://" + username + ":" + password + "@" + host + ":" + port + "/?authSource=" + author);
            System.out.println("conectado");
            return conexion;
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
            System.out.println("no conectado");
            return  null;
        }
    }
    public static void desconectar(MongoClient con) {
        if (con != null) {
            con.close();
        }
    }

}
