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
import mymoviesassigment.dal.CategoryDAO;
import mymoviesassigment.dal.MovieDAO;
/**
 *
 * @author nedas
 */
public class Manager implements LogicFacade {

    private final MovieDAO movieDAO;
    private final CategoryDAO categoryDAO;

    /*
    Initialises all classes in DAL
     */
    public Manager() throws IOException {
        movieDAO = new MovieDAO();
        categoryDAO = new CategoryDAO();
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

}
