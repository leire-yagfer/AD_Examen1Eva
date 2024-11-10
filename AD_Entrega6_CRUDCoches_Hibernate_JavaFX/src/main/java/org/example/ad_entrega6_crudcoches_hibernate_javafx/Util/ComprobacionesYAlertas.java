package org.example.ad_entrega6_crudcoches_hibernate_javafx.Util;

import javafx.scene.control.Alert;

public class ComprobacionesYAlertas {

    //método para las alertas que quiera introducir en mi programa --> evito demasiado código
    public static void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // utilizo el tipo de alerta pasado como parámetro
        alert.setTitle("Error");
        alert.setContentText(mensaje); //mensaje que muestra cuando salta la alerta, que paso como parámetro
        alert.showAndWait(); //si no se cierra la ventana, no me permite continuar
    }//mostrarAlerta


    //método que comprueba el formato de la matrícula
    public static boolean validarMatricula(String matricula) {
        //expresión regular que verifica 4 dígitos seguidos de 3 consonantes mayúsculas
        String regex = "^[0-9]{4}[BCDFGHJKLMNPQRSTVWXYZ]{3}$";
        //comprueba si la matrícula coincide con el patrón
        return matricula != null && matricula.matches(regex);
    }//validarMatricula
}//class