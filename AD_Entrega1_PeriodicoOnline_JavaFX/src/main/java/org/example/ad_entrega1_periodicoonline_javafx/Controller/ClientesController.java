package org.example.ad_entrega1_periodicoonline_javafx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.ad_entrega1_periodicoonline_javafx.Model.Alertas;
import org.example.ad_entrega1_periodicoonline_javafx.Model.Usuario;

import javax.swing.*;
import java.util.ArrayList;

public class ClientesController {

    //ATRIBUTOS
    @FXML
    private ToggleGroup clientePremium;

    @FXML
    private TextField contrasenaTF;

    @FXML
    private TextField descuentoTF;

    @FXML
    private TextField usuarioCorreoTF;

    private ArrayList<Usuario> listaUsuarios = new ArrayList<>(); //ArrayList para almacenar los usuarios


    //MÉTODOS
    @FXML
    void onAnadirCliente() {
        //obtengo los datos de los campos de texto
        String correo = usuarioCorreoTF.getText();
        String contrasena = contrasenaTF.getText();
        double descuento; //voy a coger lo que está marcado en el campo descuento
        double importe; //importe real (importe = costeFijo - descuento)

        //si uno de los campos usuario o contraseña están vacíos --> alerta
        if (correo.isEmpty() || contrasena.isEmpty()) {
            Alertas.mostrarAlerta("Los campos usuario y contraseña son obligatorios.", Alert.AlertType.ERROR);
            return; //detengo la ejecución si faltan datos
        }

        //hago try-catch para convertir el descuento a Double, y si no es un número va al catch y salta la alerta
        try {
            descuento = Double.parseDouble(descuentoTF.getText());
        } catch (NumberFormatException e) {
            Alertas.mostrarAlerta("El importe del descuento debe ser un número válido. Si no tiene descuento, ponga 0.0.", Alert.AlertType.ERROR);
            return; //se sale y continúa con el programa
        }

        //veo si el cliente es premium o no
        RadioButton premiumSeleccionado = (RadioButton) clientePremium.getSelectedToggle(); //convierto lo seleccionado a RadioButton
        boolean esPremium = premiumSeleccionado.getText().equalsIgnoreCase("Sí"); //esPremium es true cuando está seleccionado "Sí"
        if (esPremium) {
            importe = 35.5 - descuento;
        } else {
            importe = 20.5 - descuento;
        }

        //compruebo que el importe final (precioFijo - descuento) no es negativo
        if (importe < 0) {
            Alertas.mostrarAlerta("El descuento no puede ser mayor que el coste.", Alert.AlertType.ERROR);
            return; //detengo si el importe es negativo
        }

        //creo el nuevo usuario con los datos obtenidos y lo añado al ArrayList
        Usuario nuevoUsuario = new Usuario(correo, contrasena, importe, esPremium); //creo el usuario con el importe real (tarifa-descuento)
        listaUsuarios.add(nuevoUsuario);

        //muestro mensaje de confirmación
        Alertas.mostrarAlerta("Usuario creado correctamente", Alert.AlertType.INFORMATION);

        //limpio los campos de texto
        usuarioCorreoTF.clear();
        contrasenaTF.clear();
        descuentoTF.clear();
    }//onAnadirCliente


    @FXML
    void onBuscarCliente() {
        String correo = usuarioCorreoTF.getText();

        //si el campo usuario está vacío --> alerta
        if (correo.isEmpty()) {
            Alertas.mostrarAlerta("El campo usuario es obligatorio.", Alert.AlertType.ERROR);
            return; //detengo si no hay correo introducido
        }

        //recorro el ArrayList para encontrar al cliente
        for (Usuario usuario : listaUsuarios) {
            if (correo.equalsIgnoreCase(usuario.getIdentificador())) { //no tengo en cuenta mayúsculas o minúsculas
                Alertas.mostrarAlerta("Cliente encontrado. \n Datos: " + usuario, Alert.AlertType.INFORMATION); //usuario muestra los datos del toString de la clase Usuario
                return; //salgo del for al encontrar el usuario buscado
            }
        }

        //si no se encuentra el usuario
        Alertas.mostrarAlerta("Cliente no encontrado.", Alert.AlertType.ERROR);

        //limpio todos los campos por si se hubiese escrito en algún otro campo
        usuarioCorreoTF.clear();
        contrasenaTF.clear();
        descuentoTF.clear();
    }//onBuscarCliente


    @FXML
    void onIngresosTotales() {
        double ingresoTotal = 0.0;
        for (Usuario usuario : listaUsuarios) {
            ingresoTotal += usuario.getImporte_descuento();
        }
        Alertas.mostrarAlerta("Ingresos totales: " + ingresoTotal, Alert.AlertType.INFORMATION);
    }//onIngresosTotales


    @FXML
    void onSalir() {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) { //Si la opción elegida es "Sí" se cierra
            System.exit(0); //Cierro la aplicación
        }
    }//onSalir
}//class