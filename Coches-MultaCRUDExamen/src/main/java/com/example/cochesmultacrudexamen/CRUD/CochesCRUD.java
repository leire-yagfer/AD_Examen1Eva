package com.example.cochesmultacrudexamen.CRUD;

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
    SessionFactory factory = HibernateUtil.getSessionFactory();

    public int insertarCoche(Coches coches){
        Transaction transaction = null;

        try(Session session=factory.openSession()){
            transaction = session.beginTransaction();
            session.save(coches);
            transaction.commit();
            return 1;
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
        }
        return 0;
    }
    public boolean comprobarMatricula(String matricula){
        ArrayList<Coches>listaCoches= getCoches();
        for (Coches coches : listaCoches){
            if (coches.getMatricula().equals(matricula)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Coches>getCoches(){
        ArrayList<Coches>listaCoches = new ArrayList<>();
        Transaction transaction =null;
        try (Session session = factory.openSession()){
            transaction = session.beginTransaction();
            List<Coches>coches = session.createQuery("from Coches", Coches.class).getResultList();
            listaCoches.addAll(coches);
            transaction.commit();
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
        }
        return listaCoches;
    }
    public boolean eliminarCoche(Coches coches) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(coches);
            transaction.commit();
            return true;
        } catch (Exception e) {
            Alerta.alertaError(e.getMessage());
        }
        return false;
    }
    public boolean editarCoche(Coches coches){
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.update(coches);
            transaction.commit();
            return true;
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
        }
        return false;
    }
    public boolean estructuraMatricula(String matricula){
        return matricula.matches("^[0-9]{4}[A-Z]{3}$");
    }
}
