/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.dal;

import java.util.List;
import mymoviesassigment.be.Category;
import mymoviesassigment.be.Movie;
import mymoviesassigment.dal.exceptions.daoException;

/**
 *
 * @author nedas
 */
public interface DAOLogicFacade {

    public List<Category> getAllCategories() throws daoException;

    public List<Movie> getAllMovies() throws daoException;

    public Movie createMovie(String name, int rating, int imdbrating, String url) throws daoException;

    public Movie updateMovie(Movie movieToEdit, String name, int rating, int imdbrating, String url) throws daoException;

    public void deleteMovie(Movie selectedItem) throws daoException;

    public Category createCategory(String name) throws daoException;

    public Category updatedCategory(Category editingList, String name) throws daoException;

    public void deleteCategory(Category selectedItem) throws daoException;

    public void addToCategory(Category selectedItem, Movie selectedMovie) throws daoException;

    public void removeFromCategory(Category selectedItem, Movie selectedMovie) throws daoException;

    public Movie updateMovieRating(Movie selectedItem, Integer newRating) throws daoException;

    public Movie updateMovieDate(Movie selectedItem) throws daoException;

}
