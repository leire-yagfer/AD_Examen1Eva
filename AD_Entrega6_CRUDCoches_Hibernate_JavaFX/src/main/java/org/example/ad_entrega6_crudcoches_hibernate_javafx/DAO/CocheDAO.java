package org.example.ad_entrega6_crudcoches_hibernate_javafx.DAO;

import org.example.ad_entrega6_crudcoches_hibernate_javafx.Model.Coche;

import java.util.List;

public interface CocheDAO {
    List<Coche> mostrarCoches(); //método para mostrar todos los coches almacenados en la base de datos
    int insertarCoche(Coche coche); //método para insertar un nuevo coche
    int eliminarCoche(Coche coche); //método para eliminar un coche
    int actualizarCoche(Coche coche); //método para actualizar un coche
    int existeMatricula(String matricula); //método para verificar si una matrícula ya existe
}//class