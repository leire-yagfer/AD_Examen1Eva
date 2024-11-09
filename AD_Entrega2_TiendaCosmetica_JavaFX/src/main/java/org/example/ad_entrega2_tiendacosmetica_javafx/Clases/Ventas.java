package org.example.ad_entrega2_tiendacosmetica_javafx.Clases;

import java.time.LocalDateTime;

public class Ventas {

    //ATRIBUTOS
    private int idVenta;
    private String nombreUsuarioCliente;
    private String nombreProducto;
    private LocalDateTime fecha_venta; //fecha con hora
    private int cantidad;
    private double total;


    //CONSTRUCTOR
    public Ventas(String nombreUsuarioCliente, String idProducto, LocalDateTime fecha_venta, int cantidad, double total) {
        this.nombreUsuarioCliente = nombreUsuarioCliente;
        this.nombreProducto = idProducto;
        this.fecha_venta = fecha_venta;
        this.cantidad = cantidad;
        this.total = total;
    }


    //GET Y SET
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNombreUsuarioCliente() {
        return nombreUsuarioCliente;
    }

    public void setNombreUsuarioCliente(String nombreUsuarioCliente) {
        this.nombreUsuarioCliente = nombreUsuarioCliente;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public LocalDateTime getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(LocalDateTime fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}//class
