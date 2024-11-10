package com.juan.CRUD_Hibernate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



import com.juan.dao.UserDao;
import com.juan.dao.UserDaoImpl;
import com.juan.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.juan.util.HibernateUtil;

public class App {
	static SessionFactory factory = null;
	public static void main(String[] args) {

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = HibernateUtil.getSession();


		UserDaoImpl dao = new UserDaoImpl();
		//o bien de esta otra forma. Mira que el tipo de objeto puede ser de tipo Interface, pero después de new hayq ue poner la clase que usa la BD, no el interface
		UserDao dao1=new UserDaoImpl();
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				
				System.out.println("==================MENU=================");
				System.out.println("1. Crear un nuevo Usuario");
				System.out.println("2. Visualizar a un Usuario");
				System.out.println("3. Ver TODOS los Usuarios");
				System.out.println("4. Modificar los datos de un usuario");
				System.out.println("5. Borrar un usuario");
				System.out.println("6. Exit");
				System.out.print("Introduce la opcion elegida: ");
				int choice = Integer.valueOf(input.readLine());

				switch (choice) {
				case 1: {
					System.out.print("Introduce el nombre: ");
					String firstname = input.readLine().trim();
					System.out.print("Introduce los apellidos: ");
					String lastname = input.readLine().trim();
					System.out.println();
					System.out.println("Introduce la fecha de nacimiento en formato dd/MM/yyyy");

					String fecha = input.readLine().trim();
					User user = new User(firstname, lastname,  LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					/*
					LocalDate hoy = LocalDate.now();
        			LocalTime ahora = LocalTime.now();

        			LocalDateTime fecha = LocalDateTime.of(hoy, ahora);

        			System.out.println(fecha);

					 */
					System.out.println("\nAñadiento al usuario.........");
					dao.saveUser(user,session);
					System.out.println("Usuario Añadido satisfactoriamente!");
					break;
				}
				case 2: {
					System.out.print("Introduce el id del usuario: ");
					int id = Integer.valueOf(input.readLine());
					User usuario = dao.getUserById(id, session);
					if (usuario != null)
						System.out.println(usuario);
					else
						System.out.println("********* Ese usuario NO se encuentra en la BD *******");
					
					
					break;
					
				}
				case 3: {
					for(User usuario :dao.getAllUsers(session)) {
						System.out.println(usuario);
					}


					
					break;
				}
				case 4: {
					System.out.println("Introduce el id del usuario: ");
					int id = Integer.valueOf(input.readLine());
					User user = dao.getUserById(id, session);
					if (user == null) {
						System.out.println("Lo siento! Ese usuario NO EXISTE.");
						break;
					}
					System.out.println("Dejar en blanco si no desea cambiar.");
					System.out.print("Introduce el nombre: ");
					String firstname = input.readLine().trim();
					if (firstname != "")
						user.setFirstname(firstname);
					System.out.print("Introduce los apellidos: ");
					String lastname = input.readLine().trim();
					if (lastname != "")
						user.setLastname(lastname);
					System.out.println();
					System.out.println("Introduce la fecha de nacimiento en formato yyyy-mm-dd");


					String fecha1 = input.readLine().trim();
					if (fecha1 != "")
						user.setDob(LocalDate.parse(fecha1, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					System.out.println("\nModificando al usuario.........");
					dao.updateUser(user,session);
					System.out.println("Cambios realizados correctamente!");
					break;
				}
				case 5: {
					System.out.println("Introduce el id del usuario: ");
					int id = Integer.valueOf(input.readLine());
					System.out.println("Borrando al usuario.......");
					dao.deleteUserById(id,session);
					System.out.println("Usuario borrado satisfactoriamente!");
					break;
				}
				case 6:
					System.exit(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}//main
	
	
}
