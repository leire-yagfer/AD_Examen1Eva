package org.example.empresamongodbjbcdhibernate.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.empresamongodbjbcdhibernate.CRUD.ClientesCRUD;
import org.example.empresamongodbjbcdhibernate.Main;
import org.example.empresamongodbjbcdhibernate.clases.Clientes;
import org.example.empresamongodbjbcdhibernate.utils.Alerta;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button bttnActualizar;

    @FXML
    private Button bttnAgregar;

    @FXML
    private Button bttnEliminar;

    @FXML
    private Button bttnVerTransacciones;

    @FXML
    private TableView<Clientes> tableView;

    @FXML
    private TableColumn<?, ?> tcApellido;

    @FXML
    private TableColumn<?, ?> tcEmail;

    @FXML
    private TableColumn<?, ?> tcId;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TableColumn<?, ?> tcTelefono;

    @FXML
    private TextField txtfApellido;

    @FXML
    private TextField txtfEmail;

    @FXML
    private TextField txtfId;

    @FXML
    private TextField txtfNombre;

    @FXML
    private TextField txtfTelefono;

    ClientesCRUD clientesCRUD = new ClientesCRUD();

    ObservableList<Clientes> observableList;

    Clientes clienteSeleccionado;

    @FXML
    void onActualizarClick(ActionEvent event) {
        String nombre= txtfNombre.getText();
        String apellido= txtfApellido.getText();
        String email = txtfEmail.getText();

        clienteSeleccionado.setNombre(nombre);
        clienteSeleccionado.setApellido(apellido);
        clienteSeleccionado.setEmail(email);
        clienteSeleccionado.setTelefono(Integer.parseInt(txtfTelefono.getText()));

        if (clientesCRUD.editarClienteHibernate(clienteSeleccionado)){
            Alerta.alertaInfo("Cliente actualizado correctamente hibernate");
        }else {
            Alerta.alertaError("No se pudo actualizar el cliente");
        }

        if (clientesCRUD.actualizarClienteMongoDB(clienteSeleccionado)){
            Alerta.alertaInfo("Cliente actualizado correctamente mongo");
        }else {
            Alerta.alertaError("No se pudo actualizar el cliente mongo");
        }
        cargar();
        limpiar();
    }

    @FXML
    void onAgregarClick(ActionEvent event) {
        String nombre= txtfNombre.getText();
        String apellido= txtfApellido.getText();
        String email = txtfEmail.getText();
        String telefonoTxt = txtfTelefono.getText();
        if (nombre.isEmpty()||apellido.isEmpty()||email.isEmpty()|| telefonoTxt.isEmpty()){
            Alerta.alertaError("Rellena todos los campos");
        }else{
            int telefono = Integer.parseInt(txtfTelefono.getText());
            Clientes clientes = new Clientes(nombre,apellido,email,telefono);
            if (clientesCRUD.insertarClienteHibernate(clientes)){
                Alerta.alertaInfo("Cliente agregado ");
                System.out.println("cliente agregado hibernate");
            }
            if (clientesCRUD.inertarClienteMongoDB(clientes)){
                System.out.println("cliente agregado mongo");
            }
            cargar();
            limpiar();
        }
    }

    @FXML
    void onEliminarClick(ActionEvent event) {
        if (clientesCRUD.eliminarClienteHibernate(clienteSeleccionado)){
            Alerta.alertaInfo("Cliente eliminado hibernate");
        }
        if (clientesCRUD.eliminarClienteMongoDB(clienteSeleccionado)){
            System.out.println("cliente eliminado mongo");;
        }

        cargar();
        limpiar();
    }

    @FXML
    void onVertransaccionesClick(ActionEvent event) {
        try {
            if (clienteSeleccionado==null){
                Alerta.alertaError("Selecciona un cliente para ver sus transacciones");
            }else {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("transacciones.fxml"));
                Parent root = fxmlLoader.load();
                TransaccionesController transaccionesController = fxmlLoader.getController();
                transaccionesController.metod(clienteSeleccionado);
                Scene scene = new Scene(root);
                Stage stage = (Stage) bttnVerTransacciones.getScene().getWindow();
                stage.setScene(scene);
            }
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientesCRUD.conectarBDMongo();

        tcApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        ArrayList<Clientes>clientes = clientesCRUD.getClientesHibernate();
        observableList = FXCollections.observableArrayList(clientes);
        tableView.setItems(observableList);
    }

    public void limpiar(){
        txtfTelefono.clear();
        txtfApellido.clear();
        txtfEmail.clear();
        txtfNombre.clear();
        txtfId.clear();
    }
    public void cargar(){
        ArrayList<Clientes>clientes= clientesCRUD.getClientesHibernate();
        tableView.getItems().setAll(clientes);
    }
    @FXML
    void onMouseClick(MouseEvent event) {
        Clientes clientes = tableView.getSelectionModel().getSelectedItem();
        if (clientes!=null){
            clienteSeleccionado = clientes;
            txtfId.setText(String.valueOf(clientes.getId()));
            txtfNombre.setText(clientes.getNombre());
            txtfApellido.setText(clientes.getApellido());
            txtfEmail.setText(clientes.getEmail());
            txtfTelefono.setText(String.valueOf(clientes.getTelefono()));
        }
    }
}
