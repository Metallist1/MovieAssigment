/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.gui.model;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mymoviesassigment.be.Movie;
import mymoviesassigment.bll.LogicFacade;
import mymoviesassigment.bll.Manager;
import mymoviesassigment.bll.exceptions.bllException;
import mymoviesassigment.gui.exceptions.modelException;

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
    public ObservableList<Movie> getAllMovies() throws modelException {
        allMovies = FXCollections.observableArrayList();
        try {
            allMovies.addAll(logiclayer.getAllMovies());
            return allMovies;
        } catch (bllException ex) {
            throw new modelException(ex.getMessage());
        }
    }

    public ObservableList<Movie> getCurrentMovies() {
        return allMovies;
    }

    public void createMovie(String name, int rating, int imdbrating, String url) throws modelException {
        Movie createdMovie;
        try {
            createdMovie = logiclayer.createMovie(name, rating, imdbrating, url);
            allMovies.add(createdMovie);
        } catch (bllException ex) {
            throw new modelException(ex.getMessage());
        }
    }

    public void updateMovie(Movie movieToEdit, int movieIndex, String name, int rating, int imdbrating, String url) throws modelException {
        Movie updatedMovie;
        try {
            updatedMovie = logiclayer.updateMovie(movieToEdit, name, rating, imdbrating, url);
            allMovies.set(movieIndex, updatedMovie);
        } catch (bllException ex) {
            throw new modelException(ex.getMessage());
        }
    }

    public void deleteMovie(Movie selectedItem, int selectedIndex) throws modelException {
        try {
            logiclayer.deleteMovie(selectedItem);
            allMovies.remove(selectedIndex);
        } catch (bllException ex) {
            throw new modelException(ex.getMessage());
        }
    }

    public ObservableList<Movie> search(ObservableList<Movie> currentMovies, String movieToFind) {
        return logiclayer.searchMovie(currentMovies, movieToFind);
    }

    public void updateMovieRating(Movie selectedItem, int movieIndex, Integer newRating) throws modelException {
        Movie updatedMovie;
        try {
            updatedMovie = logiclayer.updateMovieRating(selectedItem, newRating);
            allMovies.set(movieIndex, updatedMovie);
        } catch (bllException ex) {
            throw new modelException(ex.getMessage());
        }
    }

    public void updateMovieDate(Movie selectedItem, int selectedIndex) throws modelException {
        Movie updatedMovie;
        try {
            updatedMovie = logiclayer.updateMovieDate(selectedItem);
            allMovies.set(selectedIndex, updatedMovie);
        } catch (bllException ex) {
            throw new modelException(ex.getMessage());
        }
    }
}
