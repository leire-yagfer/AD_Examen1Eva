package com.example.clientesempresarepasogeneral.ClasesDAO;

import com.example.clientesempresarepasogeneral.Clases.Clientes;
import com.example.clientesempresarepasogeneral.Clases.ConnectionBDMongo;
import com.example.clientesempresarepasogeneral.Clases.LocalDateAdapter;
import com.example.clientesempresarepasogeneral.Clases.Transacciones;
import com.example.clientesempresarepasogeneral.Util.Alerta;
import com.example.clientesempresarepasogeneral.Util.HibernateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.print.Doc;
import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransaccionDAO {
    SessionFactory factory = HibernateUtil.getSessionFactory(); //se obtiene la factoría de sesiones de Hibernate

    MongoClient con; //conexión con MongoDB
    MongoCollection<Document> collection = null; //colección de MongoDB, inicialmente nula
    String json; //cadena JSON para la transacción
    Document doc; //documento de MongoDB para la transacción

    public void conectarBD() {
        try {
            con = ConnectionBDMongo.conectar(); //conecta a MongoDB
            MongoDatabase database = con.getDatabase("empresa"); //selecciona la base de datos "empresa"
            database.createCollection("transaccion"); //crea la colección "transaccion"
            collection = database.getCollection("transaccion"); //asigna la colección a la variable
            System.out.println("Conectado a Mongo"); //mensaje de conexión exitosa
        } catch (Exception exception) {
            System.out.println(exception.getMessage()); //muestra mensaje de error en caso de fallo
        }
    }

    public List<Transacciones> obtenerTransacciones(Clientes cliente) {
        List<Transacciones> transacciones = new ArrayList<>(); //lista vacía para almacenar las transacciones
        try (Session session = factory.openSession()) { //abre una nueva sesión de Hibernate
            Transaction transaction = session.beginTransaction(); //inicia una transacción
            String hql = "from Transacciones t where t.cliente.id = :id_cliente"; //consulta HQL

            //crear la consulta con la sesión de Hibernate
            Query<Transacciones> query = session.createQuery(hql, Transacciones.class);
            query.setParameter("id_cliente", cliente.getId()); //establece el parámetro para la consulta

            //obtener los resultados de la consulta
            transacciones = query.getResultList(); //ejecuta la consulta y obtiene los resultados

            transaction.commit(); //confirma la transacción
        } catch (Exception e) {
            e.printStackTrace(); //imprime el error en caso de excepción
        }
        return transacciones; //devuelve la lista de transacciones
    }

    public void agregarTransaccion(Transacciones transaccion) {
        try (Session session = factory.openSession()) { //abre una nueva sesión de Hibernate
            Transaction transaction = session.beginTransaction(); //inicia una transacción
            session.save(transaccion); //guarda la transacción en la base de datos
            transaction.commit(); //confirma la transacción
            Alerta.Info("Se ha insertado la transaccion", "ÉXITO!!"); //mensaje de éxito
        } catch (Exception e) {
            e.printStackTrace(); //imprime el error en caso de excepción
        }
    }

    public void agregarTransaccionMongo(Transacciones transacciones) {
        if (collection != null) { //si la colección no es nula
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create(); //crea un adaptador para LocalDate
            json = gson.toJson(transacciones); //convierte la transacción a formato JSON
            doc = Document.parse(json); //convierte el JSON a un documento de MongoDB
            collection.insertOne(doc); //inserta el documento en MongoDB
            Alerta.Info("Transaccion insertada en Mongo", "ÉXITO!!"); //mensaje de éxito
        }
    }

    public void borrarTransaccionMongo (Transacciones transacciones) {
        if (transacciones == null) { //si la transacción es nula
            System.out.println("Nulo"); //mensaje para transacción nula
        }
        if (con != null && collection != null) { //si la conexión y la colección no son nulas
            Document document = new Document("id", transacciones.getId()); //crea un documento con el id de la transacción
            collection.deleteOne(document); //elimina el documento de MongoDB
            Alerta.Info("Elemento borrado en Mongo", "ÉXITO!!"); //mensaje de éxito
        } else {
            System.out.println("ERROR"); //mensaje de error si la colección o la conexión son nulas
        }
    }

    public void borrarTransaccion(Transacciones transacciones) {
        try(Session session = factory.openSession()) { //abre una nueva sesión de Hibernate
            Transaction transaction = session.beginTransaction(); //inicia una transacción
            session.delete(transacciones); //elimina la transacción de la base de datos
            transaction.commit(); //confirma la transacción
            Alerta.Info("La transaccion se ha eliminado", "ÉXITO!!"); //mensaje de éxito
        } catch (Exception e) {
            e.printStackTrace(); //imprime el error en caso de excepción
        }
    }
}