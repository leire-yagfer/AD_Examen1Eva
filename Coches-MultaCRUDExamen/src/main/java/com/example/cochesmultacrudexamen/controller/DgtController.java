package com.example.cochesmultacrudexamen.controller;

import com.example.cochesmultacrudexamen.CRUD.CochesCRUD;
import com.example.cochesmultacrudexamen.Main;
import com.example.cochesmultacrudexamen.clases.Coches;
import com.example.cochesmultacrudexamen.utils.Alerta;
import com.sun.glass.events.MouseEvent;
import javafx.beans.Observable;
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


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

public class DgtController implements Initializable {

    @FXML
    private Button bttnActualizar;

    @FXML
    private Button bttnBorrar;

    @FXML
    private Button bttnInsertar;

    @FXML
    private Button bttnLimipiar;

    @FXML
    private Button bttnMultas;

    @FXML
    private ChoiceBox<String> cbTipo;

    @FXML
    private TableView<Coches> tableView;

    @FXML
    private TableColumn<?, ?> tcMarca;

    @FXML
    private TableColumn<?, ?> tcMatricula;

    @FXML
    private TableColumn<?, ?> tcModelo;

    @FXML
    private TableColumn<?, ?> tcTipo;

    @FXML
    private TextField txtfMarca;

    @FXML
    private TextField txtfMatricula;

    @FXML
    private TextField txtfModelo;

    CochesCRUD cochesCRUD = new CochesCRUD();

    ObservableList<Coches>observableList;

    Coches cocheSeleccionado;

    @FXML
    void onActualizarCLick(ActionEvent event) {
        Coches coches = cocheSeleccionado;
        String matricula = txtfMatricula.getText();
        String marca = txtfMarca.getText();
        String modelo = txtfModelo.getText();
        String tipo = cbTipo.getSelectionModel().getSelectedItem();
        if  (coches!=null){
            coches.setMarca(marca);
            coches.setModelo(modelo);
            coches.setTipo(tipo);
            if (!coches.getMatricula().equals(matricula)){
                Alerta.alertaError("No se puede editar la matricula");
            }else if(cochesCRUD.editarCoche(coches)){
                Alerta.alertaInfo("Coche editado");
            }
        }
        cargarTabla();
        limipar();
    }

    @FXML
    void onBorrarClick(ActionEvent event) {
        if (cocheSeleccionado!=null){
            if (cochesCRUD.eliminarCoche(cocheSeleccionado)){
                Alerta.alertaInfo("Coche eliminado");
            }
        }else {
            Alerta.alertaError("Selecciona un coche para eliminar");
        }
        cargarTabla();
    }

    @FXML
    void onInsertarClick(ActionEvent event) {
        String matricula = txtfMatricula.getText();
        String marca = txtfMarca.getText();
        String modelo = txtfModelo.getText();
        String tipo = cbTipo.getSelectionModel().getSelectedItem();

        if (matricula.isEmpty()||marca.isEmpty()||modelo.isEmpty()||tipo==null){
            Alerta.alertaError("Rellena todos los campos");
        }else {
            if (!cochesCRUD.comprobarMatricula(matricula)){
                if (cochesCRUD.estructuraMatricula(matricula)){
                    Coches coches = new Coches(matricula,marca,modelo,tipo);
                    if(cochesCRUD.insertarCoche(coches)==1){
                        Alerta.alertaInfo("Coche creado");
                    }
                }else {
                    Alerta.alertaError("Introduce la estructura de la matricula correctamente (0000XXX)");
                }
            }else {
                Alerta.alertaError("Ya existe esta matricula");
            }
        }
        cargarTabla();
        limipar();
    }

    @FXML
    void onLimpiarClick(ActionEvent event) {
        limipar();
    }

    @FXML
    void onMultasClick(ActionEvent event) {

        if (cocheSeleccionado!=null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("multas.fxml"));
                Parent root = fxmlLoader.load();

                MultasController multasController = fxmlLoader.getController();
                multasController.metod(cocheSeleccionado);

                Scene scene = new Scene(root);
                Stage stage = (Stage) bttnMultas.getScene().getWindow();
                stage.setScene(scene);

            }catch (Exception e){
                Alerta.alertaError(e.getMessage());
            }
        }else {
            Alerta.alertaError("Seleccione un coche para ver sus multas");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String []lista = {"SUV","Deportivo","TodoTerreno","Familiar","MonoVolumen"};
        cbTipo.getItems().addAll(lista);
        tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        ArrayList<Coches>listaCoches=cochesCRUD.getCoches();
        observableList= FXCollections.observableList(listaCoches);
        tableView.setItems(observableList);
    }

    public void onMouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        Coches coches =tableView.getSelectionModel().getSelectedItem();
        if (coches!=null){
            cocheSeleccionado = coches;
            txtfMarca.setText(coches.getMarca());
            txtfMatricula.setText(coches.getMatricula());
            txtfModelo.setText(coches.getModelo());
            cbTipo.getSelectionModel().select(coches.getTipo());
        }
    }
    public void cargarTabla(){
        ArrayList<Coches>listaCoches =cochesCRUD.getCoches();
        tableView.getItems().setAll(listaCoches);
    }
    public  void limipar(){
        txtfMarca.clear();
        txtfModelo.clear();
        txtfMatricula.clear();
        cbTipo.getSelectionModel().clearSelection();
    }
}
