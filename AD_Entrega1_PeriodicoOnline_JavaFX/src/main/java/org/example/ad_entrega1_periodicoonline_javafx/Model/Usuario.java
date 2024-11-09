package org.example.ad_entrega1_periodicoonline_javafx.Model;

public class Usuario extends Cliente { //herencia de la clase CLiente

    //ATRIBUTO
    private boolean premium;


    //CONSTRUCTOR
    //VACÍO
    public Usuario(boolean premium) {
        this.premium = premium;
    }

    //CON PARÁMETROS
    public Usuario(String identificador, String contrasena, Double importe_descuento, boolean premium) {
        super(identificador, contrasena, importe_descuento);
        this.premium = premium;
    }


    //GET Y SET
    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }


    //TOSTRING --> para cuando se pulse en el botón Buscar, que me muestre la info del cliente
    @Override
    public String toString() {
        return getIdentificador() + ", " + getContrasena() + ", " + getImporte_descuento() + ", " + (isPremium() ? "Sí" : "No"); //hago un operador ternario
    }
}//class