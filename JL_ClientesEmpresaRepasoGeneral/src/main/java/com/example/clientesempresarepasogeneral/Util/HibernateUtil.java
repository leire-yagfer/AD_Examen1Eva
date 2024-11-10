package com.example.clientesempresarepasogeneral.Util;

import com.example.clientesempresarepasogeneral.Clases.Clientes;
import com.example.clientesempresarepasogeneral.Clases.Transacciones;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    static SessionFactory factory = null;
    static {
        Configuration cfg = new Configuration();
        cfg.configure("configuration/hibernate.cfg.xml");


        cfg.addAnnotatedClass(Transacciones.class);
        cfg.addAnnotatedClass(Clientes.class);
        factory = cfg.buildSessionFactory();
        System.out.println("Conectado");
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }
}
