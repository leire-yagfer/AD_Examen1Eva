package org.example.empresamongodbjbcdhibernate.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.empresamongodbjbcdhibernate.CRUD.TransaccionesCRUD;
import org.example.empresamongodbjbcdhibernate.clases.Clientes;
import org.example.empresamongodbjbcdhibernate.clases.Transacciones;
import org.example.empresamongodbjbcdhibernate.utils.Alerta;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TransaccionesController implements Initializable {

    @FXML
    private Button bttnActualizar;

    @FXML
    private Button bttnActualizar1;

    @FXML
    private Button bttnAgregar;

    @FXML
    private Button bttnEliminar;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TableView<Transacciones> tableView;

    @FXML
    private TableColumn<?, ?> tcCantidad;

    @FXML
    private TableColumn<?, ?> tcFecha;

    @FXML
    private TableColumn<?, ?> tcId;

    @FXML
    private TableColumn<?, ?> tcTipo;

    @FXML
    private TextField txtfCantidad;

    @FXML
    private TextField txtfId;

    TransaccionesCRUD transaccionesCRUD = new TransaccionesCRUD();

    Clientes clienteSeleccionado;

    Transacciones transaccionSeleccionada;

    ObservableList<Transacciones>observableList;

    @FXML
    void onActualizarClick(ActionEvent event) {

    }

    @FXML
    void onAgregarClick(ActionEvent event) {

        if (txtfCantidad.getText().isEmpty()|| dpFecha.getValue() ==null|| cbTipo.getSelectionModel().getSelectedItem()==null){
            Alerta.alertaError("Rellena todos los campos");
        }else {
            double cantidad = Double.parseDouble(txtfCantidad.getText());
            LocalDate fecha = dpFecha.getValue();
            String tipo = cbTipo.getSelectionModel().getSelectedItem();
            Transacciones transacciones = new Transacciones(fecha,cantidad,tipo);
            transacciones.setClientes(clienteSeleccionado);
            if (transaccionesCRUD.inertarTransiccionHibernate(transacciones)){
                Alerta.alertaInfo("Transaccion completa");
            }
        }
        cargar();
        limpiar();

    }

    @FXML
    void onEliminarClick(ActionEvent event) {
        if (transaccionesCRUD.eliminarTransiccionHibernate(transaccionSeleccionada)){
            Alerta.alertaInfo("Transaccion eliminada");
        }else {
            Alerta.alertaError("No se pudo eliminar la transaccion");
        }
        cargar();
        limpiar();
    }

    @FXML
    void onMouseClick(MouseEvent event) {
        Transacciones transacciones = tableView.getSelectionModel().getSelectedItem();
        if (transacciones!=null){
            transaccionSeleccionada=transacciones;
            txtfId.setText(String.valueOf(transacciones.getId()));
            txtfCantidad.setText(String.valueOf(transacciones.getCantidad()));
            dpFecha.setValue(transacciones.getFecha());
            cbTipo.setValue(transacciones.getTipo());
        }
    }

    public void metod(Clientes clientes){
        clienteSeleccionado= clientes;
        List<Transacciones>listaTransacciones=  transaccionesCRUD.getTransaccionesPorClienteHibernate(clienteSeleccionado);
        observableList= FXCollections.observableList(listaTransacciones);
        tableView.setItems(observableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        tcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cbTipo.getItems().setAll("deposito", "retiro");


    }
    public void cargar(){
        List<Transacciones>listaTransacciones=  transaccionesCRUD.getTransaccionesPorClienteHibernate(clienteSeleccionado);
        tableView.getItems().setAll(listaTransacciones);
    }

    public void limpiar(){
        txtfCantidad.clear();
        txtfId.clear();
        cbTipo.setValue(null);
        dpFecha.setValue(null);
    }

}
