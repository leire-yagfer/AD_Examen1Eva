package org.example.util;

import java.util.Scanner;

public class comprobaciones {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
    }


    //MÉTODOS
    //método para comprobar que el saldo inicial introcido es positivo
    public static void comprobacionSaldoPositivo(Scanner sc, double s) {
        while (s <= 0) {
            System.out.print("Ingrese un saldo positivo: ");
            s = sc.nextDouble();
            sc.nextLine();
        }//while
    }//comprobacionSaldoPositivo


    //método para comprobar que el interes introcido es positivo
    public static void comprobacionInteresPositivo(Scanner sc, double i) {
        while (i <= 0) {
            System.out.print("Ingrese un interés positivo: ");
            i = sc.nextDouble();
            sc.nextLine();
        }//while
    }//comprobacionInteresPositivo


    //método para comprobar que la comisión introcida es positiva
    public static void comprobacionComisionPositiva(Scanner sc, double c) {
        while (c <= 0) {
            System.out.print("Ingrese una comisión positiva: ");
            c = sc.nextDouble();
            sc.nextLine();
        }//while
    }//comprobacionComisionPositiva


    //método para validar el IBAN
    public static boolean validarIban(String iban) {
        //expresión regular para validar el IBAN --> buscado en internet
        return iban.matches("^ES[0-9]{20}$"); //devuelve true si el IBAN es válido
    }//validarIban


    //método para validar el DNI
    public static boolean validarDni(String dni) {
        //expresión regular para validar el DNI tenga (8 dígitos seguidos de una letra) --> buscado en internet
        return dni.matches("\\d{8}[A-Z]");
    }//validarDni
}//class