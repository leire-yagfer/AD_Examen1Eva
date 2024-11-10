package org.example.ad_entrega7_bueno_crudcoches_hibernate1an_javafx.CRUD;

import com.example.cochesmultacrudexamen.clases.Coches;

import java.util.ArrayList;

public interface CochesCRUDimpl {
    int insertarCoche(Coches coches);
    boolean comprobarMatricula(String matricula);
    ArrayList<Coches> getCoches();
    boolean eliminarCoche(Coches coches);
    boolean editarCoche(Coches coches);

}

