/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.gui.model;

import java.io.IOException;
import java.util.List;
import mymoviesassigment.bll.LogicFacade;
import mymoviesassigment.bll.Manager;

/**
 *
 * @author nedas
 */
public class CategoryModel {

    private static final CategoryModel categorySingleton = new CategoryModel();
    private final LogicFacade logiclayer;

    /*
    Initialises the logic layer manager
     */
    private CategoryModel() {
        try {
            logiclayer = new Manager();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Missing a required resource", e);
        }
    }

    /* Static 'instance' method */
    public static CategoryModel getInstance() {
        return categorySingleton;
    }

    /*
    Gets all categories from database and then returns a string list of all categories
     */
    public List<String> getAllCategories() {
        return logiclayer.getAllCategories();
    }

}
