package org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Controller;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.App;
import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.DAO.CocheDAO;
import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.DAO.CocheDAOImpl;
import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Model.Coche;
import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Util.ComprobacionesAlertasCambioEscena;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    CocheDAO cocheDAO = new CocheDAOImpl();

    //ATRIBUTOS
    @FXML
    private TextField matriculaTF;
    @FXML
    private TextField marcaTF;
    @FXML
    private TextField modeloTF;
    @FXML
    private ComboBox<String> tipoCB;
    @FXML
    private TableView<Coche> tableViewCoches;
    @FXML
    private TableColumn<Coche, String> colMatricula;
    @FXML
    private TableColumn<Coche, String> colMarca;
    @FXML
    private TableColumn<Coche, String> colModelo;
    @FXML
    private TableColumn<Coche, String> colTipo;
    @FXML
    private Button verMultasBoton;

    private ObservableList<Coche> cochesOL;


    //MÉTODOS
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //COMBOBOX --> le inicializo con los tipos de coche que hay
        ObservableList<String> tipoCoche = FXCollections.observableArrayList("SUV", "Monovolumen", "Deportivo", "Pick-up", "Familiar"); //creo una lista con los tipos de coche
        tipoCB.setItems(tipoCoche); //asigno la lista al ComboBox

        //TABLEVIEW --> inicializo las columnas
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        //cargo los coches de la BD en la tabla
        List<Coche> listarCoches = cocheDAO.mostrarCoches();
        ArrayList<Coche> arrayListCoches = new ArrayList<>(listarCoches);
        cochesOL = FXCollections.observableArrayList(arrayListCoches);
        tableViewCoches.setItems(cochesOL);
    }//initialize


    //método que muestra los datos del coche seleccionado del tableView en los distintos TextFields y comboBox
    @FXML
    void onElegirCocheClick(MouseEvent event) {
        Coche cocheSeleccionado = tableViewCoches.getSelectionModel().getSelectedItem(); //obtengo el coche seleccionado en el tableView y lo guardo en la variable cocheSeleccioando de tipo Coche
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
    void onInsertarClick(ActionEvent event) {
        String matricula = matriculaTF.getText();
        String marca = marcaTF.getText();
        String modelo = modeloTF.getText();
        String tipo = tipoCB.getSelectionModel().getSelectedItem();

        if (validarCampos(matricula, marca, modelo, tipo) && validarMatriculaUnica(matricula)) { //si todos los campos están rellenos y la matrícula es única
            Coche cocheNuevo = new Coche(matricula, marca, modelo, tipo); //creo el coche con los datos de los campos
            if (cocheDAO.insertarCoche(cocheNuevo) > 0) {
                actualizarTabla(); //llamo al método que actualiza la tabla después de haber realizado la inserción
                limpiarCampos(); //limpio los campos
            } else {
                ComprobacionesAlertasCambioEscena.mostrarAlerta("No se ha podido agregar el coche. Inténtelo de nuevo.");
            }//if-else
        }//if-else
    }//onInsertarClick


    //método en el que si se han realizado cambios en los datos de algún coche, lo actualizo
    @FXML
    void onActualizarClick(ActionEvent event) {
        Coche cocheSeleccionado = tableViewCoches.getSelectionModel().getSelectedItem(); //obtengo el coche seleccionado en el tableView y lo guardo en la variable cocheSeleccioando de tipo Coche

        //compruebo si hay algún coche seleccionado
        if (cocheSeleccionado == null) {
            ComprobacionesAlertasCambioEscena.mostrarAlerta("No se ha seleccionado ningún coche.");
            return; //si no hay coche seleccionado, salgo del método
        }//if

        String marca = marcaTF.getText();
        String modelo = modeloTF.getText();
        String tipo = tipoCB.getSelectionModel().getSelectedItem();

        //compruebo que la matricula introducida no se haya modificado, porque se debe mantener constante
        if (!Objects.equals(cocheSeleccionado.getMatricula(), matriculaTF.getText())) {
            ComprobacionesAlertasCambioEscena.mostrarAlerta("La matrícula no puede ser modificada.");
            matriculaTF.setText(cocheSeleccionado.getMatricula()); //cambio a la matricula original en caso de haberse modificarse
        } else if (validarCampos(cocheSeleccionado.getMatricula(), marca, modelo, tipo)) { //compruebo que todos los campos tienen datos
            //actualizo los campos que pueden ser modificados
            cocheSeleccionado.setMarca(marca);
            cocheSeleccionado.setModelo(modelo);
            cocheSeleccionado.setTipo(tipo);

            //compruebo si se ha llevado a cabo la actualización
            if (cocheDAO.actualizarCoche(cocheSeleccionado) > 0) {
                actualizarTabla(); //actualizo la tabla
            } else {
                ComprobacionesAlertasCambioEscena.mostrarAlerta("Error al actualizar los datos del coche.");
            }//if-else
        }//if-elseif
    }//onActualizarClick


    //método que limpia todos los campos, ya que se cancela lo que se estuviese queriendo hacer
    @FXML
    void onLimpiarClick(ActionEvent event) {
        limpiarCampos(); //llamo al método que elimina los datos de los campos
    }//onLimpiarClick


    //método que elimina el coche seleccionado
    @FXML
    void onBorrarClick(ActionEvent event) {
        Coche cocheSeleccionado = tableViewCoches.getSelectionModel().getSelectedItem(); //obtengo el coche seleccionado en el tableView y lo guardo en la variable cocheSeleccioando de tipo Coche

        //verifico que hay un coche seleccionado y si el método eliminar coche devuelve un 1, que indica que se va a proceder a la eliminación del coche
        if (cocheSeleccionado != null && cocheDAO.eliminarCoche(cocheSeleccionado) > 0) {
            actualizarTabla(); //actualizo la tabla
            limpiarCampos(); //limpio todos los campos
        } else {
            ComprobacionesAlertasCambioEscena.mostrarAlerta("No se ha podido eliminar el coche.");
        }//if-else
    }//onBorrarClick


    @FXML
    void onVerMultasClick(ActionEvent event) {
        Coche cocheSeleccionado = tableViewCoches.getSelectionModel().getSelectedItem(); //obtengo el coche seleccionado en el tableView y lo guardo en la variable cocheSeleccioando de tipo Coche

        if(cocheSeleccionado != null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("multas.fxml"));
                Parent root = fxmlLoader.load();

                //objetivo de lo siguiente --> pasar un coche (el seleccionado en esta pantalla) a la clase MultasController
                MultasController multasController = fxmlLoader.getController(); //cojo los datos de la clase MultasController (todos los datos de esa clase, o sea que puedo llamar a cualquier método que pueda necesitar)
                //con el control total de MultasController, llamo al método datosCocheMulta para pasarle el coche seleccionado
                multasController.datosCocheMulta(cocheSeleccionado); //le paso el coche seleccionado al método de la clase MultasController

                Scene scene = new Scene(root);
                Stage stage = (Stage) verMultasBoton.getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }//try-catch
        }//if
        else ComprobacionesAlertasCambioEscena.mostrarAlerta("Debe haber un coche seleccionado para obtener la información de sus multas.");
    }//onVerMultasClick


    //método que me valida los campos de entrada
    private boolean validarCampos(String matricula, String marca, String modelo, String tipo) {

        //validación de que todos los campos deben contener datos
        if (matricula.isEmpty() || marca.isEmpty() || modelo.isEmpty() || tipo == null) {
            ComprobacionesAlertasCambioEscena.mostrarAlerta("Todos los campos han de estar rellenos.");
            return false;
        }

        //validación del formato de la matrícula
        if (!ComprobacionesAlertasCambioEscena.validarMatricula(matricula)) { //llamo al método que comprueba el formato de la matrícula
            ComprobacionesAlertasCambioEscena.mostrarAlerta("La matrícula debe tener el formato nnnnLLL.");
            return false;
        }
        return true;
    }//validarCampos


    //método que verifica si la matrícula introducida ya existe o no (1 no, 0 si)
    private boolean validarMatriculaUnica(String matricula) {
        //llamo al método que accede a la base de datos y comprueba la existencia de la matrícula
        if (cocheDAO.existeMatricula(matricula) > 0) {
            ComprobacionesAlertasCambioEscena.mostrarAlerta("Esa matrícula ya está en uso. Pruebe con otra.");
            return false;
        }
        return true;
    }//validarMatriculaUnica


    //método para actualizar la tabla después de realizar cambios
    private void actualizarTabla() {
        List<Coche> listarCoches = cocheDAO.mostrarCoches(); //obtengo la lista de coches
        cochesOL = FXCollections.observableArrayList(listarCoches); //convierto a ObservableList
        tableViewCoches.setItems(cochesOL); //actualizo el TableView con la nueva lista
    }//actualizarTabla


    //método que limpia los campos de entrada
    private void limpiarCampos() {
        matriculaTF.clear();
        marcaTF.clear();
        modeloTF.clear();
        tipoCB.getSelectionModel().clearSelection();
    }//limpiarCampos
}//classs