package org.example.ad_entrega4_mongodb_crudcoches_tableview.DAO;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.ad_entrega4_mongodb_crudcoches_tableview.Model.Coche;
import org.example.ad_entrega4_mongodb_crudcoches_tableview.util.ConexionBD;

import java.util.ArrayList;

public class CocheDAO {
    static MongoClient con; //objeto para establecer la conexión con la base de datos mongodb
    static MongoCollection<Document> collection = null; //referencia a la colección (tabla) de la base de datos, donde se almacenan los coches

    static String json; //cadena que contendrá el objeto coche en formato JSON
    static Document doc; //objeto BSON (formato de documento usado en mongodb)

    //método en el que creo la base de datos y su colección (tabla) coches
    public static void crearBD() {
        try {
            con = ConexionBD.conectar(); //establezco la conexión con la base de datos
            MongoDatabase database = con.getDatabase("Concesionario"); //me conecto a la base de datos "Concesionario"
            database.createCollection("coches"); //creo una colección (tabla) llamada coches en la BD
            collection = database.getCollection("coches"); //obtengo una referencia a la colección coches para realizar operaciones posteriores -_> al ser static, voy a poder realizar las diferentes funciones del CRUD sobre él
        } catch (Exception exception) {
            System.err.println(exception.getClass().getName() + ": " + exception.getMessage()); //manejo de errores en caso de que no se pueda crear la BD o la colección
        }
    } // crearBD


    //método para mostrar los coches almacenados en la base de datos
    public static ArrayList<Coche> mostrarCoches() {
        ArrayList<Coche> cochesBD = new ArrayList<>(); //lista que almacena los coches de la BD
        Gson gson = new Gson(); //objeto Gson para convertir entre JSON y objetos java
        for (Document doc : collection.find()) { //itero sobre todos los documentos (datos almacenados de cada coche) en la colección coches
            Coche coche = gson.fromJson(doc.toJson(), Coche.class); //convierto el documento BSON a un objeto coche
            cochesBD.add(coche); //añado el coche a la lista
        }//for
        return cochesBD; //devuelvo la lista de coches almacenados en la BD
    }//mostrarCoches


    //método para insertar un nuevo coche en la base de datos
    public static int insertarCoche(Coche insertarCoche) {
        Gson gson = new Gson(); //creo un objeto Gson para convertir el coche a JSON
        int semaforo = 0; //inicializo la variable semáforo (indica si la operación fue exitosa o no)
        if (insertarCoche != null) { //compruebo que el coche no sea null
            json = gson.toJson(insertarCoche); //convierto el objeto coche a una cadena JSON
            doc = Document.parse(json); //parseo la cadena JSON a un documento BSON
            collection.insertOne(doc); //inserto el documento BSON en la colección coches
            semaforo = 1; //establezco el semáforo en 1, indicando que la inserción fue exitosa
        }
        return semaforo; //devuelvo el semáforo
    }//insertarCoche


    //método para eliminar un coche de la base de datos
    public static int eliminarCoche(Coche eliminarCoche) {
        Gson gson = new Gson(); //creo un objeto Gson para convertir el coche a JSON
        int semaforo = 0; //inicializo la variable semáforo (indica si la operación fue exitosa o no)
        if (eliminarCoche != null) { //compruebo que el coche no sea null
            json = gson.toJson(eliminarCoche); //convierto el objeto coche a una cadena JSON
            doc = Document.parse(json); //parseo la cadena JSON a un documento BSON
            collection.deleteOne(doc); //elimino el documento BSON en la colección coches
            semaforo = 1; //establezco el semáforo en 1, indicando que la inserción fue exitosa
        }
        return semaforo; //devuelvo el semáforo
    }//eliminarCoche


    //método para actualizar un coche en la base de datos
    public static int actualizarCoche(Coche actualizarCoche) {
        int semaforo = 0; //inicializo la variable semáforo (indica si la operación fue exitosa o no)
        try {
            Gson gson = new Gson(); //creo un objeto Gson para convertir el coche a JSON
            json = gson.toJson(actualizarCoche); //convierto el objeto coche a una cadena JSON

            //busco el coche en la colección por su matrícula --> la matrícula es única y voy a actualizar el resto de datos
            Document filtro = new Document("matricula", actualizarCoche.getMatricula()); //filtro almacena la matrícula del coche

            //actualizaciones: solo se actualizan marca, modelo y tipo
            Document actualizaciones = new Document()
                    .append("marca", actualizarCoche.getMarca()) //actualizo la marca
                    .append("modelo", actualizarCoche.getModelo()) //actualizo el modelo
                    .append("tipo", actualizarCoche.getTipo()); //actualizo el tipo

            //realizo la actualización (busca por matrícula y actualiza los demás campos)
            collection.updateOne(filtro, new Document("$set", actualizaciones)); //actualizo los campos en base al filtro de matrícula --> filtro: busca el coche por matrícula, y new Document("$set", actualizaciones): documento con la estructura $set, que indica los campos que quiero cambiar (marca, modelo y tipo)

            semaforo = 1; //operación exitosa --> semáforo a 1
        } catch (Exception e) {
            System.out.println("ha habido un error: " + e.getMessage()); //manejo de errores si falla la actualización
        }
        return semaforo; //devuelvo el semáforo
    }//actualizarCoche


    //método para consultar en la base de datos si ya existe una matrícula --> solo utilizada en el Controller cuando quiero insertar porque no la necesito para más (no se puede modificar la matrícula una vez insertado el coche)
    public static int existeMatricula(String matricula) {
        Document comprobacionMatricula = new Document("matricula", matricula); //creo un documento que filtra por la matrícula
        Document result = collection.find(comprobacionMatricula).first(); //busco el primer documento que coincida con la matrícula
        return result != null ? 1 : 0; //si hay un resultado, la matrícula ya existe (semaforo = 1), si no permite la acción que se quiera realizar
    }//existeMatricula
}//class