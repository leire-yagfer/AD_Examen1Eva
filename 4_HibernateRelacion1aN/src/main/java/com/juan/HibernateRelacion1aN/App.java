package com.juan.HibernateRelacion1aN;

import java.util.List;

import model.Arma;
import model.Personaje;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

public class App {
	public static void main(String[] args) {
		// SessionFactoryes una instancia que creará objetos de tipo Sessiono(fabrica
		// sessiones).

		SessionFactory factory = HibernateUtil.getSessionFactory();

		// Ahora que ya tenemos el objeto SessionFactory podemos obtener la Session
		// para trabajar con Hibernate.

		// Una sesión se utiliza para obtener una conexión física con una base de datos.
		// El objeto Session es liviano y está diseñado para ser instanciado
		// cada vez que se necesita una interacción con la base de datos.
		// Los objetos persistentes se guardan y recuperan a través de un objeto de
		// sesión.

		// Los objetos de la sesión no deben mantenerse abiertos durante mucho tiempo
		// porque generalmente no son seguros para subprocesos y deben crearse y
		// destruirse
		// según sea necesario. La función principal de la sesión
		// es ofrecer, crear, leer y eliminar operaciones para instancias
		// de clases de entidades asignadas.

		Session session = HibernateUtil.getSession();
		//si os fijais en el constructor de la clase padre no esta a lista, es decir NO
		//se utiliza para nada a la hora de insertar. Solo la utiliza para uso interno.
		Arma arma1 = new Arma(28, "rayos", "rayos x en los ojos", 10); //aunque de el id = 28, como se autogenera, tendrá el id que le corresponda
		Arma armaPruebaExtra = new Arma(30, "prueba", "prueba extra", 30);

		session.beginTransaction();
		session.save(arma1);
		session.save(armaPruebaExtra);
		session.getTransaction().commit();

		// el resto de operaciones CRUD se haria como en el ejemplo anterior con una
		// sola clase y tabla

		//podriamos seleccionar un objeto de la clase padre de otra forma sin crearlo, es decir uno 
		//que ya exista como por ejemplo el que tiene id=1
		Arma arma2 = session.get(Arma.class, 1); //select que me devuelve el arma seleccionado (id = 1)
		
		
		//ahora insertamos un personaje, tener en cuenta que aqui si es necesario insertar un objeto del padre.
		//de alguna de las 2 formas que os he exlicado antes.
		Personaje personaje1 = new Personaje();
		personaje1.setNombre("Mortadelo");
		personaje1.setNivel(120);
		personaje1.setEnergia(5);
		personaje1.setPuntos(250);
		personaje1.setArma(arma1);
		//personaje1.setArma(arma2);

		Personaje personajeExtra = new Personaje("Leire", 250, 10, 30, armaPruebaExtra);

		session.beginTransaction();
		session.save(personaje1);
		session.save(personajeExtra);
		session.getTransaction().commit();

		// modificar
		// 1 buscamos el personaje a modificar, suponemos que hemos introducido el 2
		Personaje personaje_aux = (Personaje) session.createQuery("FROM Personaje  WHERE id_personaje = 2").uniqueResult();

		if (personaje_aux != null) { //si existe el personaje
			System.out.println(personaje_aux.toString());
			// modifico el campo nombre por poner un ejemplo
			personaje_aux.setNombre("Rodolfo");

			session.beginTransaction();
			session.update(personaje_aux);
			session.getTransaction().commit();
		} else
			System.out.println("Personaje NO encontrado");

		// eliminar al peronaje id=4
		personaje_aux = (Personaje) session.createQuery("FROM Personaje  WHERE id_personaje = 4").uniqueResult();
		session.beginTransaction();
		session.delete(personaje_aux);
		session.getTransaction().commit();

		// listar
		List<Personaje> lista = session.createQuery("from Personaje").getResultList();


		for (Personaje p : lista) {
			// formato clasico
			System.out.println(p.toString());

		}
		session.close();

	}
}
