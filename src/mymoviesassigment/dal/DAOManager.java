/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.dal;

import java.io.IOException;
import java.util.List;
import mymoviesassigment.be.Category;
import mymoviesassigment.be.Movie;
import mymoviesassigment.dal.db.MovieDAO;
import mymoviesassigment.dal.db.CategoryDAO;
import mymoviesassigment.dal.db.CatDAO;
import mymoviesassigment.dal.exceptions.daoException;

/**
 *
 * @author nedas
 */
public class DAOManager implements DAOLogicFacade {

    private final MovieDAO movieDAO;
    private final CategoryDAO categoryDAO;
    private final CatDAO catDAO;

    /*
    Initialises all classes in DAL
     */
    public DAOManager() throws IOException {
        movieDAO = new MovieDAO();
        categoryDAO = new CategoryDAO();
        catDAO = new CatDAO();
    }

    @Override
    public List<Category> getAllCategories() throws daoException {
        try {
            return categoryDAO.getAllCategories();
        } catch (daoException ex) {
            throw new daoException(ex.getMessage());
        }
    }

    @Override
    public List<Movie> getAllMovies() throws daoException {
        try {
            return movieDAO.getAllMovies();
        } catch (daoException ex) {
            throw new daoException(ex.getMessage());
        }
    }

    @Override
    public Movie createMovie(String name, int rating, int imdbrating, String url) throws daoException {
        try {
            return movieDAO.createMovie(name, rating, imdbrating, url);
        } catch (daoException ex) {
            throw new daoException(ex.getMessage());
        }
    }

    @Override
    public Movie updateMovie(Movie movieToEdit, String name, int rating, int imdbrating, String url) throws daoException {
        try {
            return movieDAO.updateMovie(movieToEdit, name, rating, imdbrating, url);
        } catch (daoException ex) {
            throw new daoException(ex.getMessage());
        }
    }

    @Override
    public void deleteMovie(Movie selectedItem) throws daoException {
        try {
            catDAO.removeMoviesFromCat(selectedItem);
            movieDAO.removeMovie(selectedItem);
        } catch (daoException ex) {
            throw new daoException(ex.getMessage());
        }
    }

    @Override
    public Category createCategory(String name) throws daoException {
        try {
            return categoryDAO.createCategory(name);
        } catch (daoException ex) {
            throw new daoException(ex.getMessage());
        }
    }

    @Override
    public Category updatedCategory(Category editingList, String name) throws daoException {
        try {
            return categoryDAO.updateCategory(editingList, name);
        } catch (daoException ex) {
            throw new daoException(ex.getMessage());
        }
    }

    @Override
    public void deleteCategory(Category selectedItem) throws daoException {
        try {
            catDAO.removeFromCat(selectedItem);
            categoryDAO.deleteCategory(selectedItem);
        } catch (daoException ex) {
            throw new daoException(ex.getMessage());
        }
    }

    @Override
    public void addToCategory(Category selectedItem, Movie selectedMovie) throws daoException {
        try {
            catDAO.addToCategory(selectedItem, selectedMovie);
        } catch (daoException ex) {
            throw new daoException(ex.getMessage());
        }
    }

    @Override
    public void removeFromCategory(Category selectedItem, Movie selectedMovie) throws daoException {
        try {
            catDAO.removeFromCategory(selectedItem, selectedMovie);
        } catch (daoException ex) {
            throw new daoException(ex.getMessage());
        }
    }

    @Override
    public Movie updateMovieRating(Movie selectedItem, Integer newRating) throws daoException {
        try {
            return movieDAO.updateMovieRating(selectedItem, newRating);
        } catch (daoException ex) {
            throw new daoException(ex.getMessage());
        }
    }

    @Override
    public Movie updateMovieDate(Movie selectedItem) throws daoException {
        try {
            return movieDAO.updateMovieDate(selectedItem);
        } catch (daoException ex) {
            throw new daoException(ex.getMessage());
        }
    }

}
