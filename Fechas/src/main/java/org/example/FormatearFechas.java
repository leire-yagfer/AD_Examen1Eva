package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormatearFechas {
    public static void main(String[] args) {
        LocalDate fecha = LocalDate.now();

        System.out.println("Fecha original: " + fecha);

        // Definir el formato
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        // Formatear la fecha
        String fechaFormateada = fecha.format(formatter);
        String fechaFormateada2 = fecha.format(formatter2);

        // Mostrar fechas formateadas
        System.out.println("Fecha con formato: " + fechaFormateada);
        System.out.println("Fecha con formato: " + fechaFormateada2);
    }
}