package org.example.ad_entrega1_periodicoonline_javafx.Model;

import javafx.scene.control.Alert;

//creo un método para las alertas que quiera introducir en mi programa --> evito demasiado código
public class Alertas {
    public static void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo); // utilizo el tipo de alerta pasado como parámetro
        alert.setTitle("Total.");
        alert.setContentText(mensaje); //mensaje que muestra cuando salta la alerta, que paso como parámetro
        alert.showAndWait(); //si no se cierra la ventana, no me permite continuar
    }//Alertas
}