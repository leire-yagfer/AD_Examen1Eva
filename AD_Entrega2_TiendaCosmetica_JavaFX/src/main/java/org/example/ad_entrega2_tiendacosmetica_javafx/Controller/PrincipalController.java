package org.example.ad_entrega2_tiendacosmetica_javafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.ad_entrega2_tiendacosmetica_javafx.Clases.Clientes;
import org.example.ad_entrega2_tiendacosmetica_javafx.DAO.ClientesDAO;
import org.example.ad_entrega2_tiendacosmetica_javafx.HelloApplication;
import org.example.ad_entrega2_tiendacosmetica_javafx.Util.utilidades;

public class PrincipalController {

    @FXML
    private Button botonEntrar;

    @FXML
    private Button botonRegistro;

    @FXML
    private Button botonSalir;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField nombreTF;


    //MÉTODOS
    //método que cuando ya estoy registrado e introduzco las credenciales correctamente se pasa a la otra pantalla
    @FXML
    void onEntrarClick(ActionEvent event) {
        String nombre = nombreTF.getText();
        String email = emailTF.getText();

        //compruebo que todos los campos están rellenos
        if (nombre.isEmpty() || email.isEmpty()) {
            utilidades.mostrarAlerta("Todos los campos han de estar rellenos.", Alert.AlertType.ERROR);
            return; //se reinicia el programa y no realiza lo que queda de código
        }//if

        //compruebo que los datos son correctos con la base de datos --> tengo un método que devuelve true si son correctos y false si no
        if (ClientesDAO.verificacionDatos(nombre, email)) {
            //si es true --> cambio a otra ventana
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Compras.fxml"));
                Parent root = fxmlLoader.load();

                //como necesito compartir datos de esta clase a la clase de ComprasController realizo lo siguiente, permitiendo
                //así, que el controlador ComprasController acceda a los datos o métodos de este controlador (de esta clase)
                ComprasController controller = fxmlLoader.getController(); //obtengo el controlador de la escena Ticket.fxml
                controller.cargaNombreUsuario(nombre); //le paso el nombre que recojo del textField al  método de la clase ComprasController que lo necesita para crear la venta

                Scene scene = new Scene(root);
                Stage stage = (Stage) botonEntrar.getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception e) {
                System.out.println("Error al cambiar la escena." + e.getMessage());
            }//catch

            //borro los datos de los campos
            nombreTF.clear();
            emailTF.clear();
        } else {
            //si las credenciales no son correctas, muestro un mensaje de error
            utilidades.mostrarAlerta("Nombre de usuario o email incorrectos. Inténtelo de nuevo", Alert.AlertType.ERROR);
        }//else
    }//onEntrarClick


    //método mediante el cual se registra a un nuevo usuario
    @FXML
    void onRegistrarseClick(ActionEvent event) {

        String nombre = nombreTF.getText();
        String email = emailTF.getText();

        //compruebo que todos los campos están rellenos
        if (nombre.isEmpty() || email.isEmpty()) {
            utilidades.mostrarAlerta("Todos los campos deben estar rellenos.", Alert.AlertType.ERROR);
            return; //se reinicia el programa y no realiza lo que queda de código
        }//if


        //creo el nuevo cliente con los datos introducidos
        Clientes nuevoCliente = new Clientes(nombre, email);

        //llamo al metodo insertarCliente en el que compruebo si ya hay otro usuario con ese mismo usuario/email
        if (ClientesDAO.insertarCliente(nuevoCliente) > 0) { //si se ha podido insertar --> columnasAfectadas > 0
            //pongo una alerta de registro correcto
            utilidades.mostrarAlerta("Cliente registrado correctamente.", Alert.AlertType.INFORMATION);
            //BORRo LOS datos de los CAMPOS
            nombreTF.clear();
            emailTF.clear();
        }//if
    }//onRegistrarseClick


    //método para salir de programa
    @FXML
    void onSalirClick(ActionEvent event) {
        utilidades.salir();
    }//onSalirClick
}//class
