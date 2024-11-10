package org.example.ad_entrega7_bueno_crudcoches_hibernate1an_javafx.utils;

import com.example.cochesmultacrudexamen.clases.Coches;
import com.example.cochesmultacrudexamen.clases.Multas;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    static SessionFactory factory = null;
    static {
        Configuration cfg = new Configuration();
        cfg.configure("configuration/hibernate.cfg.xml");
        // Se registran las clases que hay que MAPEAR con cada tabla de la base de datos
        cfg.addAnnotatedClass(Coches.class);
        cfg.addAnnotatedClass(Multas.class);

        factory = cfg.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }
}