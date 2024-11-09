package org.example.ad_entrega2_tiendacosmetica_javafx.Util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.ad_entrega2_tiendacosmetica_javafx.HelloApplication;

import javax.swing.*;


public class utilidades {

    // Función para mostrar alertas cuando lo necesite
    public static void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo); // utilizo el tipo de alerta pasado como parámetro
        alert.setTitle("Total.");
        alert.setContentText(mensaje); //mensaje que muestra cuando salta la alerta, que paso como parámetro
        alert.showAndWait(); //si no se cierra la ventana, no me permite continuar
    }//mostrarAlerta


    // Función para cambiar la escena a un nuevo FXML
    public static void cambiarEscena(Button boton, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile)); // Verifica la ruta correcta de Compras.fxml --> obtengo un controlador
            Parent root = fxmlLoader.load(); // Carga el archivo FXML
            Scene scene = new Scene(root); // Crea una nueva escena con el archivo FXML cargado
            Stage stage = (Stage) boton.getScene().getWindow(); // Obtén la ventana (Stage) desde el botón
            stage.setScene(scene); // Establece la nueva escena en la ventana actual
        } catch (Exception e) {
            System.out.println("Error al cambiar la escena." + e.getMessage());
        }//catch
    }//cambiarEscena


    //función para salir del programa
    public static void salir() {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea salir del prgrama?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) { //Si la opción elegida es "Sí" se cierra
            System.exit(0); //Cierro la aplicación
        }
    }//salir


}//class
