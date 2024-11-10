package org.example.ad_entrega7_bueno_crudcoches_hibernate1an_javafx.clases;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity //indica que esta clase es una entidad de base de datos
@Table(name="coches") //especifica el nombre de la tabla en la base de datos
public class Coches implements Serializable {
    @Id //define el campo como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-incrementa el valor del id
    @Column(name = "id") //especifica el nombre de la columna en la tabla
    private int id;

    @Column(name = "matricula") //define la columna matricula en la tabla coches
    private String matricula;

    @Column(name = "marca") //define la columna marca en la tabla coches
    private String marca;

    @Column(name = "modelo") //define la columna modelo en la tabla coches
    private String modelo;

    @Column(name = "tipo") //define la columna tipo en la tabla coches
    private String tipo;

    @OneToMany(mappedBy = "coches", cascade = CascadeType.ALL, orphanRemoval = true) //relación de uno a muchos con la entidad multas
    List<Multas> lista;

    public Coches() {
    }

    //constructor con id y detalles del coche
    public Coches(int id, String matricula, String marca, String modelo, String tipo) {
        this.id = id;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
    }

    //constructor sin id para creación de nuevos coches
    public Coches(String matricula, String marca, String modelo, String tipo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
    }

    //métodos getter y setter para cada campo
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() { //método para mostrar detalles del coche como string
        return "Coches{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}