package org.example.empresamongodbjbcdhibernate.clases;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "transacciones")
public class Transacciones implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "cantidad")
    private double cantidad;

    @Column(name = "tipo")
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Clientes clientes;


    public Transacciones(LocalDate fecha, double cantidad, String tipo) {
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.tipo = tipo;
    }

    public Transacciones(int id, LocalDate fecha, double precio, String tipo, Clientes clientes) {
        this.id = id;
        this.fecha = fecha;
        this.cantidad = precio;
        this.tipo = tipo;
        this.clientes = clientes;
    }

    public Transacciones(LocalDate fecha, double precio, String tipo, Clientes clientes) {
        this.fecha = fecha;
        this.cantidad = precio;
        this.tipo = tipo;
        this.clientes = clientes;
    }

    public Transacciones() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    @Override
    public String toString() {
        return "Transacciones{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", precio=" + cantidad +
                ", tipo='" + tipo + '\'' +
                ", clientes=" + clientes +
                '}';
    }
}
