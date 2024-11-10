package com.example.clientesempresarepasogeneral.Controller;

import com.example.clientesempresarepasogeneral.Clases.Clientes;
import com.example.clientesempresarepasogeneral.Clases.Sesion;
import com.example.clientesempresarepasogeneral.ClasesDAO.ClienteDAO;
import com.example.clientesempresarepasogeneral.ClasesDAO.TransaccionDAO;
import com.example.clientesempresarepasogeneral.HelloApplication;
import com.example.clientesempresarepasogeneral.Util.Alerta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaginaPrincipalController implements Initializable {
    @FXML
    private TableView<Clientes> TablaId; //tabla para mostrar clientes

    @FXML
    private Button agregarClienteBoton; //botón para agregar cliente

    @FXML
    private TextField apellidosId; //campo para ingresar apellidos

    @FXML
    private TableColumn<Clientes, String> apellidosTablaId; //columna para apellidos

    @FXML
    private Button eliminarClienteButton; //botón para eliminar cliente

    @FXML
    private TextField emailId; //campo para ingresar email

    @FXML
    private TableColumn<Clientes, String> emailTablaId; //columna para email

    @FXML
    private TextField idId; //campo para ingresar id

    @FXML
    private TableColumn<Clientes, Integer> idTablaId; //columna para id

    @FXML
    private Button modificarClienteButton; //botón para modificar cliente

    @FXML
    private TextField nombreId; //campo para ingresar nombre

    @FXML
    private TableColumn<Clientes, String> nombreTablaId; //columna para nombre

    @FXML
    private TextField telefonoId; //campo para ingresar teléfono

    @FXML
    private TableColumn<Clientes, Integer> telefonoTablaId; //columna para teléfono

    @FXML
    private Button verTransaccionesButton; //botón para ver transacciones

    TransaccionDAO transaccionDAO = new TransaccionDAO(); //instancia de TransaccionDAO

    ClienteDAO clienteDAO = new ClienteDAO(); //instancia de ClienteDAO

    Clientes clienteSeleccionado ; //cliente seleccionado en la tabla

    @FXML
    void onAgregarClienteClick(ActionEvent event) { //método para agregar cliente
        if (nombreId.getText().isEmpty() || apellidosId.getText().isEmpty() || telefonoId.getText().isEmpty() || emailId.getText().isEmpty()) { //si algún campo está vacío
            Alerta.Error("Completa todos los campos"); //mostrar alerta de error
        } else {
            String nombre = nombreId.getText(); //obtener nombre
            String apellido = apellidosId.getText(); //obtener apellidos
            int telefono = Integer.parseInt(telefonoId.getText()); //obtener teléfono
            String email = emailId.getText(); //obtener email
            Clientes cliente = new Clientes(nombre,apellido, email, telefono); //crear nuevo cliente
            clienteDAO.agregarClienteSQL(cliente); //agregar cliente a SQL
            clienteDAO.agregarClienteMongo(cliente); //agregar cliente a Mongo
            cargarTabla(); //cargar la tabla de clientes
            limpiarCampos(); //limpiar los campos de entrada
        }
    }

    @FXML
    void onEliminarClienteClick(ActionEvent event) { //método para eliminar cliente
        if (clienteSeleccionado == null) { //si no se ha seleccionado un cliente
            Alerta.Error("Debe seleccionar un cliente"); //mostrar alerta de error
        } else {
            clienteDAO.eliminarClienteSQL(clienteSeleccionado); //eliminar cliente de SQL
            clienteDAO.eliminarClienteMongo(clienteSeleccionado); //eliminar cliente de Mongo
            cargarTabla(); //cargar la tabla de clientes
            limpiarCampos(); //limpiar los campos de entrada
        }
    }

    @FXML
    void onModificarClienteClick(ActionEvent event) { //método para modificar cliente
        if (clienteSeleccionado == null) { //si no se ha seleccionado un cliente
            Alerta.Error("Selecciona un cliente"); //mostrar alerta de error
        } else {
            String nombre = nombreId.getText(); //obtener nuevo nombre
            String apellido = apellidosId.getText(); //obtener nuevo apellido
            String email = emailId.getText(); //obtener nuevo email
            int telefono = Integer.parseInt(telefonoId.getText()); //obtener nuevo teléfono
            clienteDAO.modificarClienteSQL(clienteSeleccionado, nombre, apellido, email, telefono); //modificar cliente en SQL
            clienteDAO.modificarClienteMongo(clienteSeleccionado, nombre, apellido, email, telefono); //modificar cliente en Mongo
            cargarTabla(); //cargar la tabla de clientes
        }
    }

    @FXML
    void onVerTransaccionesClick(ActionEvent event) throws IOException { //método para ver transacciones
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("verTransacciones.fxml")); //cargar la vista de transacciones
        Parent root = fxmlLoader.load(); //cargar el archivo FXML
        verTransaccionesController controller = fxmlLoader.getController(); //obtener el controlador de la vista

        Scene scene = new Scene(root); //crear nueva escena
        Stage stage = (Stage) verTransaccionesButton.getScene().getWindow(); //obtener la ventana actual
        stage.setScene(scene); //establecer la nueva escena
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //método de inicialización
        transaccionDAO = new TransaccionDAO(); //crear instancia de TransaccionDAO
        clienteDAO = new ClienteDAO(); //crear instancia de ClienteDAO
        clienteDAO.conectarBD(); //conectar a la base de datos

        idTablaId.setCellValueFactory(new PropertyValueFactory<>("id")); //asignar valor de columna id
        nombreTablaId.setCellValueFactory(new PropertyValueFactory<>("nombre")); //asignar valor de columna nombre
        apellidosTablaId.setCellValueFactory(new PropertyValueFactory<>("apellido")); //asignar valor de columna apellido
        emailTablaId.setCellValueFactory(new PropertyValueFactory<>("email")); //asignar valor de columna email
        telefonoTablaId.setCellValueFactory(new PropertyValueFactory<>("telefono")); //asignar valor de columna teléfono

        cargarTabla(); //cargar la tabla de clientes
    }

    public void cargarTabla() { //método para cargar los clientes en la tabla
        ArrayList<Clientes> lista = clienteDAO.obtenerClientes(); //obtener lista de clientes desde la base de datos
        ObservableList<Clientes> listaCliente = FXCollections.observableArrayList(lista); //convertir la lista a observable
        TablaId.getItems().setAll(listaCliente); //establecer los items en la tabla
    }

    public void seleccionarFila(javafx.scene.input.MouseEvent mouseEvent) { //método para seleccionar una fila de la tabla
        clienteSeleccionado = TablaId.getSelectionModel().getSelectedItem(); //obtener cliente seleccionado
        if (clienteSeleccionado != null) { //si hay un cliente seleccionado
            idId.setText(String.valueOf(clienteSeleccionado.getId())); //mostrar id del cliente
            nombreId.setText(clienteSeleccionado.getNombre()); //mostrar nombre del cliente
            apellidosId.setText(clienteSeleccionado.getApellido()); //mostrar apellido del cliente
            emailId.setText(clienteSeleccionado.getEmail()); //mostrar email del cliente
            telefonoId.setText(String.valueOf(clienteSeleccionado.getTelefono())); //mostrar teléfono del cliente
        }

        Sesion.setCliente(clienteSeleccionado); //establecer cliente en sesión
    }

    public void limpiarCampos() { //método para limpiar los campos
        idId.clear(); //limpiar campo id
        nombreId.clear(); //limpiar campo nombre
        apellidosId.clear(); //limpiar campo apellidos
        emailId.clear(); //limpiar campo email
        telefonoId.clear(); //limpiar campo teléfono
    }
}