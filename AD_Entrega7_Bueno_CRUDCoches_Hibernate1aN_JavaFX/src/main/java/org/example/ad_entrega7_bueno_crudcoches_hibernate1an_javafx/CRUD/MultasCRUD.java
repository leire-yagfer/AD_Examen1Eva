package org.example.ad_entrega7_bueno_crudcoches_hibernate1an_javafx.CRUD;

import com.example.cochesmultacrudexamen.clases.Coches;
import com.example.cochesmultacrudexamen.clases.Multas;
import com.example.cochesmultacrudexamen.utils.Alerta;
import com.example.cochesmultacrudexamen.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MultasCRUD implements MultasCRUDimpl{
    //crea la fábrica de sesiones de hibernate
    SessionFactory factory = HibernateUtil.getSessionFactory();

    //método para insertar una multa en la base de datos
    public boolean insertaMulta(Multas multas) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction(); //inicia la transacción
            session.save(multas); //guarda la multa en la base de datos
            transaction.commit(); //confirma la transacción
            return true; //devuelve true si la inserción fue exitosa
        } catch (Exception e) {
            Alerta.alertaError(e.getMessage()); //muestra alerta en caso de error
        }
        return false; //devuelve false si ocurre un error
    }

    //método para eliminar una multa de la base de datos
    public boolean eliminarMulta(Multas multas) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction(); //inicia la transacción
            session.delete(multas); //elimina la multa de la base de datos
            transaction.commit(); //confirma la transacción
            return true; //devuelve true si la eliminación fue exitosa
        } catch (Exception e) {
            Alerta.alertaError(e.getMessage()); //muestra alerta en caso de error
        }
        return false; //devuelve false si ocurre un error
    }

    //método para actualizar una multa en la base de datos
    public boolean actualizarMulta(Multas multas) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction(); //inicia la transacción
            session.update(multas); //actualiza la multa en la base de datos
            transaction.commit(); //confirma la transacción
            return true; //devuelve true si la actualización fue exitosa
        } catch (Exception e) {
            Alerta.alertaError(e.getMessage()); //muestra alerta en caso de error
        }
        return false; //devuelve false si ocurre un error
    }

    //método para obtener todas las multas de la base de datos
    public ArrayList<Multas> getMultas() {
        ArrayList<Multas> listaMultas = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction(); //inicia la transacción
            List<Multas> multas = session.createQuery("from Multas", Multas.class).getResultList(); //obtiene las multas de la base de datos
            listaMultas.addAll(multas); //agrega las multas a la lista
            transaction.commit(); //confirma la transacción
        } catch (Exception e) {
            Alerta.alertaError(e.getMessage()); //muestra alerta en caso de error
        }
        return listaMultas; //devuelve la lista de multas
    }

    //método para obtener las multas asociadas a un coche específico
    public List<Multas> obtenerMultas(Coches coches) {
        List<Multas> listadoMultas = null;
        if (coches == null) {
            System.out.println("error el coche es nulo"); //muestra error si el coche es nulo
        }
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction(); //inicia la transacción
            listadoMultas = session.createQuery("from Multas WHERE coches.matricula = :matricula", Multas.class) //obtiene las multas asociadas al coche --> Consulta las multas (Multas)
                    // y filtra los resultados en función de la matrícula del coche (coches.matricula). :matricula: Es un parámetro que se reemplaza con el valor real de la matrícula del coche proporcionado.
                    .setParameter("matricula", coches.getMatricula()).list();
                        /*setParameter("matricula", coches.getMatricula()): Reemplaza el parámetro :matricula en la consulta con la matrícula del coche (que se obtiene a través de coches.getMatricula()).
                         .list(): Ejecuta la consulta y devuelve los resultados como una lista de objetos Multas. Esta lista contiene todas las multas asociadas a la matrícula del coche proporcionado.*/
            transaction.commit(); //confirma la transacción
        } catch (Exception e) {
            e.printStackTrace(); //imprime la traza de la excepción si ocurre un error
        }
        return listadoMultas; //devuelve la lista de multas
    }

    //método para comprobar si un id de multa existe
    public boolean comprobarIdMulta(int id) {
        ArrayList<Multas> lista = getMultas(); //obtiene toda la lista de multas y comprueba si el id existe o no
        for (Multas multas : lista) {
            if (multas.getId_multa() == id) { //compara el id de la multa
                return true; //devuelve true si el id existe
            }
        }
        return false; //devuelve false si el id no existe
    }
}