package com.juan.CRUD_Hibernate_cfg.DAO;

import com.juan.CRUD_Hibernate_cfg.Modelo.Usuario;
import org.hibernate.Session;

import java.util.List;

public class DAOUsuario {

    public static  void insertarUsuario(Session session, Usuario u)
    {

        session.beginTransaction();
        session.save(u);
        session.getTransaction().commit();
    }
    public static  void modificarUsuario(Session session,Usuario f)
    {
        session.beginTransaction();
        session.update(f);
        session.getTransaction().commit();
    }
    public static  void borrarUsuario(Session session,Usuario f)
    {

        session.beginTransaction();

        session.delete(f);

        session.getTransaction().commit();
    }
    public static  void listarUsuarios(Session session)
    {
        List<Usuario> lista = session.createQuery("from Usuario").getResultList();

        for (Usuario p:lista) {
            // formato clasico
            System.out.println(p.toString());

        }

        //list.forEach(System.out::println);//version 1.8 de java
    }
    public static  Usuario buscarUsuario(Session session,int id)
    {
        Usuario p;

        p=(Usuario) session.get(Usuario.class,id);
        /*session.get(Usuario.class, id): Aquí, se utiliza el método get() de Hibernate para recuperar un objeto de la
        base de datos. El primer parámetro es la clase de entidad (Usuario.class), y el segundo es el identificador del
        objeto que estamos buscando (en este caso, el id del usuario).
        Este método retorna el objeto con ese id de la base de datos si lo encuentra, o null si no existe un usuario
        con ese id. Luego, el resultado se asigna a la variable p.
        (Usuario): El resultado de session.get() es un Object, por lo que se hace un casting explícito a Usuario para
        asegurarse de que el tipo del objeto es el esperado.*/
        System.out.println(p.getId());
        System.out.println(p.getNombre());
        System.out.println(p.getApellidos());
        System.out.println(p.getFechaNacimiento());
        return p;
    }

}
