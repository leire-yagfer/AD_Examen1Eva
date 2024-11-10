package com.juan.CRUD_Hibernate_cfg.DAO;

import com.juan.CRUD_Hibernate_cfg.Modelo.Profesor;
import org.hibernate.Session;

import java.util.List;

public class DAOProfesor {


    public static  void insertarProfesor(Session session,Profesor f)
    {
        session.beginTransaction();
        session.save(f);

        session.getTransaction().commit();
    }
    public static  void modificarProfesor(Session session,Profesor f)
    {
        session.beginTransaction();
        session.update(f);
        session.getTransaction().commit();
    }
    public static  void borrarProfesor(Session session,Profesor f)
    {

        session.beginTransaction();

        session.delete(f);

        session.getTransaction().commit();
    }
    public static  void listarProfesores(Session session)
    {
        List<Profesor> lista = session.createQuery("from Profesor").getResultList();

        for (Profesor p:lista) {
            // formato clasico
            System.out.println(p.toString());

        }

        //list.forEach(System.out::println);//version 1.8 de java
    }
    public static  Profesor buscarProfesor(Session session,int id)
    {
        Profesor p;

        p=(Profesor) session.get(Profesor.class,id);
        System.out.println(p.getId());
        System.out.println(p.getNombre());
        System.out.println(p.getApe1());
        System.out.println(p.getApe2());
        return p;
    }

}
