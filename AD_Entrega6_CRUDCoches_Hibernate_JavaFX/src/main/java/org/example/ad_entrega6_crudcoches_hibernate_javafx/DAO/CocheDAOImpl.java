package org.example.ad_entrega6_crudcoches_hibernate_javafx.DAO;

import org.example.ad_entrega6_crudcoches_hibernate_javafx.Model.Coche;
import org.example.ad_entrega6_crudcoches_hibernate_javafx.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CocheDAOImpl implements CocheDAO {

    //método para obtener todos los coches almacenados en la base de datos
    @Override
    public List<Coche> mostrarCoches() {
        Transaction transaction = null; //inicializo la transacción
        List<Coche> coches = new ArrayList<>(); //inicializo la lista de coches
        try (Session session = HibernateUtil.getSession()) { //utilizo HibernateUtil para obtener la sesión
            transaction = session.beginTransaction();
            coches = session.createQuery("from Coche", Coche.class).list(); //creo la consulta y obtengo la lista de coches
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); //si la transacción no es nula, hago rollback
            System.err.println("Error al mostrar coches: " + e.getMessage()); //imprimir mensaje de error
        }
        return coches; //devuelvo la lista de coches
    }//mostrarCoches


    //método para insertar un nuevo coche en la base de datos
    @Override
    public int insertarCoche(Coche insertarCoche) {
        int semaforo = 0; //variable para controlar el estado de la operación
        Transaction transaction = null; //inicializo la transacción como nula
        try (Session session = HibernateUtil.getSession()) { //abro la sesión
            transaction = session.beginTransaction();
            session.save(insertarCoche); //guardo el coche en la base de datos
            transaction.commit();
            semaforo = 1; //operación exitosa
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); //si la transacción no es nula, hago rollback
            System.err.println("Error al insertar coche: " + e.getMessage());
        }
        return semaforo; //devuelvo el estado de la operación
    }//insertarCoche


    //método para eliminar un coche de la base de datos
    @Override
    public int eliminarCoche(Coche eliminarCoche) {
        int semaforo = 0; //variable para controlar el estado de la operación
        Transaction transaction = null; //inicializo la transacción como nula
        try (Session session = HibernateUtil.getSession()) { //abro la sesión
            transaction = session.beginTransaction();
            session.delete(eliminarCoche); //elimino el coche
            transaction.commit();
            semaforo = 1; //operación exitosa
        } catch (Exception e) { //capturo excepciones
            if (transaction != null) transaction.rollback(); //si la transacción no es nula, hago rollback
            System.err.println("Error al eliminar coche: " + e.getMessage());
        }
        return semaforo; //devuelvo el estado de la operación
    }//eliminarCoche


    //método para actualizar un coche en la base de datos
    @Override
    public int actualizarCoche(Coche actualizarCoche) {
        int semaforo = 0; //variable para controlar el estado de la operación
        Transaction transaction = null; //inicializo la transacción como nula
        try (Session session = HibernateUtil.getSession()) { //abro la sesión
            transaction = session.beginTransaction();
            session.update(actualizarCoche); //actualizo el coche en la base de datos
            transaction.commit();
            semaforo = 1; //operación exitosa
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); //si la transacción no es nula, hago rollback
            System.err.println("Error al actualizar coche: " + e.getMessage());
        }
        return semaforo; //devuelvo el estado de la operación
    }//actualizarCoche


    //método para verificar si una matrícula ya existe en la base de datos
    @Override
    public int existeMatricula(String matricula) {
        try (Session session = HibernateUtil.getSession()) { //abro la sesión
            Query<Coche> query = session.createQuery("from Coche where matricula = :matricula", Coche.class); //creo la consulta para buscar la matrícula
            query.setParameter("matricula", matricula); //asigno el valor del parámetro
            return query.uniqueResult() != null ? 1 : 0; //devuelvo 1 si existe, 0 si no
        } catch (Exception e) { //capturo excepciones
            System.err.println("Error al verificar matrícula: " + e.getMessage()); //imprimo el error
        }
        return 0; //devuelvo 0 en caso de error
    }//existeMatricula
}//class