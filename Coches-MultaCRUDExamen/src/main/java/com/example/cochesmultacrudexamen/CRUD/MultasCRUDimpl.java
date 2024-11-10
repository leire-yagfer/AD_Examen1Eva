package com.example.cochesmultacrudexamen.CRUD;

import com.example.cochesmultacrudexamen.clases.Coches;
import com.example.cochesmultacrudexamen.clases.Multas;

import java.util.ArrayList;
import java.util.List;

public interface MultasCRUDimpl {
    boolean insertaMulta(Multas multas);
    boolean eliminarMulta(Multas multas);
    boolean actualizarMulta(Multas multas);
    ArrayList<Multas> getMultas();
    List<Multas> obtenerMultas(Coches coches);
    boolean comprobarIdMulta(int id);

}
