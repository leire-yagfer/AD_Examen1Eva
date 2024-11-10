package org.example.ad_entrega6_crudcoches_hibernate_javafx.Util;

import org.example.ad_entrega6_crudcoches_hibernate_javafx.Model.Coche;
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