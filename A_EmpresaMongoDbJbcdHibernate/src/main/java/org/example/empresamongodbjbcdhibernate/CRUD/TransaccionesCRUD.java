package org.example.empresamongodbjbcdhibernate.CRUD;

import org.example.empresamongodbjbcdhibernate.clases.Clientes;
import org.example.empresamongodbjbcdhibernate.clases.Transacciones;
import org.example.empresamongodbjbcdhibernate.utils.Alerta;
import org.example.empresamongodbjbcdhibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransaccionesCRUD {
    SessionFactory factory = HibernateUtil.getSessionFactory(); //Conectarse con hibernate

    public List<Transacciones>getTransaccionesPorClienteHibernate(Clientes clientes){
        List<Transacciones> transacciones = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = factory.openSession()){
            transaction = session.beginTransaction();
            transacciones = session.createQuery("from Transacciones WHERE clientes.id = :id ", Transacciones.class).setParameter("id", clientes.getId()).list();
            transaction.commit();
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
        }
        return transacciones;
    }
    public boolean inertarTransiccionHibernate(Transacciones transacciones){
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.save(transacciones);
            transaction.commit();
            return true;
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
        }
        return false;
    }
    public boolean eliminarTransiccionHibernate(Transacciones transacciones){
        Transaction transaction = null;
        try (Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.delete(transacciones);
            transaction.commit();
            return true;
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
        }
        return false;
    }
    public boolean actualizarTransiccionHibernate(Transacciones transacciones){
        Transaction transaction = null;
        try (Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.update(transacciones);
            transaction.commit();
            return true;
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
        }
        return false;
    }
}
