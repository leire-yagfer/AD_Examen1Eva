package org.example.ad_entrega2_tiendacosmetica_javafx.Clases;

public class Productos {

    //ATRIBUTOS
    private int idProducto;
    private String nombreProducto;
    private double precio;


    //CONSTRUCTOR --> sin id porque es auto-increment
    public Productos(String nombreProducto, double precio) {
        this.nombreProducto = nombreProducto;
        this.precio = precio;
    }


    //GET Y SET
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}//class