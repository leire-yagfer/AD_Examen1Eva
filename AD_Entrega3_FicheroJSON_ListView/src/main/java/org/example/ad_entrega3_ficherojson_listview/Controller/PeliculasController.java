package org.example.ad_entrega3_ficherojson_listview.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.ad_entrega3_ficherojson_listview.Model.Pelicula;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PeliculasController implements Initializable {

    //ATRIBUTOS
    @FXML
    private TextField tituloTF;

    @FXML
    private TextField directorTF;

    @FXML
    private TextField fechaTF;

    @FXML
    private TextField generoTF;

    @FXML
    private Button importarBoton;

    @FXML
    private ListView<Pelicula> peliculasLV; //ListView de tipo Pelicula

    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();


    //MÉTODOS
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //bloqueo la función de edición en los textField
        tituloTF.setEditable(false);
        directorTF.setEditable(false);
        fechaTF.setEditable(false);
        generoTF.setEditable(false);
    }//initialize


    //método que al pulsar el botón importar aparecen las películas en el ListView
    @FXML
    void onImportarClick(ActionEvent event) {
        try {
            //convierto el fichero JSON a un arrayList  -->  Deserializacion
            ArrayList<Pelicula> peliculas = JSON_MAPPER.readValue(new File("src/main/resources/FicheroJSON/Peliculas.json"), JSON_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, Pelicula.class));

            //paso el ArrayList de peliculas a ObservableList para que pueda ser utilizado en un ListView
            ObservableList<Pelicula> peliculasOL = FXCollections.observableArrayList(peliculas);

            //establezco la lista de elementos que se mostrará en el ListView llamado peliculasLV y le paso la ObservableList para que funcione correctamente
            peliculasLV.setItems(peliculasOL);


        } catch (IOException e) {
            System.out.println("Error al leer el fichero. " + e.getMessage());
        }
    }//onImportarClick


    //método específico cuando quiero mostrar datos de un elemento del ListView en un textField (SceneBuilder -> selecciono ListView -> code -> Mouse -> On Mouse Clicked)
    @FXML
    void onElegirPeliculaClick(MouseEvent event) {
        Pelicula pelicula = peliculasLV.getSelectionModel().getSelectedItem(); //obtengo la película seleccionada por el usuario en el ListView y la guardo en la variable pelicula de tipo Pelicula
        if (pelicula != null) { //si hay un elemento seleccionado
            //pongo los datos de la película en los diferentes TextFields
            tituloTF.setText(pelicula.getTitulo());
            fechaTF.setText(pelicula.getFecha());
            generoTF.setText(pelicula.getGenero());
            directorTF.setText(pelicula.getDirector());
        }//if
    }//onElegirPeliculaClick
}//class