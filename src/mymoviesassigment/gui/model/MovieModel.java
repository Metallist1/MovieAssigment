/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.gui.model;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mymoviesassigment.be.Movie;
import mymoviesassigment.bll.LogicFacade;
import mymoviesassigment.bll.Manager;

/**
 *
 * @author nedas
 */
public class MovieModel {

    private static final MovieModel MovieSingleton = new MovieModel();
    private final LogicFacade logiclayer;
    private ObservableList<Movie> allMovies;

    /*
    Initialises the logic layer manager
     */
    private MovieModel() {
        try {
            logiclayer = new Manager();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Missing a required resource", e);
        }
    }

    /* Static 'instance' method */
    public static MovieModel getInstance() {
        return MovieSingleton;
    }

    /*
    Gets all categories from database and then returns a string list of all categories
     */
    public ObservableList<Movie> getAllMovies() {
        allMovies = FXCollections.observableArrayList();
        allMovies.addAll(logiclayer.getAllMovies());
        return allMovies;
    }
}
