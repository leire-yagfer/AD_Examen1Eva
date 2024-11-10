package org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.DAO;

import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Model.Coche;
import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Model.Multa;
import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Util.ComprobacionesAlertasCambioEscena;
import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MultaDAOImpl implements MultaDAO {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    //método para obtener todas las multas almacenados en la base de datos
    @Override
    public List<Multa> mostrarMultas(Coche obtenerMultasCocheSeleccionado) {
        Transaction transaction = null; //inicializo la transacción
        List<Multa> multas = new ArrayList<>(); //inicializo la lista de multas
        if (obtenerMultasCocheSeleccionado == null) { //compruebo que hay un coche seleccionado
            ComprobacionesAlertasCambioEscena.mostrarAlerta("Se debe seleccionar un coche para ver sus multas.");
        } else {
            try (Session session = factory.openSession()) {
                transaction = session.beginTransaction();
                multas = session.createQuery("from Multa m where m.coche.matricula = :matricula", Multa.class).setParameter("matricula", obtenerMultasCocheSeleccionado.getMatricula()).list();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback(); //si la transacción no es nula, hago rollback
                System.err.println("Error al mostrar multas: " + e.getMessage()); //imprimir mensaje de error
            }
        }
        return multas; //devuelvo la lista de coches
    }//mostrarMultas



    //método para insertar una nueva multa en la base de datos
    @Override
    public int insertarMulta(Multa insertarMulta, Coche coche) {
        int semaforo = 0; //variable para controlar el estado de la operación
        Transaction transaction = null; //inicializo la transacción como nula
        try (Session session = factory.openSession()) { //abro la sesión
            transaction = session.beginTransaction();

            //tengo que asegurarme de asignar la matrícula de la multa antes de guardarla.
            insertarMulta.setCoche(coche); // Asignamos el coche con la matrícula

            session.save(insertarMulta); //guardo el coche en la base de datos
            transaction.commit();
            semaforo = 1; //operación exitosa
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); //si la transacción no es nula, hago rollback
            System.err.println("Error al insertar coche: " + e.getMessage());
        }
        return semaforo; //devuelvo el estado de la operación
    }//insertarMulta


    //método para eliminar una multa de la base de datos
    public int eliminarMulta(Multa eliminarMulta) {
        int semaforo = 0; //variable para controlar el estado de la operación
        Transaction transaction = null; //inicializo la transacción como nula
        try (Session session = factory.openSession()) { //abro la sesión
            transaction = session.beginTransaction();
            session.delete(eliminarMulta); //elimino la multa
            transaction.commit();
            semaforo = 1; //operación exitosa
        } catch (Exception e) { //capturo excepciones
            if (transaction != null) transaction.rollback(); //si la transacción no es nula, hago rollback
            System.err.println("Error al eliminar la multa: " + e.getMessage());
        }
        return semaforo; //devuelvo el estado de la operación
    }//eliminarMulta


    //método para actualizar una multa en la base de datos
    @Override
    public int actualizarMulta(Multa actualizarMulta) {
        int semaforo = 0; //variable para controlar el estado de la operación
        Transaction transaction = null; //inicializo la transacción como nula
        try (Session session = factory.openSession()) { //abro la sesión
            transaction = session.beginTransaction();
            session.update(actualizarMulta); //actualizo la multa en la base de datos
            transaction.commit();
            semaforo = 1; //operación exitosa
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); //si la transacción no es nula, hago rollback
            System.err.println("Error al actualizar la multa: " + e.getMessage());
        }
        return semaforo; //devuelvo el estado de la operación
    }//actualizarMulta
}//class
