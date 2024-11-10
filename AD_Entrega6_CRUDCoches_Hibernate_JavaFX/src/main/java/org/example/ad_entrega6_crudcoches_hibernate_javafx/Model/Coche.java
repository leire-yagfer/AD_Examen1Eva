package org.example.ad_entrega6_crudcoches_hibernate_javafx.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //indica que esta clase es una entidad mapeada a una tabla en la base de datos
@Table(name = "COCHES") //especifica el nombre de la tabla en la base de datos
public class Coche {

    //ATRIBUTOS
    @Id //indica que matricula es la clave primaria de la entidad
    @Column(name = "matricula")
    private String matricula;
    @Column(name = "marca")
    private String marca;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "tipo")
    private String tipo;


    //CONSTRUCTOR
    public Coche() {
    }

    public Coche(String matricula, String marca, String modelo, String tipo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
    }


    //GETTER Y SETTER
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
}//class