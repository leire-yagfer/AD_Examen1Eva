package org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Model;


import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity // indica que esta clase es una entidad mapeada a una tabla en la base de datos
@Table(name = "multas") // especifica el nombre de la tabla en la base de datos
public class Multa {

    // ATRIBUTOS
    @Id // indica que id_multa es la clave primaria de la entidad
    @GeneratedValue(strategy = IDENTITY) // es autoincrement --> genera un id
    @Column(name="id_multa")
    private int id_multa;

    @Column(name = "precio")
    private String precio;

    @Column(name = "fecha")
    private LocalDate fecha;

    //el atributo matricula no es necesario, ya que se gestiona a través de la relación ManyToOne


    @ManyToOne // utilizo Mto1 porque un coche (one) puede tener varias multas (many)
    @JoinColumn(name = "matricula", referencedColumnName = "matricula", insertable = false, updatable = false)
    // name es el atributo de la tabla multas que guarda la clave foránea (matricula)
    // referencedColumnName es el atributo en la tabla coches a la que apunta la clave foránea (matricula en coches).
    // insertable = false y updatable = false indican que la columna 'matricula' no se debe insertar ni actualizar desde esta clase
    private Coche coche;

    // CONSTRUCTOR
    public Multa() {
    }

    public Multa(String precio, LocalDate fecha, Coche coche) {
        this.precio = precio;
        this.fecha = fecha;
        this.coche = coche;
    }

    public Multa(String precio, LocalDate fecha) {
        this.precio = precio;
        this.fecha = fecha;
    }

    // GETTER Y SETTER
    public int getId_multa() {
        return id_multa;
    }

    public void setId_multa(int id_multa) {
        this.id_multa = id_multa;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Coche getCoche() {
        return coche;
    }

    public void setCoche(Coche coche) {
        this.coche = coche;
    }
}
