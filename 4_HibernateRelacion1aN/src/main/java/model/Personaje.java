package model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Clase que mapea un objeto Personaje con la tabla de MySQL correspondiente, utilizando anotaciones

 */
@Entity
@Table(name="personaje")
public class Personaje implements Serializable {
/*
La documentación oficial de Hibernate , al describir el mapeo en Hibernate,
señala que el campo al que se hace referencia debe ser serializable
 cuando usamos referencedColumnName de la anotación @JoinColumn .
 Normalmente, este campo es una clave principal en otra entidad.
 */
	@Id					// Marca el campo como la clave de la tabla
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id_personaje")  // Opcional si coinciden atributo y columna. 
						// Indica la columna de la tabla que corresponde con este atributo
	private int id_personaje;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="nivel")
	private int nivel;
	
	@Column(name="energia")
	private int energia;
	
	@Column(name="puntos")
	private int puntos;
	
	@ManyToOne
	@JoinColumn(name="id_arma",referencedColumnName="id_arma")
	private Arma arma;// un personaje solo puede tener un arma
	
	// Constructor vacío. Hibernate puede mostrar algún error si no está implementado
	public Personaje() {}

	//aunque ponga el id como requisito en el constructor, cada uno tendra su id autogenerado -->
		//1. se puede crear un nuevo constructor para que no de error en la creación de nuevos objetos de la clase
		//2. quitar el id del constructor // UTILIZO ESTA
	public Personaje(String nombre, int nivel, int energia, int puntos, Arma arma) {
		this.nombre = nombre;
		this.nivel = nivel;
		this.energia = energia;
		this.puntos = puntos;
		this.arma = arma;
	}
	
	
	public int getId() {
		return id_personaje;
	}
	
	public void setId(int id_personaje) {
		this.id_personaje = id_personaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public int getEnergia() {
		return energia;
	}
	
	public void setEnergia(int energia) {
		this.energia = energia;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public Arma getArma() {
		return arma;
	}
	

	public void setArma(Arma arma) {
		this.arma = arma;
	}

	@Override
	public String toString() {
		return "Personaje [id=" + id_personaje + ", nombre=" + nombre + ", nivel=" + nivel + ", energia=" + energia + ", puntos="
				+ puntos + ", arma=" + arma + "]";
	}
	
	
}
