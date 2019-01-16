/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.bll.util;

import javafx.collections.ObservableList;
import mymoviesassigment.be.Movie;

/**
 *
 * @author nedas
 */
public class searchMovie {

    public ObservableList<Movie> search(ObservableList<Movie> items, String text) {
        return items.filtered((t) -> t.getName().toLowerCase().startsWith(text.toLowerCase()));
    }
}
