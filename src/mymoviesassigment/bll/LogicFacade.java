/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.bll;

import java.util.List;


/**
 *
 * @author nedas
 */
public interface LogicFacade {

    public List<String> getAllCategories();

    /*
    Gets a list of all Movies
     */
    //public List<Movie> getAllMovies();

    public List<String> getAllMovies();

}

