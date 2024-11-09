package org.example.ad_entrega1_periodicoonline_javafx.Model;

public abstract class Cliente { //clase absatracta porque como dice el enunciadfo "no se van a poder instancias objetos"

    //ATRIBUTOS
    private String identificador;
    private String contrasena;
    private double importe_descuento; //cantidad decimal, no porcentaje


    //CONSTRUCTOR
    //VACÍO
    public Cliente() {
    }

    //CON PARÁMETROS
    public Cliente(String identificador, String contrasena, Double importe_descuento) {
        this.identificador = identificador;
        this.contrasena = contrasena;
        this.importe_descuento = importe_descuento;
    }


    //GET Y SET
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Double getImporte_descuento() {
        return importe_descuento;
    }

    public void setImporte_descuento(Double importe_descuento) {
        this.importe_descuento = importe_descuento;
    }
}//class