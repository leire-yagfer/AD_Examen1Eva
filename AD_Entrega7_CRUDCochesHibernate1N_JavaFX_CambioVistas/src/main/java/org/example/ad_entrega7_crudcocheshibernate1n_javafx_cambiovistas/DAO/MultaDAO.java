package org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.DAO;

import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Model.Coche;
import org.example.ad_entrega7_crudcocheshibernate1n_javafx_cambiovistas.Model.Multa;

import java.util.List;

public interface MultaDAO {
    List<Multa> mostrarMultas(Coche coche); //método para mostrar todas las multas almacenados en la base de datos
    int insertarMulta(Multa insertarMulta, Coche coche); //método para insertar una nueva multa. Le paso el coche para que se la asigne al coche cuya matrícula es fija.
    int eliminarMulta(Multa eliminarMulta); //método para eliminar una multa
    int actualizarMulta(Multa actualizarMulta); //método para actualizar una multa
}//class