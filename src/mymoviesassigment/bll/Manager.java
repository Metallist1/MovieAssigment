/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.bll;

import java.io.IOException;
import java.util.List;
import mymoviesassigment.dal.MovieDAO;
/**
 *
 * @author nedas
 */
public class Manager implements LogicFacade {

    private final MovieDAO movieDAO;

    /*
    Initialises all classes in DAL
     */
    public Manager() throws IOException {
        movieDAO = new MovieDAO();
    }

    /*@Override
    public List<Movie> getAllMovies() {
        return MovieDAO.getAllMovies();
    }*/

    @Override
    public List<String> getAllCategories() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAllMovies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
