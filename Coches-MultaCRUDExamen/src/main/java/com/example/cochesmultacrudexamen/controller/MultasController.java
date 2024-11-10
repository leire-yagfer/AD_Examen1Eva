package com.example.cochesmultacrudexamen.controller;

import com.example.cochesmultacrudexamen.CRUD.MultasCRUD;
import com.example.cochesmultacrudexamen.Main;
import com.example.cochesmultacrudexamen.clases.Coches;
import com.example.cochesmultacrudexamen.clases.Multas;
import com.example.cochesmultacrudexamen.utils.Alerta;
import com.google.protobuf.StringValue;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.ResourceBundle;

public class MultasController implements Initializable {


    @FXML
    private Button bttnAtras;

    @FXML
    private Button bttnBorrar;

    @FXML
    private Button bttnEditar;

    @FXML
    private Button bttnInsertar;

    @FXML
    private Button bttnLimpiar;

    @FXML
    private TableView<Multas> tableView;

    @FXML
    private TableColumn<?, ?> tcId;

    @FXML
    private TableColumn<?, ?> tcPrecio;

    @FXML
    private TableColumn<?, ?> tcFecha;

    @FXML
    private DatePicker txtfFecha;


    @FXML
    private TextField txtfPrecio;

    @FXML
    private TextField txtfmatricula;

    Coches coche;

    Multas multasSeleccionada;

    MultasCRUD multasCRUD = new MultasCRUD();

    ObservableList<Multas> observableList;

    @FXML
    void onAtrasClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dgt.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) bttnAtras.getScene().getWindow();
            stage.setScene(scene);
        }catch (Exception e){
            Alerta.alertaError(e.getMessage());
        }
    }

    @FXML
    void onBorrarClick(ActionEvent event) {
        if (multasSeleccionada!=null){
            multasCRUD.eliminarMulta(multasSeleccionada);
        }else {
            Alerta.alertaError("Seleccione una multa para eliminarla");
        }
        cargar();
    }

    @FXML
    void onEditarClick(ActionEvent event) {
        double precio= Double.parseDouble(txtfPrecio.getText());
        LocalDate fecha = txtfFecha.getValue();
        if (multasSeleccionada!=null){
            multasSeleccionada.setFecha(fecha);
            multasSeleccionada.setPrecio(precio);
            if(multasCRUD.actualizarMulta(multasSeleccionada)){
                Alerta.alertaInfo("Se ha actualizado correctamente");
            }
        }
        cargar();
        limpiar();
    }

    @FXML
    void onInsertarClick(ActionEvent event) {

        double precio= Double.parseDouble(txtfPrecio.getText());
        LocalDate fecha = txtfFecha.getValue();
        String matricula = coche.getMatricula();
        if (txtfPrecio.getText().isEmpty()||fecha ==null){
            Alerta.alertaError("Rellene todos los campos");
        }else {
            Multas multas = new Multas(precio,fecha, matricula);
            if (!multasCRUD.comprobarIdMulta(multas.getId_multa())){
                if (multasCRUD.insertaMulta(multas)){
                    Alerta.alertaInfo("Multa creada");
                }
            }else {
                Alerta.alertaError("Ya existe una multa con ese id");
            }
        }
        limpiar();
        cargar();
    }

    @FXML
    void onLimpiarClick(ActionEvent event) {
        limpiar();
    }

    @FXML
    void onMouseClick(MouseEvent event) {
        Multas multas = tableView.getSelectionModel().getSelectedItem();
        if (multas!=null){
            multasSeleccionada = multas;
            txtfFecha.setValue(multas.getFecha());
            txtfPrecio.setText(String.valueOf(multas.getPrecio()));
        }
    }

    public void metod(Coches coches){
        coche = coches;
        System.out.println(coches);
        txtfmatricula.setText(coche.getMatricula());

        List<Multas> multas = multasCRUD.obtenerMultas(coche);
        observableList= FXCollections.observableArrayList(multas);
        tableView.setItems(observableList);
    }

    public void cargar(){
        ArrayList<Multas>multas = multasCRUD.getMultas();
        tableView.getItems().setAll(multas);
    }

    public void limpiar(){

        txtfPrecio.clear();
        txtfFecha.setValue(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("id_multa"));
        tcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    }
}
