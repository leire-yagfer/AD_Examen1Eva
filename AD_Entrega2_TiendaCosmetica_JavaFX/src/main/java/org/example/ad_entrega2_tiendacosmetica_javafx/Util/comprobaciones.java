package org.example.ad_entrega2_tiendacosmetica_javafx.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class comprobaciones {

    //método que verifica si el nombre de usuario ya está en uso o no
    public static boolean existeUsuario(String nUsuario) {
        String sql = "SELECT COUNT(*) FROM Clientes WHERE nombre_usuario = ?"; //consulta sql para contar cuántos registros hay con el email proporcionado
        try (Connection connection = conexionBBDD.conectar(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nUsuario);
            ResultSet rs = statement.executeQuery(); //se ejecuta la consulta y obtengo el resultado
            //si se obtiene un resultado, se verifica el conteo
            if (rs.next()) {
                return rs.getInt(1) > 0; //devuelve true si el email existe --> paso el rs a int y si es >0, es porque existe
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar el usuario: " + e.getMessage());
        }
        return false; //devuelve false si ocurre un error o si no existe el email
    }//existeUsuario

    //método que verifica si el email ya está en uso o no
    public static boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Clientes WHERE email = ?"; //consulta sql para contar cuántos registros hay con el email proporcionado
        try (Connection connection = conexionBBDD.conectar(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery(); //se ejecuta la consulta y obtengo el resultado
            //si se obtiene un resultado, se verifica el conteo
            if (rs.next()) {
                return rs.getInt(1) > 0; //devuelve true si el email existe --> paso el rs a int y si es >0, es porque existe
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar el email: " + e.getMessage());
        }
        return false; //devuelve false si ocurre un error o si no existe el email
    }//existeEmail
}
