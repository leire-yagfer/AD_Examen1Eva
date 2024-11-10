package org.example;

import java.time.LocalDate;
import java.time.Period;

public class ComprobarAntiguedad {
    public static void main(String[] args) {
        // Fecha de referencia (puede ser una fecha de nacimiento o cualquier fecha)
        LocalDate fechaInicio = LocalDate.of(1990, 5, 15);

        // Fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Calculamos la antigüedad
        Period antigüedad = Period.between(fechaInicio, fechaActual);

        // Mostramos la antigüedad en años, meses y días
        System.out.println("Antigüedad: " + antigüedad.getYears() + " años, "
                + antigüedad.getMonths() + " meses, "
                + antigüedad.getDays() + " días.");
    }
}
