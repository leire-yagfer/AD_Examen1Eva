package com.example.clientesempresarepasogeneral.ClasesDAO;

import com.example.clientesempresarepasogeneral.Clases.Clientes;
import com.example.clientesempresarepasogeneral.Clases.ConnectionBD;
import com.example.clientesempresarepasogeneral.Clases.ConnectionBDMongo;
import com.example.clientesempresarepasogeneral.Clases.LocalDateAdapter;
import com.example.clientesempresarepasogeneral.Util.Alerta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import javafx.fxml.Initializable;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClienteDAO{

    MongoClient con; //cliente de mongo
    MongoCollection<Document> collection = null; //colección de documentos en mongo --> si es null no conectado a la BD y si es !=null conectado
    String json; //cadena json para guardar datos del cliente
    Document doc; //documento de mongo

    //conecta a la base de datos mongo y crea la colección "clientes"
    public void conectarBD() {
        try {
            con = ConnectionBDMongo.conectar(); //establece conexión con mongo
            MongoDatabase database = con.getDatabase("empresa"); //accede a la base de datos "empresa"
            database.createCollection("clientes"); //crea la colección "clientes" si no existe
            collection = database.getCollection("clientes"); //obtiene la colección "clientes"
            System.out.println("conectado a mongo"); //mensaje de éxito
        } catch (Exception exception) {
            System.out.println(exception.getMessage()); //muestra mensaje de error si ocurre una excepción
        }
    }

    //agrega un cliente a la base de datos SQL
    public void agregarClienteSQL(Clientes cliente) {
        String sql = "INSERT INTO CLIENTES(nombre, apellido, email, telefono) VALUES (?,?,?,?)"; //sentencia SQL para insertar cliente
        try (Connection connection = ConnectionBD.conectar()){ //establece conexión con la base de datos SQL
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //prepara la sentencia SQL --> genera el id
            statement.setString(1, cliente.getNombre()); //establece el nombre del cliente
            statement.setString(2, cliente.getApellido()); //establece el apellido del cliente
            statement.setString(3, cliente.getEmail()); //establece el email del cliente
            statement.setInt(4, cliente.getTelefono()); //establece el teléfono del cliente
            ResultSet resultSet = statement.getGeneratedKeys(); //obtiene las claves generadas
            if(resultSet.next()) { //si hay un id generado
                int idGenerado = resultSet.getInt(1); //obtiene el id generado
                cliente.setId(idGenerado); //asigna el id al objeto cliente
            }
            int columnasAfectadas = statement.executeUpdate(); //ejecuta la sentencia SQL
            if (columnasAfectadas > 0) { //si se afectaron filas
                Alerta.Info("El cliente se ha agregado", "ÉXITO!!!"); //muestra mensaje de éxito
            } else {
                Alerta.Error("El cliente no ha sido agregado"); //muestra mensaje de error
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); //muestra mensaje de error en caso de excepción SQL
        } catch (IOException e) {
            throw new RuntimeException(e); //maneja error de IO
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); //maneja error de clase no encontrada
        }
    }

    //agrega un cliente a la base de datos mongo
    public void agregarClienteMongo(Clientes cliente) {
        if (collection != null) { //si la colección no es nula
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create(); //crea el adaptador de Gson para LocalDate
            json = gson.toJson(cliente); //convierte el objeto cliente a json
            if (json == null) { //si el json es nulo
                System.out.println("json nulo"); //muestra mensaje de json nulo
            }
            doc = Document.parse(json); //convierte el json a un documento de mongo
            collection.insertOne(doc); //inserta el documento en la colección de mongo
            if (doc != null) { //si el documento no es nulo
                Alerta.Info("El cliente se ha insertado en Mongo", "ÉXITO!!!"); //muestra mensaje de éxito
            } else {
                Alerta.Error("El cliente no se ha insertado"); //muestra mensaje de error
            }
        }
    }


    //obtiene todos los clientes de la base de datos SQL
    public ArrayList<Clientes> obtenerClientes() {
        ArrayList<Clientes> listaCliente = new ArrayList<>(); //crea una lista vacía de clientes
        String sql = "SELECT * FROM CLIENTES"; //sentencia SQL para obtener todos los clientes
        try (Connection connection = ConnectionBD.conectar()){ //establece conexión con la base de datos SQL
            PreparedStatement statement = connection.prepareStatement(sql); //prepara la sentencia SQL
            ResultSet resultSet = statement.executeQuery(); //ejecuta la sentencia y obtiene los resultados
            while (resultSet.next()) { //mientras haya resultados
                String nombre = resultSet.getString("nombre"); //obtiene el nombre del cliente
                String email = resultSet.getString("email"); //obtiene el email del cliente
                int telefono = resultSet.getInt("telefono"); //obtiene el teléfono del cliente
                String apellido = resultSet.getString("apellido"); //obtiene el apellido del cliente
                int id = resultSet.getInt("id"); //obtiene el id del cliente
                Clientes cliente = new Clientes(id, nombre, apellido, email, telefono); //crea el objeto cliente
                listaCliente.add(cliente); //agrega el cliente a la lista
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //maneja error SQL
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); //maneja error de clase no encontrada
        } catch (IOException e) {
            throw new RuntimeException(e); //maneja error de IO
        }
        return listaCliente; //devuelve la lista de clientes
    }

    //elimina un cliente de la base de datos SQL
    public void eliminarClienteSQL(Clientes cliente) {
        String sql = "DELETE FROM Clientes WHERE id = ?"; //sentencia SQL para eliminar un cliente
        try (Connection connection = ConnectionBD.conectar()){ //establece conexión con la base de datos SQL
            PreparedStatement statement = connection.prepareStatement(sql); //prepara la sentencia SQL
            statement.setInt(1, cliente.getId()); //establece el id del cliente a eliminar
            int colAfectadas = statement.executeUpdate(); //ejecuta la sentencia SQL
            if (colAfectadas > 0) { //si se afectaron filas
                Alerta.Info("Se ha eliminado el cliente de forma correcta", "ÉXITO!!"); //muestra mensaje de éxito
            } else {
                Alerta.Error("No se ha podido borrar"); //muestra mensaje de error
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //maneja error SQL
        } catch (IOException e) {
            throw new RuntimeException(e); //maneja error de IO
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); //maneja error de clase no encontrada
        }
    }

    //elimina un cliente de la base de datos mongo
    public void eliminarClienteMongo(Clientes cliente) {
        if (cliente == null) { //si el cliente es nulo
            Alerta.Error("No has seleccionado ningun cliente"); //muestra mensaje de error
        } else {
            if (con == null || collection == null) { //si la conexión o la colección son nulas
                System.out.println("Conexion nula"); //muestra mensaje de conexión nula
            }
            try {
                Document document = new Document("email", cliente.getEmail()); //crea el documento con el email del cliente
                collection.deleteOne(document); //elimina el cliente de la colección
                Alerta.Info("Se ha borrado correctamente en Mongo", "ÉXITO!!"); //muestra mensaje de éxito
            } catch (Exception e) {
                System.out.println(e.getMessage()); //muestra el mensaje de error
            }
        }
    }

    //modifica un cliente en la base de datos SQL
    public void modificarClienteSQL (Clientes cliente, String nombre, String apellido, String email, int telefono) {
        String sql = "UPDATE clientes SET nombre = ?, apellido = ?, email = ?, telefono = ? WHERE id = ?"; //sentencia SQL para modificar cliente
        try (Connection connection = ConnectionBD.conectar()){ //establece conexión con la base de datos SQL
            PreparedStatement statement = connection.prepareStatement(sql); //prepara la sentencia SQL
            statement.setString(1, nombre); //establece el nuevo nombre del cliente
            statement.setString(2, apellido); //establece el nuevo apellido del cliente
            statement.setString(3, email); //establece el nuevo email del cliente
            statement.setInt(4, telefono); //establece el nuevo teléfono del cliente
            statement.setInt(5, cliente.getId()); //establece el id del cliente
            int colAfectadas = statement.executeUpdate(); //ejecuta la sentencia SQL
            if (colAfectadas == 1) { //si se afectó una fila
                Alerta.Info("Se ha modificado correctamente en SQL", "ÉXITO!!"); //muestra mensaje de éxito
            } else {
                Alerta.Error("No se ha podido modificar"); //muestra mensaje de error
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //maneja error SQL
        } catch (IOException e) {
            throw new RuntimeException(e); //maneja error de IO
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); //maneja error de clase no encontrada
        }
    }

    //modifica un cliente en la base de datos mongo
    public void modificarClienteMongo(Clientes cliente, String nombre, String apellido, String email, int telefono) {
        if (cliente == null) { //si el cliente es nulo
            Alerta.Error("Selecciona un cliente"); //muestra mensaje de error
        } else {
            Document document = new Document("email", email); //crea el documento con el email del cliente
            Bson update = Updates.combine( //crea la actualización para modificar los campos
                    Updates.set("nombre", nombre),
                    Updates.set("apellido", apellido),
                    Updates.set("telefono", telefono)
            );
            collection.updateOne(document,update); //actualiza el cliente en mongo
            Alerta.Info("Cliente actualizado en Mongo", "ÉXITO!!"); //muestra mensaje de éxito
        }
    }
}