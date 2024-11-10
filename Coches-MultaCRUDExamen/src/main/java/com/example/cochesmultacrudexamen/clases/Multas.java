package com.example.cochesmultacrudexamen.clases;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "multas")
public class Multas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_multa")
    private int id_multa;

    @Column(name = "precio")
    private double precio;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "matricula")
    private String matricula;

    @ManyToOne
    @JoinColumn(name = "matricula",referencedColumnName = "matricula", insertable = false, updatable = false)
    private Coches coches;

    public Multas() {
    }

    public Multas(int id_multa, double precio, LocalDate fecha, String matricula, Coches coches) {
        this.id_multa = id_multa;
        this.precio = precio;
        this.fecha = fecha;
        this.matricula = matricula;
        this.coches = coches;
    }

    public Multas(int id_multa, double precio, LocalDate fecha, String matricula) {
        this.id_multa = id_multa;
        this.precio = precio;
        this.fecha = fecha;
        this.matricula = matricula;
    }

    public Multas(double precio, LocalDate fecha, String matricula) {
        this.precio = precio;
        this.fecha = fecha;
        this.matricula = matricula;
    }

    public Multas(double precio, LocalDate fecha, String matricula, Coches coches) {
        this.precio = precio;
        this.fecha = fecha;
        this.matricula = matricula;
        this.coches = coches;
    }

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
    public String toString() {
        return "Multas{" +
                "id_multa=" + id_multa +
                ", precio=" + precio +
                ", fecha=" + fecha +
                ", matricula='" + matricula + '\'' +
                ", coches=" + coches +
                '}';
    }
}
