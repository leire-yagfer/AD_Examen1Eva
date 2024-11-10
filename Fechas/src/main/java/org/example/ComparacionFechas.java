package org.example;

import java.time.LocalDate;

public class ComparacionFechas {
    public static void main(String[] args) {
        LocalDate primeraFecha = LocalDate.of(2019, 1, 1);
        LocalDate segundaFecha = LocalDate.of(2020, 1, 1);
        LocalDate terceraFecha = LocalDate.of(2020, 1, 1);

        /*
        negativo: el valor de la izquierda es menor
        0: ambos iguales
        positivo: el valor de la izquierda es mayor*/
        if (primeraFecha.compareTo(segundaFecha) < 0){
            System.out.println("La fecha " + primeraFecha + " es menor que " + segundaFecha);
        }

        if (terceraFecha.compareTo(segundaFecha) == 0){
            System.out.println("La fecha " + terceraFecha + " es igual que " + segundaFecha);
        }

        if (segundaFecha.compareTo(primeraFecha) > 0){
            System.out.println("La fecha " + primeraFecha + " es mayor que " + primeraFecha);
        }
    }
}
