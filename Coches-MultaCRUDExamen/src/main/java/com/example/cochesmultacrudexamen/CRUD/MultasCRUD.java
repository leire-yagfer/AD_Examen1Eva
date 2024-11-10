package com.example.cochesmultacrudexamen.CRUD;

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
    SessionFactory factory = HibernateUtil.getSessionFactory();

    public boolean insertaMulta(Multas multas) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(multas);
            transaction.commit();
            return true;
        } catch (Exception e) {
            Alerta.alertaError(e.getMessage());
        }
        return false;
    }

    public boolean eliminarMulta(Multas multas) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(multas);
            transaction.commit();
            return true;
        } catch (Exception e) {
            Alerta.alertaError(e.getMessage());
        }
        return false;
    }

    public boolean actualizarMulta(Multas multas) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.update(multas);
            transaction.commit();
            return true;
        } catch (Exception e) {
            Alerta.alertaError(e.getMessage());
        }
        return false;
    }

    public ArrayList<Multas> getMultas() {
        ArrayList<Multas> listaMultas = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            List<Multas> multas = session.createQuery("from Multas", Multas.class).getResultList();
            listaMultas.addAll(multas);
            transaction.commit();
        } catch (Exception e) {
            Alerta.alertaError(e.getMessage());
        }
        return listaMultas;
    }

    public List<Multas> obtenerMultas(Coches coches) {
        List<Multas> listadoMultas = null;
        if (coches==null){
            System.out.println("error el coche es nulo");
        }
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            listadoMultas = session.createQuery("from Multas WHERE coches.matricula = :matricula", Multas.class).setParameter("matricula", coches.getMatricula()).list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listadoMultas;
    }

    public boolean comprobarIdMulta(int id) {
        ArrayList<Multas> lista = getMultas();
        for (Multas multas : lista) {
            if (multas.getId_multa() == id) {
                return true;
            }
        }
        return false;
    }
}
