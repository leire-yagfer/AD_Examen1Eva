package org.example.ad_entrega7_bueno_crudcoches_hibernate1an_javafx.controller;

import com.example.cochesmultacrudexamen.CRUD.CochesCRUD;
import com.example.cochesmultacrudexamen.Main;
import com.example.cochesmultacrudexamen.clases.Coches;
import com.example.cochesmultacrudexamen.utils.Alerta;
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

public class DgtController implements Initializable {

    //declaración de los elementos de la interfaz
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

    //instancia de CochesCRUD para manejar las operaciones con la base de datos
    CochesCRUD cochesCRUD = new CochesCRUD();

    //lista observable que contendrá los coches para mostrarlos en la tabla
    ObservableList<Coches> observableList;

    //coche seleccionado en la tabla
    Coches cocheSeleccionado;

    //método que se ejecuta al hacer clic en el botón "Actualizar"
    @FXML
    void onActualizarCLick(ActionEvent event) {
        //recoge los datos de los campos de texto
        Coches coches = cocheSeleccionado;
        String matricula = txtfMatricula.getText();
        String marca = txtfMarca.getText();
        String modelo = txtfModelo.getText();
        String tipo = cbTipo.getSelectionModel().getSelectedItem();

        //verifica si se seleccionó un coche
        if (coches != null) {
            //actualiza los datos del coche
            coches.setMarca(marca);
            coches.setModelo(modelo);
            coches.setTipo(tipo);
            //verifica que no se intente cambiar la matrícula
            if (!coches.getMatricula().equals(matricula)) {
                Alerta.alertaError("No se puede editar la matricula");
            } else if (cochesCRUD.editarCoche(coches)) {
                Alerta.alertaInfo("Coche editado");
            }
        }
        //vuelve a cargar la tabla y limpia los campos
        cargarTabla();
        limipar();
    }

    //método que se ejecuta al hacer clic en el botón "Borrar"
    @FXML
    void onBorrarClick(ActionEvent event) {
        //verifica si se ha seleccionado un coche
        if (cocheSeleccionado != null) {
            //elimina el coche seleccionado
            if (cochesCRUD.eliminarCoche(cocheSeleccionado)) {
                Alerta.alertaInfo("Coche eliminado");
            }
        } else {
            Alerta.alertaError("Selecciona un coche para eliminar");
        }
        cargarTabla(); //vuelve a cargar la tabla
    }

    //método que se ejecuta al hacer clic en el botón "Insertar"
    @FXML
    void onInsertarClick(ActionEvent event) {
        //recoge los datos de los campos de texto
        String matricula = txtfMatricula.getText();
        String marca = txtfMarca.getText();
        String modelo = txtfModelo.getText();
        String tipo = cbTipo.getSelectionModel().getSelectedItem();

        //verifica que todos los campos estén completos
        if (matricula.isEmpty() || marca.isEmpty() || modelo.isEmpty() || tipo == null) {
            Alerta.alertaError("Rellena todos los campos");
        } else {
            //verifica que la matrícula no exista y que siga la estructura correcta
            if (!cochesCRUD.comprobarMatricula(matricula)) {
                if (cochesCRUD.estructuraMatricula(matricula)) {
                    Coches coches = new Coches(matricula, marca, modelo, tipo);
                    //inserta el coche en la base de datos
                    if (cochesCRUD.insertarCoche(coches) == 1) {
                        Alerta.alertaInfo("Coche creado");
                    }
                } else {
                    Alerta.alertaError("Introduce la estructura de la matricula correctamente (0000XXX)");
                }
            } else {
                Alerta.alertaError("Ya existe esta matricula");
            }
        }
        cargarTabla(); //vuelve a cargar la tabla
        limipar(); //limpia los campos de entrada
    }

    //método que se ejecuta al hacer clic en el botón "Limpiar"
    @FXML
    void onLimpiarClick(ActionEvent event) {
        limipar(); //limpia los campos de entrada
    }

    //método que se ejecuta al hacer clic en el botón "Multas"
    @FXML
    void onMultasClick(ActionEvent event) {
        //verifica si se ha seleccionado un coche
        if (cocheSeleccionado != null) {
            try {
                //carga la vista de multas
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("multas.fxml"));
                Parent root = fxmlLoader.load();

                MultasController multasController = fxmlLoader.getController();
                //pasa el coche seleccionado al controlador de multas
                multasController.metod(cocheSeleccionado);

                Scene scene = new Scene(root);
                Stage stage = (Stage) bttnMultas.getScene().getWindow();
                stage.setScene(scene);

            } catch (Exception e) {
                Alerta.alertaError(e.getMessage()); //muestra alerta en caso de error
            }
        } else {
            Alerta.alertaError("Seleccione un coche para ver sus multas");
        }
    }

    //método que inicializa la vista y configura la tabla
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //agrega los tipos de coche al ChoiceBox
        String[] lista = {"SUV", "Deportivo", "TodoTerreno", "Familiar", "MonoVolumen"};
        cbTipo.getItems().addAll(lista);
        //configura las columnas de la tabla
        tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        //carga la lista de coches y la muestra en la tabla
        ArrayList<Coches> listaCoches = cochesCRUD.getCoches();
        observableList = FXCollections.observableList(listaCoches);
        tableView.setItems(observableList);
    }

    //método que se ejecuta al hacer clic en una fila de la tabla
    public void onMouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        //obtiene el coche seleccionado
        Coches coches = tableView.getSelectionModel().getSelectedItem();
        if (coches != null) {
            cocheSeleccionado = coches;
            //muestra los datos del coche en los campos de texto
            txtfMarca.setText(coches.getMarca());
            txtfMatricula.setText(coches.getMatricula());
            txtfModelo.setText(coches.getModelo());
            cbTipo.getSelectionModel().select(coches.getTipo());
        }
    }

    //método para recargar los datos de la tabla
    public void cargarTabla() {
        ArrayList<Coches> listaCoches = cochesCRUD.getCoches();
        tableView.getItems().setAll(listaCoches);
    }

    //método para limpiar los campos de entrada
    public void limipar() {
        txtfMarca.clear();
        txtfModelo.clear();
        txtfMatricula.clear();
        cbTipo.getSelectionModel().clearSelection();
    }
}