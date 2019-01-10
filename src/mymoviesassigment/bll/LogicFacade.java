/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.bll;

import java.util.List;
import mymoviesassigment.be.Category;
import mymoviesassigment.be.Movie;


/**
 *
 * @author nedas
 */
public interface LogicFacade {

    public List<Category> getAllCategories();

    /*
    Gets a list of all Movies
     */
    //public List<Movie> getAllMovies();

    public List<Movie> getAllMovies();

    public Movie createMovie(String name, int rating, int imdbrating, String url);

    public Movie updateMovie(Movie movieToEdit, String name, int rating, int imdbrating, String url);

    public void deleteMovie(Movie selectedItem);

    public Category createCategory(String name);

    public Category updatedCategory(Category editingList, String name);

    public void deleteCategory(Category selectedItem);

    public void addToCategory(Category selectedItem, Movie selectedMovie);

    public void removeFromCategory(Category selectedItem, Movie selectedMovie);

}

