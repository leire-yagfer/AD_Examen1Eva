package org.example.ad_entrega4_mongodb_crudcoches_tableview.Controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.ad_entrega4_mongodb_crudcoches_tableview.DAO.CocheDAO;
import org.example.ad_entrega4_mongodb_crudcoches_tableview.Model.Coche;
import org.example.ad_entrega4_mongodb_crudcoches_tableview.util.ComprobacionesYAlertas;


import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    //ATRIBUTOS
    @FXML
    private TextField matriculaTF;

    @FXML
    private TextField marcaTF;

    @FXML
    private TextField modeloTF;

    @FXML
    private Button cancelarBoton;

    @FXML
    private TableColumn<Coche, String> colMatricula;

    @FXML
    private TableColumn<Coche, String> colMarca;

    @FXML
    private TableColumn<Coche, String> colModelo;

    @FXML
    private TableColumn<Coche, String> colTipo;

    @FXML
    private Button eliminarBoton;

    @FXML
    private Button guadarCambiosBoton;

    @FXML
    private Button nuevoBoton;

    @FXML
    private TableView<Coche> tableViewCoches; //defino el tipo de datoq ue va a mostrar

    @FXML
    private ComboBox<String> tipoCB; //tipo de dato que almacena el ComboBox

    ObservableList<Coche> cochesOL; //creo un ObservableList de tipo coche que va a almacenar todos los coches


    //MÉTODOS
    //método que según se inicializa el programa, me carga los datos del comboBox, creo el acceso a la BD y muestro los coches almacenados en la BD
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //COMBOBOX --> le inicializo con los tipos de coche que hay
        ObservableList<String> tipoCoche = FXCollections.observableArrayList("SUV", "Monovolumen", "Deportivo", "Pick-up", "Familiar"); //creo una lista con los tipos de coche
        tipoCB.setItems(tipoCoche); //asigno la lista al ComboBox


        //TABLEVIEW --> inicializo las columnas
        //inicializo las columnas del tableView (lo que hay entre "" es el getter de cada propiedad de la clase coche)
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));


        //llamo al método que se conecta a la BD --> crea el dataBase y la tabla coches
        CocheDAO.crearBD();

        //llamo al método que muestra los coches almacenados en la base de datos y los añado a la tableView
        ArrayList<Coche> listarCoches = CocheDAO.mostrarCoches(); //creo un ArrayList para convertir a ObservableList
        cochesOL = FXCollections.observableArrayList(listarCoches);
        tableViewCoches.setItems(cochesOL);
    }//initialize


    //método que muestra los datos del coche seleccionado del tableView en los distintos TextFields y ComboBox
    @FXML
    void onElegirCocheClick(MouseEvent event) {
        Coche cocheSeleccionado = tableViewCoches.getSelectionModel().getSelectedItem(); //obtengo el coche seleccionado del tableView y lo guardo en la variable cocheSeleccioando de tipo Coche
        if (cocheSeleccionado != null) { //si hay un coche seleccionado
            //pongo los datos del coche en los diferentes TextFields y comboBox
            matriculaTF.setText(cocheSeleccionado.getMatricula());
            marcaTF.setText(cocheSeleccionado.getMarca());
            modeloTF.setText(cocheSeleccionado.getModelo());
            tipoCB.getSelectionModel().select(cocheSeleccionado.getTipo());
        }//if
    }//onElegirCocheClick


    //método que añade el nuevo coche creado
    @FXML
    void onNuevoClick(ActionEvent event) {
        String matricula = matriculaTF.getText();
        String marca = marcaTF.getText();
        String modelo = modeloTF.getText();
        String tipo = tipoCB.getSelectionModel().getSelectedItem();

        //compruebo que todos los campos están rellenos
        if (matricula.isEmpty() || marca.isEmpty() || modelo.isEmpty() || tipo.isEmpty()) {
            ComprobacionesYAlertas.mostrarAlerta("Todos los campos han de estar rellenos.");
        } else {
            //compruebo que la matrícula cumple con el formato
            if (!ComprobacionesYAlertas.validarMatricula(matricula)) {
                ComprobacionesYAlertas.mostrarAlerta("La matrícula debe tener 4 dígitos (del 0 al 9) seguidos de 3 letras mayúsculas, excluyendo las vocales.");
            } else if (CocheDAO.existeMatricula(matricula) > 0) { //compruebo que la matrícula no esté ya en la BD
                ComprobacionesYAlertas.mostrarAlerta("Esa matrícula ya está en uso. Pruebe con otra.");
            } else { //si cumple --> creo la instancia de coche con los datos introducidos
                Coche cocheNuevo = new Coche(matricula, marca, modelo, tipo);
                if (CocheDAO.insertarCoche(cocheNuevo) > 0) {
                    actualizarTabla(); //llamo al método que actualiza la tabla después de haber realizado la inserción

                    //cuando elimino el coche, limpio los datos de los campos
                    matriculaTF.clear();
                    marcaTF.clear();
                    modeloTF.clear();
                    tipoCB.getSelectionModel().clearSelection();
                } else {
                    ComprobacionesYAlertas.mostrarAlerta("No se ha podido insertar el coche.");
                }//if-else
            }//if-else
        }//if-else
    }//onNuevoClick


    //método en el que si se han realizado cambios en los datos de algún coche, lo actualizo
    @FXML
    void onGuardarCambiosClick(ActionEvent event) {
        Coche cocheSeleccionado = tableViewCoches.getSelectionModel().getSelectedItem(); //obtengo el coche seleccionado del tableView y lo guardo en la variable cocheSeleccioando de tipo Coche

        //compruebo si hay algún coche seleccionado
        if (cocheSeleccionado == null) {
            ComprobacionesYAlertas.mostrarAlerta("No se ha seleccionado ningún coche.");
            return; //si no hay coche seleccionado, salgo del método
        }

        //obtengo los datos de cada TF y del CB
        String matricula = matriculaTF.getText();
        String marca = marcaTF.getText();
        String modelo = modeloTF.getText();
        String tipo = tipoCB.getSelectionModel().getSelectedItem();

        //compruebo que la matricula introducida no se haya modificado, porque se debe mantener constante
        if (!Objects.equals(cocheSeleccionado.getMatricula(), matricula)) {
            ComprobacionesYAlertas.mostrarAlerta("La matrícula no puede ser modificada.");
            matriculaTF.setText(cocheSeleccionado.getMatricula()); //cambio a la matrícula original en caso de modificarse
            return; //detengo el proceso si la matrícula ha cambiado
        }

        //verifico que los otros campos no estén vacíos
        if (marca.isEmpty() || modelo.isEmpty() || tipo == null) {
            ComprobacionesYAlertas.mostrarAlerta("Todos los campos deben estar rellenos.");
            return; //si algún campo está vacío, detengo el proceso
        }

        //actualizo los campos que pueden ser modificados
        cocheSeleccionado.setMarca(marca);
        cocheSeleccionado.setModelo(modelo);
        cocheSeleccionado.setTipo(tipo);

        //compruebo si se ha actualizado correctamente los datos
        if (CocheDAO.actualizarCoche(cocheSeleccionado) > 0) {
            actualizarTabla();
        } else {
            ComprobacionesYAlertas.mostrarAlerta("Error en la actualización de los datos.");
        }
    }//onGuardarCambiosClick


    //método que limpia todos los campos, ya que se cancela lo que se estuviese queriendo hacer
    @FXML
    void onCancelarClick(ActionEvent event) {
        matriculaTF.clear();
        marcaTF.clear();
        modeloTF.clear();
        tipoCB.getSelectionModel().clearSelection();
    }//onCancelarClick


    //método que elimina el coche seleccionado
    @FXML
    void onEliminarClick(ActionEvent event) {
        Coche cocheSeleccionado = tableViewCoches.getSelectionModel().getSelectedItem(); //obtengo el coche seleccionado del tableView y lo guardo en la variable cocheSeleccioando de tipo Coche

        //compruebo si hay algún coche seleccionado
        if (cocheSeleccionado == null) {
            ComprobacionesYAlertas.mostrarAlerta("No se ha seleccionado ningún coche.");
            return; //si no hay coche seleccionado, salgo del método
        }

        //compruebo si se ha podido eliminar
        if (CocheDAO.eliminarCoche(cocheSeleccionado) > 0) {
            actualizarTabla();
        } else ComprobacionesYAlertas.mostrarAlerta("No se ha podido eliminar el coche.");

        //cuando elimino el coche, limpio los datos de los campos
        matriculaTF.clear();
        marcaTF.clear();
        modeloTF.clear();
        tipoCB.getSelectionModel().clearSelection();
    }//onEliminarClick


    //método que actualiza los datos de la tabla después de realizar cambios --> el refresh() no actualiza la tabla
    void actualizarTabla() {
        ArrayList<Coche> listarCoches = CocheDAO.mostrarCoches();
        tableViewCoches.getItems().setAll(listarCoches);
    }//actualizarTabla
}//class