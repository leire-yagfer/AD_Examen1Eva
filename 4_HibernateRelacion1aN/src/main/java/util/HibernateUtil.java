package util;

import model.Arma;
import model.Personaje;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

	static SessionFactory factory = null;
	static {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		// Se registran las clases que hay que MAPEAR con cada tabla de la base de datos
		cfg.addAnnotatedClass(Arma.class);
		cfg.addAnnotatedClass(Personaje.class);

	    //configuration.addAnnotatedClass(Clase1.class);
	   //configuration.addAnnotatedClass(Clase2.class);
	  //configuration.addAnnotatedClass(Clase3.class);

		factory = cfg.buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory() {
		return factory;
	}
	
	public static Session getSession() {
		return factory.openSession();
	}
	
	
}
