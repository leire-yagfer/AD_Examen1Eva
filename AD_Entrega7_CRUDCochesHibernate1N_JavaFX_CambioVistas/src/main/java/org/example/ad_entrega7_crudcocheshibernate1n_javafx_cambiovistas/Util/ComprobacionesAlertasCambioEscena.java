package org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.App;

import java.io.IOException;

public class ComprobacionesAlertasCambioEscena {//método para las alertas que quiera introducir en mi programa --> evito demasiado código

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



    //método que valida el precio introducido (puede contener decimales, hasta 2)
    public static boolean esPrecioValido(String precio) {
        return precio.matches("\\d+(\\.\\d{1,2})?");  // Acepta números con hasta 2 decimales
    }//esPrecioValido



    //método que cambia de escena en el FXML
    public static void cambiarEscena(Button boton, String fxmlFile) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile)); //carga el archivo FXML
            Parent root = fxmlLoader.load(); //carga el archivo FXML

            Scene scene = new Scene(root); //crea la nueva escena
            Stage stage = (Stage) boton.getScene().getWindow(); //obtiene la ventana (Stage) desde el botón
            stage.setScene(scene); //establece la nueva escena en la ventana actual
        } catch (Exception e) {
            System.out.println("Error al cambiar la escena.");
            e.printStackTrace(); //muestra la traza completa de la excepción
        } //try-catch
    } //cambiarEscena
}//class