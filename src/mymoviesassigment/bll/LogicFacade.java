/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.bll;

import java.util.List;
import javafx.collections.ObservableList;
import mymoviesassigment.be.Category;
import mymoviesassigment.be.Movie;
import mymoviesassigment.bll.exceptions.bllException;

/**
 *
 * @author nedas
 */
public interface LogicFacade {

    public List<Category> getAllCategories() throws bllException;

    public List<Movie> getAllMovies() throws bllException;

    public Movie createMovie(String name, int rating, int imdbrating, String url) throws bllException;

    public Movie updateMovie(Movie movieToEdit, String name, int rating, int imdbrating, String url) throws bllException;

    public void deleteMovie(Movie selectedItem) throws bllException;

    public Category createCategory(String name) throws bllException;

    public Category updatedCategory(Category editingList, String name) throws bllException;

    public void deleteCategory(Category selectedItem) throws bllException;

    public void addToCategory(Category selectedItem, Movie selectedMovie) throws bllException;

    public void removeFromCategory(Category selectedItem, Movie selectedMovie) throws bllException;

    public ObservableList<Movie> searchMovie(ObservableList<Movie> currentMovies, String movieToFind);

}
