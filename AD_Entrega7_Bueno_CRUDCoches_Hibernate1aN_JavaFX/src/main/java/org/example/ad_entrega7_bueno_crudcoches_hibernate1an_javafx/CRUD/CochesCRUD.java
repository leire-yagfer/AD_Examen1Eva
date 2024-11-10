package org.example.ad_entrega7_bueno_crudcoches_hibernate1an_javafx.CRUD;

import com.example.cochesmultacrudexamen.clases.Coches;
import com.example.cochesmultacrudexamen.utils.Alerta;
import com.example.cochesmultacrudexamen.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CochesCRUD implements CochesCRUDimpl{
    //crea la fábrica de sesiones de hibernate
    SessionFactory factory = HibernateUtil.getSessionFactory();

    //método para insertar un coche en la base de datos
    public int insertarCoche(Coches coches){
        Transaction transaction = null;

        try(Session session=factory.openSession()){
            transaction = session.beginTransaction(); //inicia la transacción
            session.save(coches); //guarda el coche en la base de datos
            transaction.commit(); //confirma la transacción
            return 1;
        }catch (Exception e){
            Alerta.alertaError(e.getMessage()); //muestra alerta en caso de error
        }
        return 0; //devuelve 0 si ocurre un error
    }

    //método para comprobar si una matrícula ya existe
    public boolean comprobarMatricula(String matricula){
        ArrayList<Coches>listaCoches= getCoches(); //obtiene la lista de coches
        for (Coches coches : listaCoches){
            if (coches.getMatricula().equals(matricula)){ //compara la matrícula
                return true; //devuelve true si la matrícula existe
            }
        }
        return false; //devuelve false si no existe
    }

    //método para obtener la lista de todos los coches
    public ArrayList<Coches>getCoches(){
        ArrayList<Coches>listaCoches = new ArrayList<>();
        Transaction transaction =null;
        try (Session session = factory.openSession()){
            transaction = session.beginTransaction(); //inicia la transacción
            List<Coches>coches = session.createQuery("from Coches", Coches.class).getResultList(); //obtiene los coches de la base de datos
            listaCoches.addAll(coches); //agrega los coches a la lista
            transaction.commit(); //confirma la transacción
        }catch (Exception e){
            Alerta.alertaError(e.getMessage()); //muestra alerta en caso de error
        }
        return listaCoches; //devuelve la lista de coches
    }

    //método para eliminar un coche de la base de datos
    public boolean eliminarCoche(Coches coches) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction(); //inicia la transacción
            session.delete(coches); //elimina el coche de la base de datos
            transaction.commit(); //confirma la transacción
            return true; //devuelve true si la eliminación fue exitosa
        } catch (Exception e) {
            Alerta.alertaError(e.getMessage()); //muestra alerta en caso de error
        }
        return false; //devuelve false si ocurre un error
    }

    //método para editar un coche en la base de datos
    public boolean editarCoche(Coches coches){
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction(); //inicia la transacción
            session.update(coches); //actualiza el coche en la base de datos
            transaction.commit(); //confirma la transacción
            return true; //devuelve true si la edición fue exitosa
        }catch (Exception e){
            Alerta.alertaError(e.getMessage()); //muestra alerta en caso de error
        }
        return false; //devuelve false si ocurre un error
    }

    //método para comprobar si la matrícula sigue el formato correcto
    public boolean estructuraMatricula(String matricula){
        return matricula.matches("^[0-9]{4}[A-Z]{3}$"); //compara con el formato de matrícula
    }
}