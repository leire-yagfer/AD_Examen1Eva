package org.example.empresamongodbjbcdhibernate.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {
    static Connection conectar(){
        Connection con = null;
        try {
            String url ="jdbc:mysql://localhost:3306/discografica";
            String user = "root";
            String password = "IESRibera23";//cambiar por toor
            con = DriverManager.getConnection(url,user,password);
        }catch (SQLException e){
            System.out.println("Error " +e );
        }
        return con;
    }
}
