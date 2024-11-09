package org.example.ad_entrega2_tiendacosmetica_javafx.Clases;

public class Clientes {

    //ATRIBUTOS
    private int idCliente;
    private String nombreUsuario;
    private String email;


    //CONSTRUCTOR --> sin id porque es auto-increment
    public Clientes(String nombreUsuario, String email) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
    }


    //GET Y SET
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}//class