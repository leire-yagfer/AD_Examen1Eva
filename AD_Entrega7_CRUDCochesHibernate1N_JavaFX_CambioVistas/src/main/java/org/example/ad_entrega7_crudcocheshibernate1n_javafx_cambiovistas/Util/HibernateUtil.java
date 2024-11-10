package org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Util;

import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Model.Coche;
import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Model.Multa;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    //crea instancias de Session, que son las que ejecutan las operaciones CRUD en la BD
    //usaré SessionFactory para abrir Session cuando necesite realizar operaciones con la base de datos
    static SessionFactory factory = null; //variable estática para el SessionFactory

    static {
        try {
            //cargar la configuración desde el archivo hibernate.cfg.xml
            Configuration cfg = new Configuration(); //instancia de la clase Configuration de Hibernate, que representa la configuración necesaria para establecer una conexión con la base de datos y gestionar el mapeo entre las entidades y las tablas
            cfg.configure("Configuration/hibernate.cfg.xml"); //carga la configuración desde el archivo hibernate.cfg.xml, que contiene los parámetros necesarios para la conexión a la BD
            cfg.addAnnotatedClass(Coche.class); //registra la clase coche como entidad
            cfg.addAnnotatedClass(Multa.class);
            factory = cfg.buildSessionFactory(); //construye el sessionfactory
        } catch (Throwable ex) {
            //si algo sale mal, lanzar una excepción
            throw new ExceptionInInitializerError(ex);
        }
    }

    //método para obtener el sessionfactory
    public static SessionFactory getSessionFactory() {
        return factory;
    }

    //método para abrir una nueva sesión
    public static Session getSession() {
        return factory.openSession();
    }
}//class