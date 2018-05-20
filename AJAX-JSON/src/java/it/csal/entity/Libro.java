package it.csal.entity;

import it.csal.database.LibrerieDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class Libro {

    private String title;
    private int id;
    private ArrayList<Autore> authors;

    public Libro(int id, String title) {
        this.title = title;
        this.id = id;
        authors = LibrerieDAO.loadAutoriLibro(id);

    }

    public ArrayList<Autore> getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public int getID() {
        return id;
    }

}
