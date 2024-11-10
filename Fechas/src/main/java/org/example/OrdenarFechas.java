package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class OrdenarFechas {
    public static void main(String[] args) {
        LocalDate[] fechas = new LocalDate[10];

        fechas[0] = LocalDate.of(2024, 1, 1);
        fechas[1] = LocalDate.of(2023, 12, 25);
        fechas[2] = LocalDate.of(2022, 7, 14);
        fechas[3] = LocalDate.of(2021, 5, 20);
        fechas[4] = LocalDate.of(2020, 8, 5);
        fechas[5] = LocalDate.of(2019, 3, 10);
        fechas[6] = LocalDate.of(2018, 11, 22);
        fechas[7] = LocalDate.of(2017, 6, 30);
        fechas[8] = LocalDate.of(2016, 2, 18);
        fechas[9] = LocalDate.of(2015, 9, 7);

        for (LocalDate fecha : fechas) {
            System.out.println(fecha);
        }

        // Convertir el array a una lista para usar Collections.sort
        List<LocalDate> listaFechas = new ArrayList<>(Arrays.asList(fechas));

        // Ordenar por día (ascendente)
        listaFechas.sort(Comparator.comparing(LocalDate::getDayOfMonth));
        System.out.println("Ordenadas por día (ascendente):");
        listaFechas.forEach(System.out::println);

        // Ordenar por mes (ascendente)
        listaFechas.sort(Comparator.comparing(LocalDate::getMonthValue));
        System.out.println("\nOrdenadas por mes (ascendente):");
        listaFechas.forEach(System.out::println);

        // Ordenar por año (ascendente)
        listaFechas.sort(Comparator.comparing(LocalDate::getYear));
        System.out.println("\nOrdenadas por año (ascendente):");
        listaFechas.forEach(System.out::println);

        // Ordenar por año, mes y día (descendente)
        listaFechas.sort(Comparator.comparing(LocalDate::getYear)
                .thenComparing(LocalDate::getMonthValue)
                .thenComparing(LocalDate::getDayOfMonth)
                .reversed());
        System.out.println("\nOrdenadas por año, mes y día (descendente):");
        listaFechas.forEach(System.out::println);
    }
}
