package com.juan.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.juan.model.User;

public  class UserDaoImpl implements UserDao{


	//DIFERENCIAS entre SessionFactory y session
	//******************************************
	//SessionFactory es una clase de fábrica para Session objetos.
	// Está disponible para toda la aplicación, mientras que Session
	// solo está disponible para una transacción en particular.
	//SI nuestra aplicación tiene más de una base de datos,
	// entonces deberíamos crear tantas fábricas de sesiones (SessionFactory) como nuestro número de bases de datos.

	//Session es de corta duración mientras que SessionFactory los objetos son de larga duración.

	//Una sesión se utiliza para obtener una conexión física con una base de datos.
	// El objeto Session es liviano y está diseñado para crear una instancia cada vez que se necesita
	// una interacción con la base de datos.
	// Los objetos persistentes se guardan y recuperan a través de un objeto de sesión.

	//
	//Los objetos de sesión no deben mantenerse abiertos durante mucho tiempo
	// porque generalmente no son seguros para subprocesos y deben crearse y destruirse según sea necesario.
	// La función principal de la sesión es ofrecer, crear, leer y eliminar operaciones
	// para instancias de clases de entidad asignadas.
	//private SessionFactory factory = HibernateConf.getFactory();
	/*
	 * Para trabajar con una base de datos usamos las transacciones.
	 *  En hibernate es tan sencillo como:

		1.- Crear una nueva transacción llamando al método beginTransaction() de la sesión:
		2.- Hacer un commit de la transacción actual llamando al método commit() de la transacción actual de la sesión:
		3.- Hacer un rollback de la transacción actual llamando al método rollback() de la transacción actual de la sesión:
	 * 
	 */

	//Si es necesario realizar una transacción con la base de datos,
	// se obtiene un objeto de sesión de corta duración de la instancia de Session Factory adecuada y,
	// una vez completada la transacción, la instancia ya no está disponible.

	/*
	
	Diferencia entre los métodos save, update y saveOrUpdate() de hibernate
	
	save

		El método Guardar almacena un objeto en la base de datos. Eso significa que inserta una entrada si el identificador no existe; de ​​lo contrario, arrojará un error. 
		Si la clave principal ya está presente en la tabla, no se puede insertar.

		El método save() devuelve el identificador generado, por lo que debe ejecutar inmediatamente la instrucción SQL INSERT.
		Ejemplo

		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmail("demo-user@email.com");
		employee.setFirstName("demo");
		employee.setLastName("user");

		Long id = session.save(employee);

		Esto ejecutará la instrucción SQL INSERT.

			Hibernate: insert into Employee (ID, email, firstName, lastName) values (default, ?, ?, ?)

	update

		El método de actualización en hibernación se utiliza para actualizar el objeto mediante el identificador. Si el identificador falta o no existe, se generará una excepción.

	saveOrUpdate()

		Este método llama a save() o update() según la operación. Si el identificador existe, llamará al método de actualización; de lo contrario, se llamará al método de guardar. 



	https://howtodoinjava.com/hibernate/hibernate-save-and-saveorupdate/
		
 */
	

	public  void saveUser(User user,Session session) {
		Transaction transaction = null;
		try{
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
	}
	

	public  User getUserById(int id,Session session) {
		Transaction transaction = null;
		User user = null;
		try {
			transaction = session.beginTransaction();
			user = session.get(User.class, id);
			transaction.commit();
			
				
		} catch (Exception e) {
			
			if(transaction != null)
				transaction.rollback();
		}
		return user;
	}
	
	

	@SuppressWarnings("unchecked")
	public  List<User> getAllUsers(Session session) {
		Transaction transaction = null;
		List<User> users = null;
		try {
			transaction = session.beginTransaction();
			users = session.createQuery("from User").list();
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
		return users;
	}
	

	public  void updateUser(User user,Session session) {
		Transaction transaction = null;
		try{
			transaction = session.beginTransaction();
			session.saveOrUpdate(user);
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
	}
	

	public  void deleteUserById(int id,Session session) {
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			User user = session.get(User.class, id);
			session.delete(user);
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
		}
	}

}
