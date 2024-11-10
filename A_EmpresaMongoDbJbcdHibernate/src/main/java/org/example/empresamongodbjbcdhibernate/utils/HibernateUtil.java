package org.example.empresamongodbjbcdhibernate.utils;

import org.example.empresamongodbjbcdhibernate.clases.Clientes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    static SessionFactory factory = null;
    static {
        Configuration cfg = new Configuration();
        cfg.configure("configuration/hibernate.cfg.xml");
        // Se registran las clases que hay que MAPEAR con cada tabla de la base de datos
        cfg.addAnnotatedClass(Clientes.class);
        cfg.addAnnotatedClass(Transaction.class);

        factory = cfg.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }
}