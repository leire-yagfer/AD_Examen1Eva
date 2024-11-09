package org.example.ad_entrega2_tiendacosmetica_javafx.DAO;

import org.example.ad_entrega2_tiendacosmetica_javafx.Clases.Ventas;
import org.example.ad_entrega2_tiendacosmetica_javafx.Util.conexionBBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class VentasDAO {


    //método para registrar una venta
    public static int registrarVenta(Ventas venta) {
        int filasAfectadas = 0;
        String sql = "INSERT INTO Ventas (nombreUsuario, nombreProducto, fecha_venta, cantidad, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = conexionBBDD.conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //establezo los parámetros para la consulta
            statement.setString(1, venta.getNombreUsuarioCliente()); //nombre del cliente
            statement.setString(2, venta.getNombreProducto()); //nombre del producto

            //convierto el LocalDateTime a un formato adecuado para SQL
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaVenta = venta.getFecha_venta().format(formatter);
            statement.setString(3, fechaVenta); //fecha de la venta

            statement.setInt(4, venta.getCantidad()); //cantidad comprada
            statement.setDouble(5, venta.getTotal()); //precio total

            filasAfectadas = statement.executeUpdate(); //ejecuto la consulta y obtengo el número de filas afectadas

        } catch (SQLException e) {
            System.out.println("Error al registrar la venta: " + e.getMessage());
        }//try-catch
        return filasAfectadas; //0 si no se ha realizado y 1 si se ha realizado
    }//registrarVenta
}//class