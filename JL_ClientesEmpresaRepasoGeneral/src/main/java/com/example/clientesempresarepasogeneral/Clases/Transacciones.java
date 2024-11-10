package com.example.clientesempresarepasogeneral.Clases;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
@Entity
@Table(name = "transacciones")
public class Transacciones implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    int id;
    @Column(name = "fecha")
    LocalDate fecha;

    @Column(name = "nombreUsuario")
    String nombre;

    @Column(name = "precio")
    double precio;

    @Column(name = "tipo")
    String tipo;

    @ManyToOne
    /*cliente_id es la clave foránea que establece la relación con la tabla clientes.
    La columna cliente_id en transacciones se usa para almacenar el id del cliente al que pertenece cada transacción.*/
    @JoinColumn(name = "cliente_id", updatable = false, insertable = false)
    private Clientes cliente; //cliente es lo que pongo el mappedBy

    public Transacciones(LocalDate fecha, double precio, String tipo) {
        this.fecha = fecha;
        this.precio = precio;
        this.tipo = tipo;
    }

    public Transacciones(LocalDate fecha, String nombre, double precio, String tipo) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
    }

    public Transacciones(int id, LocalDate fecha, double precio, String tipo, Clientes cliente) {
        this.id = id;
        this.fecha = fecha;
        this.precio = precio;
        this.tipo = tipo;
        this.cliente = cliente;
    }

    public Transacciones(LocalDate fecha, double precio, String tipo, Clientes cliente) {
        this.fecha = fecha;
        this.precio = precio;
        this.tipo = tipo;
        this.cliente = cliente;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Transacciones{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", precio=" + precio +
                ", tipo='" + tipo + '\'' +
                ", cliente=" + cliente +
                '}';
    }
}
