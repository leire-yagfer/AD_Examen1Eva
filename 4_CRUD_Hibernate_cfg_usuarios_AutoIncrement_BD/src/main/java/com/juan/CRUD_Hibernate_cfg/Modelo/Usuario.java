package com.juan.CRUD_Hibernate_cfg.Modelo;
import javax.persistence.*;
import java.time.LocalDate;


import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id                    // Marca el campo como la clave de la tabla
    //Se basa en una columna de base de datos con incremento automático y
    // permite que la base de datos genere un nuevo valor con cada operación de inserción


    @GeneratedValue(strategy= GenerationType.IDENTITY) //al poner esta linea --> genera el id del usuario

    @Column(name="id")
    private int id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="apellidos")
    private String apellidos;

    @Column(name="fechaNacimiento")
    private LocalDate fechaNacimiento;

    public Usuario() {

    }

    public Usuario(int id, String nombre, String apellidos, LocalDate fechaNacimiento) {

        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}
