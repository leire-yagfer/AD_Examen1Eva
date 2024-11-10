package org.example;

import java.time.LocalDateTime;

public class SacarFechasJava {
    public static void main(String[] args) {
        LocalDateTime fecha = LocalDateTime.now();

        System.out.println(fecha);

        System.out.println("Año " + fecha.getYear());
        System.out.println("Mes " + fecha.getMonth());
        System.out.println("Día " + fecha.getDayOfMonth());
        System.out.println("Día de la semana " + fecha.getDayOfWeek());
        System.out.println("Hora " + fecha.getHour());
        System.out.println("Minuto " + fecha.getMinute());
        System.out.println("Día del año: " + fecha.getDayOfYear());
    }
}
