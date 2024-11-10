package com.example.clientesempresarepasogeneral.Controller;

import com.example.clientesempresarepasogeneral.Clases.Clientes;
import com.example.clientesempresarepasogeneral.Clases.Sesion;
import com.example.clientesempresarepasogeneral.Clases.Transacciones;
import com.example.clientesempresarepasogeneral.ClasesDAO.TransaccionDAO;
import com.example.clientesempresarepasogeneral.HelloApplication;
import com.example.clientesempresarepasogeneral.Util.Alerta;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class verTransaccionesController implements Initializable {
    @FXML
    private TableView<Transacciones> TablaID; //tabla para mostrar transacciones

    @FXML
    private Button agregarTransaccionButton; //botón para agregar transacción

    @FXML
    private Button eliminarTransaccionButton; //botón para eliminar transacción

    @FXML
    private DatePicker fechaId; //campo para seleccionar fecha

    @FXML
    private TableColumn<Transacciones, LocalDate> fechaTablaId; //columna para fecha

    @FXML
    private TextField idId; //campo para mostrar id de la transacción

    @FXML
    private TableColumn<Transacciones, Integer> idIdTabla; //columna para id de la transacción

    @FXML
    private TextField precioId; //campo para ingresar precio

    @FXML
    private TableColumn<Transacciones, String> nombreUsuarioID; //columna para nombre de usuario

    @FXML
    private TableColumn<Transacciones, Double> precioTablaID; //columna para precio

    @FXML
    private ComboBox<String> tipoId; //combobox para seleccionar tipo de transacción

    @FXML
    private TableColumn<Transacciones, String> tipoTablaId; //columna para tipo de transacción

    @FXML
    private Button volverTransaccionButton; //botón para volver a la página principal

    Clientes cliente; //cliente seleccionado

    Transacciones transaccion; //transacción seleccionada

    TransaccionDAO transaccionDAO = new TransaccionDAO(); //instancia de TransaccionDAO

    @FXML
    void onAgregarButtonClick(ActionEvent event) { //método para agregar una transacción
        if (precioId.getText().isEmpty() || fechaId.getValue() == null || tipoId.getValue() == null) { //si algún campo está vacío
            Alerta.Error("Completa todos los campos"); //mostrar alerta de error
        } else {
            Double precio = Double.parseDouble(precioId.getText()); //obtener precio
            LocalDate fecha = fechaId.getValue(); //obtener fecha
            String tipo = tipoId.getValue(); //obtener tipo de transacción
            Transacciones transaccion = new Transacciones(fecha, cliente.getNombre(), precio, tipo); //crear nueva transacción
            transaccionDAO.agregarTransaccion(transaccion); //agregar transacción a la base de datos
            transaccionDAO.agregarTransaccionMongo(transaccion); //agregar transacción a MongoDB
            cargarTabla(); //cargar la tabla de transacciones
        }
    }

    @FXML
    void onEliminarButtonClick(ActionEvent event) { //método para eliminar una transacción
        if (transaccion == null) { //si no se ha seleccionado una transacción
            Alerta.Error("Debe seleccionar una transaccion"); //mostrar alerta de error
        } else {
            transaccionDAO.borrarTransaccion(transaccion); //eliminar transacción de la base de datos
            transaccionDAO.borrarTransaccionMongo(transaccion); //eliminar transacción de MongoDB
            cargarTabla(); //cargar la tabla de transacciones
        }
    }

    @FXML
    void onVolverButtonClick(ActionEvent event) throws IOException { //método para volver a la página principal
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("PaginaPrincipal.fxml")); //cargar vista de página principal
        Parent root = fxmlLoader.load(); //cargar archivo FXML
        PaginaPrincipalController controller = fxmlLoader.getController(); //obtener controlador de la vista

        Scene scene = new Scene(root); //crear nueva escena
        Stage stage = (Stage) volverTransaccionButton.getScene().getWindow(); //obtener la ventana actual
        stage.setScene(scene); //establecer nueva escena
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //método de inicialización
        cliente = Sesion.getCliente(); //obtener cliente de la sesión
        transaccionDAO.conectarBD(); //conectar a la base de datos

        idIdTabla.setCellValueFactory(new PropertyValueFactory<>("id")); //asignar columna id
        fechaTablaId.setCellValueFactory(new PropertyValueFactory<>("fecha")); //asignar columna fecha
        precioTablaID.setCellValueFactory(new PropertyValueFactory<>("precio")); //asignar columna precio
        tipoTablaId.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getTipo())); //asignar columna tipo
        nombreUsuarioID.setCellValueFactory(new PropertyValueFactory<>("nombre")); //asignar columna nombre de usuario

        cargarTabla(); //cargar la tabla de transacciones

        String[] tiposComboBox = {"Metálico", "Tarjeta", "PayPal"}; //tipos de transacción para el combobox
        tipoId.getItems().setAll(tiposComboBox); //establecer los tipos en el combobox
    }

    public void cargarTabla() { //método para cargar las transacciones en la tabla
        List<Transacciones> lista = transaccionDAO.obtenerTransacciones(cliente); //obtener transacciones del cliente
        ObservableList<Transacciones> listado = FXCollections.observableArrayList(lista); //convertir lista a observable
        TablaID.getItems().setAll(listado); //establecer los items en la tabla
    }

    public void seleccionarFila(javafx.scene.input.MouseEvent mouseEvent) { //método para seleccionar una fila de la tabla
        transaccion = TablaID.getSelectionModel().getSelectedItem(); //obtener transacción seleccionada
        if (transaccion != null) { //si hay una transacción seleccionada
            idId.setText(String.valueOf(transaccion.getId())); //mostrar id de la transacción
            fechaId.setValue(transaccion.getFecha()); //mostrar fecha de la transacción
            tipoId.setValue(transaccion.getTipo()); //mostrar tipo de la transacción
            precioId.setText(String.valueOf(transaccion.getPrecio())); //mostrar precio de la transacción
        }
    }
}