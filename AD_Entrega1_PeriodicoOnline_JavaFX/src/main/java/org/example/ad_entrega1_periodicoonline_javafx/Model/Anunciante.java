package org.example.ad_entrega1_periodicoonline_javafx.Model;

public class Anunciante extends Cliente {

    //ATRIBUTO
    private int numAnuncios;


    //CONSTRUCTOR
    //VACÍO
    public Anunciante(int numAnuncios) {
        this.numAnuncios = numAnuncios;
    }

    //CON PARÁMETROS
    public Anunciante(String identificador, String contrasena, Double importe_descuento, int numAnuncios) {
        super(identificador, contrasena, importe_descuento);
        this.numAnuncios = numAnuncios;
    }


    //GET Y SET
    public int getNumAnuncios() {
        return numAnuncios;
    }

    public void setNumAnuncios(int numAnuncios) {
        this.numAnuncios = numAnuncios;
    }
}//class