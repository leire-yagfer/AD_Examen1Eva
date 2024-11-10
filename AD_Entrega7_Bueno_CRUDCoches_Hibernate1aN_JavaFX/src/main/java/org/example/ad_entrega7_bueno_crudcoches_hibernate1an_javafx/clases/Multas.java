package org.example.ad_entrega7_bueno_crudcoches_hibernate1an_javafx.clases;

import javax.persistence.*;
import java.time.LocalDate;

@Entity //indica que esta clase es una entidad de base de datos
@Table(name = "multas") //especifica el nombre de la tabla en la base de datos
public class Multas {
    @Id //define el campo como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-incrementa el valor del id_multa
    @Column(name = "id_multa") //especifica el nombre de la columna en la tabla
    private int id_multa;

    @Column(name = "precio") //define la columna precio en la tabla multas
    private double precio;

    @Column(name = "fecha") //define la columna fecha en la tabla multas
    private LocalDate fecha;

    @Column(name = "matricula") //define la columna matricula en la tabla multas
    private String matricula;

    @ManyToOne //relación de muchos a uno con la entidad coches
    @JoinColumn(name = "matricula", referencedColumnName = "matricula", insertable = false, updatable = false) //especifica la clave foránea y evita actualizaciones e inserciones directas
    private Coches coches;

    public Multas() {
    }

    //constructor con id_multa y detalles de multa y coche
    public Multas(int id_multa, double precio, LocalDate fecha, String matricula, Coches coches) {
        this.id_multa = id_multa;
        this.precio = precio;
        this.fecha = fecha;
        this.matricula = matricula;
        this.coches = coches;
    }

    //constructor con id_multa y detalles de multa sin el objeto coche
    public Multas(int id_multa, double precio, LocalDate fecha, String matricula) {
        this.id_multa = id_multa;
        this.precio = precio;
        this.fecha = fecha;
        this.matricula = matricula;
    }

    //constructor sin id_multa para creación de nuevas multas
    public Multas(double precio, LocalDate fecha, String matricula) {
        this.precio = precio;
        this.fecha = fecha;
        this.matricula = matricula;
    }

    //constructor sin id_multa y con objeto coche
    public Multas(double precio, LocalDate fecha, String matricula, Coches coches) {
        this.precio = precio;
        this.fecha = fecha;
        this.matricula = matricula;
        this.coches = coches;
    }

    //métodos getter y setter para cada campo
    public int getId_multa() {
        return id_multa;
    }

    public void setId_multa(int id_multa) {
        this.id_multa = id_multa;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Coches getCoches() {
        return coches;
    }

    public void setCoches(Coches coches) {
        this.coches = coches;
    }

    @Override
    public String toString() { //método para mostrar detalles de multa como string
        return "Multas{" +
                "id_multa=" + id_multa +
                ", precio=" + precio +
                ", fecha=" + fecha +
                ", matricula='" + matricula + '\'' +
                ", coches=" + coches +
                '}';
    }
}