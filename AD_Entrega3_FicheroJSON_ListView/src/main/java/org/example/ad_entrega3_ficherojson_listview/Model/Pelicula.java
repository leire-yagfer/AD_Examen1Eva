package org.example.ad_entrega3_ficherojson_listview.Model;

public class Pelicula {

    //ATRIBUTOS
    private int id;
    private String titulo;
    private String fecha;
    private String director;
    private String genero;


    //CONSTRUCTOR
    //vacio porque JSON lo requiere
    public Pelicula() {
    }

    public Pelicula(String titulo, String fecha, String director, String genero) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.director = director;
        this.genero = genero;
    }


    //GET Y SET
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


    //TOSTIRNG para que me muestre el título en el ListView
    @Override
    public String toString() {
        return titulo;
    }
}