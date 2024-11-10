package org.example.Model;

//clase persona que contiene los datos del titular de la cuenta
public class Persona implements Imprimible {

    //ATRIBUTOS
    private String nombre;
    private String apellidos;
    private String dni;

    //CONSTRUCTOR
    public Persona(String nombre, String apellido, String dni) {
        this.nombre = nombre;
        this.apellidos = apellido;
        this.dni = dni;
    }


    //MÉTODOS
    //método que añado al haber heredado la clase CuentaCorriente
    @Override
    public String devolverInfoString() {
        return "Titular: " + nombre + ", apellidos: " + apellidos + ", dni: " + dni + "\n";
    }//devolverInfoString
}//class
