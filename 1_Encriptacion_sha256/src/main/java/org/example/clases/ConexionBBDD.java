package org.example.clases;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;



public class ConexionBBDD {
    private static Connection con;


    public static Connection conectar() throws ClassNotFoundException, SQLException, IOException {

        Properties properties= new Properties();
        String host="";
        String port="";
        String name="";
        String username="";
        String password="";
        boolean conect=false;
        try {
            properties.load(new FileInputStream(new File("src/main/resources/configuration/database.properties")));

            //System.out.println(properties.get("driver"));
            host=String.valueOf(properties.get("host"));
            port=String.valueOf(properties.get("port"));
            name=String.valueOf(properties.get("name"));
            username=String.valueOf(properties.get("username"));
            password=String.valueOf(properties.get("password"));
            //System.out.println(host+"  "+port+"  "+name+"  "+username+"  "+password);
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
                    username, password);

            return con;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block


            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return null;
        }
    }



    /**
     * metodo desconectar unaa aplicacion de la base de datos
     * @throws SQLException
     */
    public static void desconectar() throws SQLException {
        con.close();
    }

    /**
     *metodo que mostrará si se esta conectado o no
     * @return false ou true
     */
    public static boolean estadoConexion() {
        boolean ok=false;
        try {
            if (con != null && con.isValid(0)) {
                ok= true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ok;
    }




}

