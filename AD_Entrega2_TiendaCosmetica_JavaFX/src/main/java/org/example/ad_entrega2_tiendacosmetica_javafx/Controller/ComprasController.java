package org.example.ad_entrega2_tiendacosmetica_javafx.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.example.ad_entrega2_tiendacosmetica_javafx.Clases.Ventas;
import org.example.ad_entrega2_tiendacosmetica_javafx.DAO.VentasDAO;
import org.example.ad_entrega2_tiendacosmetica_javafx.HelloApplication;
import org.example.ad_entrega2_tiendacosmetica_javafx.Util.utilidades;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ComprasController implements Initializable {

    //ATRIBUTOS
    @FXML
    private Button botonComprar;

    @FXML
    private Button botonAtras;

    @FXML
    private ComboBox<Integer> cantidadCB;

    @FXML
    private RadioButton cremaRB;

    @FXML
    private RadioButton labialRB;

    @FXML
    private ToggleGroup productos;

    @FXML
    private RadioButton serumRB;

    double precioCrema = 20.67;
    double precioLabial = 9.49;
    double precioSerum = 17.99;
    double precioTotal;

    String recogidaNombreUsuario;


    //MÉTODOS
    //método que se ejecuta cuando se carga el FXML --> inicializo el ComboBox
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> cantidades = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10); //creo una lista de números del 1 al 10
        cantidadCB.setItems(cantidades); //asigno la lista al ComboBox
        cantidadCB.setValue(1); //establezco como valor predeterminado el 1
    }//initialize


    //método que me devuelve el nombre del producto que se va a comprar para después introducirlo en la base de datos en la tabla ventas
    public String devolverNombreProducto(){
        String nombreProducto = "";
        if(cremaRB.isSelected()) nombreProducto += cremaRB.getText();
        else if(labialRB.isSelected()) nombreProducto += labialRB.getText();
        else if(serumRB.isSelected()) nombreProducto += serumRB.getText();

        return nombreProducto;
    }//devolverNombreYCantidadProducto


    //método que me devuelve el nombre y cantidad de producto en formato String que me va a facilitar luego cuando le llame desde ticketController para mostrar el ticket
    public String devolverNombreYCantidadProducto(){
        String nombreYCantidadProducto = "";
        if(cremaRB.isSelected()) nombreYCantidadProducto += "Cantidad comprada de " + cremaRB.getText().toLowerCase() + ": " + cantidadCB.getValue();
        else if(labialRB.isSelected()) nombreYCantidadProducto += "Cantidad comprada de " + labialRB.getText().toLowerCase() + ": " + cantidadCB.getValue();
        else if(serumRB.isSelected()) nombreYCantidadProducto += "Cantidad comprada de " + serumRB.getText().toLowerCase() + ": " + cantidadCB.getValue();

        return nombreYCantidadProducto;
    }//devolverNombreYCantidadProducto


    //método que me calcula el precio total de la compra
    public double calculoCantidadComprada(){
        //obtengo la cantidad de productos que se quieren comprar para realizar luego la operación
        int cantidadAComprar = cantidadCB.getValue();

        if(cremaRB.isSelected()) precioTotal = cantidadAComprar * precioCrema;
        else if(labialRB.isSelected()) precioTotal = cantidadAComprar * precioLabial;
        else if(serumRB.isSelected()) precioTotal = cantidadAComprar * precioSerum;

        return precioTotal;
    }//calculoCantidadComprada



    //metodo que me facilita el nombre de usuario del cliente que va a realizar la compra
    public void cargaNombreUsuario(String nombreUsuario){
        recogidaNombreUsuario = nombreUsuario;
    }


    //método que se activa cuando se pulsa el botoón "Comprar"
    @FXML
    void onComprarClick(ActionEvent event) {
        //en este caso no llamo al método creado para cambiar de escena porque necesito realizar unos cambios
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Ticket.fxml"));
            Parent root = fxmlLoader.load();

            //como necesito compartir datos de esta clase a la clase de TicketController realizo lo siguiente, permitiendo
            //así, que el controlador TicketController acceda a los datos o métodos de este controlador (de esta clase)
            TicketController controller = fxmlLoader.getController(); //obtengo el controlador de la escena Ticket.fxml
            controller.mostrarTicket(this); //le paso la instancia del controlador de compras (this) al métododo mostrarTicket ya que necesita algún método de esta clase para realizar correctamente el ticket

            Scene scene = new Scene(root);
            Stage stage = (Stage) botonComprar.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Error al cambiar la escena. " + e.getMessage());
        }//try-catch

        //realizo el registro de la venta
        Ventas nuevaVenta = new Ventas(recogidaNombreUsuario, devolverNombreProducto(), LocalDateTime.now(), cantidadCB.getValue(), calculoCantidadComprada()); //creo un objeto de venta con los datos obtenidos de la interfaz gráfica y de otras clases
        int resultado = VentasDAO.registrarVenta(nuevaVenta); //llamo al método para registrar la venta en la base de datos
    }//onComprarClick


    //método que se activa cuando se pulsa el botoón "Salir"
    @FXML
    void onAtrasClick(ActionEvent event) {
        utilidades.cambiarEscena(botonAtras, "Principal.fxml");
    }//onAtrasClick
}//class