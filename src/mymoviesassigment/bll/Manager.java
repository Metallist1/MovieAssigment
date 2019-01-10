/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.bll;

import java.io.IOException;
import java.util.List;
import mymoviesassigment.be.Category;
import mymoviesassigment.be.Movie;
import mymoviesassigment.dal.CatDAO;
import mymoviesassigment.dal.CategoryDAO;
import mymoviesassigment.dal.MovieDAO;

/**
 *
 * @author nedas
 */
public class Manager implements LogicFacade {

    private final MovieDAO movieDAO;
    private final CategoryDAO categoryDAO;
    private final CatDAO catDAO;

    /*
    Initialises all classes in DAL
     */
    public Manager() throws IOException {
        movieDAO = new MovieDAO();
        categoryDAO = new CategoryDAO();
        catDAO = new CatDAO();
    }

    /*@Override
    public List<Movie> getAllMovies() {
        return MovieDAO.getAllMovies();
    }*/
    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieDAO.getAllMovies();
    }

    @Override
    public Movie createMovie(String name, int rating, int imdbrating, String url) {
        return movieDAO.createMovie(name, rating, imdbrating, url);
    }

    @Override
    public Movie updateMovie(Movie movieToEdit, String name, int rating, int imdbrating, String url) {
        return movieDAO.updateMovie(movieToEdit, name, rating, imdbrating, url);
    }

    @Override
    public void deleteMovie(Movie selectedItem) {
        movieDAO.removeMovie(selectedItem);
    }

    @Override
    public Category createCategory(String name) {
        return categoryDAO.createCategory(name);
    }

    @Override
    public Category updatedCategory(Category editingList, String name) {
        return categoryDAO.updateCategory(editingList, name);
    }

    @Override
    public void deleteCategory(Category selectedItem) {
        catDAO.removeFromCat(selectedItem);
        categoryDAO.deleteCategory(selectedItem);
    }

    @Override
    public void addToCategory(Category selectedItem, Movie selectedMovie) {
        catDAO.addToCategory(selectedItem, selectedMovie);
    }

    @Override
    public void removeFromCategory(Category selectedItem, Movie selectedMovie) {
        catDAO.removeFromCategory(selectedItem, selectedMovie);
    }

}
