package org.example.empresamongodbjbcdhibernate.CRUD;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.example.empresamongodbjbcdhibernate.clases.Clientes;
import org.example.empresamongodbjbcdhibernate.utils.Alerta;
import org.example.empresamongodbjbcdhibernate.utils.ConnectionMongoBD;
import org.example.empresamongodbjbcdhibernate.utils.HibernateUtil;
import org.example.empresamongodbjbcdhibernate.utils.LocalDateAdapter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientesCRUD {
    //HIBERNATE
    SessionFactory factory = HibernateUtil.getSessionFactory(); //Conectarse con hibernate
    public boolean insertarClienteHibernate(Clientes clientes){
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.save(clientes);
            transaction.commit();
            return true;
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean editarClienteHibernate(Clientes clientes){
        Transaction transaction = null;
        try (Session session= factory.openSession()){
            transaction = session.beginTransaction();
            session.update(clientes);
            transaction.commit();
            return true;
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean eliminarClienteHibernate(Clientes clientes){
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.delete(clientes);
            transaction.commit();
            return true;
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
            System.out.println(e.getMessage());
        }
        return false;
    }
    public ArrayList<Clientes>getClientesHibernate(){
        ArrayList<Clientes> listaClientes = new ArrayList<>();
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            List<Clientes>clientes=session.createQuery("from Clientes", Clientes.class).getResultList();
            listaClientes.addAll(clientes);
            transaction.commit();
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
            System.out.println(e.getMessage());
        }
        return listaClientes;
    }
    //MONGO
    MongoClient con;
    MongoCollection<Document> collection = null;
    String json;
    Document doc;

    public void conectarBDMongo(){
        try {
            con = ConnectionMongoBD.conectar();
            MongoDatabase database =con.getDatabase("empresa");
            database.createCollection("clientes");
            collection= database.getCollection("clientes");

        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public boolean inertarClienteMongoDB(Clientes clientes){
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        if (clientes!=null){
            json = gson.toJson(clientes);
            doc = Document.parse(json);
            collection.insertOne(doc);
            return true;
        }
        return false;
    }

    public  boolean eliminarClienteMongoDB(Clientes clientes){
        if (clientes!=null){
            collection.deleteMany(Filters.gte("id",clientes.getId()));
            return true;
        }
        return  false;
    }
    
    public boolean actualizarClienteMongoDB(Clientes clientes){
        try {
            Document doc = new Document("id", clientes.getId());
            Document update = new Document("$set", new Document("nombre", clientes.getNombre())
                    .append("apellido", clientes.getApellido())
                    .append("email", clientes.getEmail())
                    .append("telefono", clientes.getTelefono()));
            long numero = collection.updateOne(doc, update).getModifiedCount();
            return numero>0;
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }
    }
}
