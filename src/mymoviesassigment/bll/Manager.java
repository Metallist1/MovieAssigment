/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.bll;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import mymoviesassigment.be.Category;
import mymoviesassigment.be.Movie;
import mymoviesassigment.bll.util.searchMovie;
import mymoviesassigment.dal.CatDAO;
import mymoviesassigment.dal.CategoryDAO;
import mymoviesassigment.dal.MovieDAO;
import mymoviesassigment.bll.exceptions.bllException;
import mymoviesassigment.dal.exceptions.daoException;

/**
 *
 * @author nedas
 */
public class Manager implements LogicFacade {

    private final MovieDAO movieDAO;
    private final CategoryDAO categoryDAO;
    private final CatDAO catDAO;
    private final searchMovie searchforMovie;

    /*
    Initialises all classes in DAL
     */
    public Manager() throws IOException {
        movieDAO = new MovieDAO();
        categoryDAO = new CategoryDAO();
        catDAO = new CatDAO();
        searchforMovie = new searchMovie();
    }

    @Override
    public List<Category> getAllCategories() throws bllException {
        try {
            return categoryDAO.getAllCategories();
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public List<Movie> getAllMovies() throws bllException {
        try {
            return movieDAO.getAllMovies();
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public Movie createMovie(String name, int rating, int imdbrating, String url) throws bllException {
        try {
            return movieDAO.createMovie(name, rating, imdbrating, url);
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public Movie updateMovie(Movie movieToEdit, String name, int rating, int imdbrating, String url) throws bllException {
        try {
            return movieDAO.updateMovie(movieToEdit, name, rating, imdbrating, url);
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public void deleteMovie(Movie selectedItem) throws bllException {
        try {
            movieDAO.removeMovie(selectedItem);
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public Category createCategory(String name) throws bllException {
        try {
            return categoryDAO.createCategory(name);
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public Category updatedCategory(Category editingList, String name) throws bllException {
        try {
            return categoryDAO.updateCategory(editingList, name);
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public void deleteCategory(Category selectedItem) throws bllException {
        try {
            catDAO.removeFromCat(selectedItem);
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
        try {
            categoryDAO.deleteCategory(selectedItem);
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public void addToCategory(Category selectedItem, Movie selectedMovie) throws bllException {
        try {
            catDAO.addToCategory(selectedItem, selectedMovie);
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public void removeFromCategory(Category selectedItem, Movie selectedMovie) throws bllException {
        try {
            catDAO.removeFromCategory(selectedItem, selectedMovie);
        } catch (daoException ex) {
            throw new bllException(ex.getMessage());
        }
    }

    @Override
    public ObservableList<Movie> searchMovie(ObservableList<Movie> currentMovies, String movieToFind) {
        return searchforMovie.search(currentMovies, movieToFind);
    }

}
