package model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase que representa las armas de los Personajes
 * 
 */
@Entity
@Table(name="armas")
public class Arma {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id_arma")
	private int id_arma;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="dano")
	private int dano;
	
	//La anotación mappedBy apunta a la entidad propietaria de la relación.
	//en este caso hay que fijarse el nombre que tiene en la clase personaje el objeto arma
	//que veras esto: private Arma arma;
	
	
	//CascadeType.ALL
	//Propaga todas las operaciones de una entidad, a la entidad con la que se relaciona. 
	//Es decir, que si insertamos, actualizamos o eliminamos una entidad, 
	//también se aplican estas operaciones a la entidad que se relaciona.
	//es un simil a UPDATE CASCADE, DELETE CASCADE
	
	//se podrian poner de forma individual. Aqui teneis las opciones 
	//https://www.baeldung.com/jpa-cascade-types
	
	@OneToMany(mappedBy = "arma", cascade = CascadeType.ALL)
	//mappedBy = "arma" indica que la relación está mapeada desde el atributo arma de la clase Personaje
	private List<Personaje> personajes;//un arma puede ser utilizada  por muchos personajes
	
	public Arma() {}

	//aunque ponga el id como requisito en el constructor, cada uno tendra su id autogenerado -->
		//1. se puede crear un nuevo constructor para que no de error en la creación de nuevos objetos de la clase // UTILIZO ESTA
		//2. quitar el id del constructor
	public Arma(int id_arma, String nombre, String descripcion, int dano) {
		super();
		this.id_arma = id_arma;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.dano = dano;
	}

	public Arma(String nombre, String descripcion, int dano, List<Personaje> personajes) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.dano = dano;
		this.personajes = personajes;
	}

	public int getId() {
		return id_arma;
	}

	public void setId(int id_arma) {
		this.id_arma = id_arma;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}
	
	public List<Personaje> getPersonajes() {
		return personajes;
	}
	
	public void setPersonaje(List<Personaje> personajes) {
		this.personajes = personajes;
	}
	
	
}
