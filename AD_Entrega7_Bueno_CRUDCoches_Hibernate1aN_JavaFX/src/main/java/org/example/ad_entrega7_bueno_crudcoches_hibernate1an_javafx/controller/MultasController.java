package org.example.ad_entrega7_bueno_crudcoches_hibernate1an_javafx.controller;

import com.example.cochesmultacrudexamen.CRUD.MultasCRUD;
import com.example.cochesmultacrudexamen.Main;
import com.example.cochesmultacrudexamen.clases.Coches;
import com.example.cochesmultacrudexamen.clases.Multas;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MultasController implements Initializable {

    //declaración de los elementos de la interfaz
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

    //instancia de Coches para manejar el coche seleccionado --> el que pongo en el metod
    Coches coche;

    //instancia de Multas para manejar la multa seleccionada
    Multas multasSeleccionada;

    //instancia de MultasCRUD para interactuar con la base de datos de multas
    MultasCRUD multasCRUD = new MultasCRUD();

    //lista observable que contiene las multas para mostrar en la tabla
    ObservableList<Multas> observableList;

    //método que se ejecuta al hacer clic en el botón "Atras"
    @FXML
    void onAtrasClick(ActionEvent event) {
        try {
            //carga la vista anterior (DGT)
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dgt.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) bttnAtras.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            Alerta.alertaError(e.getMessage()); //muestra alerta en caso de error
        }
    }

    //método que se ejecuta al hacer clic en el botón "Borrar"
    @FXML
    void onBorrarClick(ActionEvent event) {
        //verifica si se ha seleccionado una multa
        if (multasSeleccionada != null) {
            //elimina la multa seleccionada
            multasCRUD.eliminarMulta(multasSeleccionada);
        } else {
            Alerta.alertaError("Seleccione una multa para eliminarla"); //muestra alerta si no se ha seleccionado ninguna multa
        }
        cargar(); //recarga la lista de multas en la tabla
    }

    //método que se ejecuta al hacer clic en el botón "Editar"
    @FXML
    void onEditarClick(ActionEvent event) {
        double precio = Double.parseDouble(txtfPrecio.getText()); //obtiene el precio de la multa
        LocalDate fecha = txtfFecha.getValue(); //obtiene la fecha de la multa

        //verifica si se ha seleccionado una multa
        if (multasSeleccionada != null) {
            //actualiza la multa seleccionada con los nuevos valores
            multasSeleccionada.setFecha(fecha);
            multasSeleccionada.setPrecio(precio);
            //realiza la actualización en la base de datos
            if (multasCRUD.actualizarMulta(multasSeleccionada)) {
                Alerta.alertaInfo("Se ha actualizado correctamente");
            }
        }
        cargar(); //vuelve a cargar las multas en la tabla
        limpiar(); //limpia los campos de entrada
    }

    //método que se ejecuta al hacer clic en el botón "Insertar"
    @FXML
    void onInsertarClick(ActionEvent event) {
        double precio = Double.parseDouble(txtfPrecio.getText()); //obtiene el precio de la multa
        LocalDate fecha = txtfFecha.getValue(); //obtiene la fecha de la multa
        String matricula = coche.getMatricula(); //obtiene la matrícula del coche seleccionado

        //verifica que los campos estén completos
        if (txtfPrecio.getText().isEmpty() || fecha == null) {
            Alerta.alertaError("Rellene todos los campos");
        } else {
            Multas multas = new Multas(precio, fecha, matricula);
            //verifica que el id de la multa no exista en la base de datos
            if (!multasCRUD.comprobarIdMulta(multas.getId_multa())) {
                //inserta la nueva multa en la base de datos
                if (multasCRUD.insertaMulta(multas)) {
                    Alerta.alertaInfo("Multa creada");
                }
            } else {
                Alerta.alertaError("Ya existe una multa con ese id"); //muestra alerta si ya existe una multa con el mismo id
            }
        }
        limpiar(); //limpia los campos de entrada
        cargar(); //vuelve a cargar las multas en la tabla
    }

    //método que se ejecuta al hacer clic en el botón "Limpiar"
    @FXML
    void onLimpiarClick(ActionEvent event) {
        limpiar(); //limpia los campos de entrada
    }

    //método que se ejecuta al hacer clic en una fila de la tabla
    @FXML
    void onMouseClick(MouseEvent event) {
        Multas multas = tableView.getSelectionModel().getSelectedItem();
        if (multas != null) {
            //establece la multa seleccionada y llena los campos con los datos de la multa
            multasSeleccionada = multas;
            txtfFecha.setValue(multas.getFecha());
            txtfPrecio.setText(String.valueOf(multas.getPrecio()));
        }
    }

    //método que recibe un coche y carga sus multas
    public void metod(Coches coches) {
        coche = coches; //asigna el coche recibido al campo de la clase
        System.out.println(coches); //imprime información del coche
        txtfmatricula.setText(coche.getMatricula()); //muestra la matrícula del coche en el TF

        //obtiene las multas asociadas al coche y las carga en la tabla
        List<Multas> multas = multasCRUD.obtenerMultas(coche);
        observableList = FXCollections.observableArrayList(multas);
        tableView.setItems(observableList);
    }

    //método para cargar todas las multas en la tabla
    public void cargar() {
        ArrayList<Multas> multas = multasCRUD.getMultas();
        tableView.getItems().setAll(multas);
    }

    //método para limpiar los campos de entrada
    public void limpiar() {
        txtfPrecio.clear();
        txtfFecha.setValue(null);
    }

    //método que se ejecuta al iniciar la vista
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //configura las columnas de la tabla
        tcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("id_multa"));
        tcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        //hago que el TextField de la matrícula no sea editable
        txtfmatricula.setEditable(false);
    }
}