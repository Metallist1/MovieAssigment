/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.gui.model;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mymoviesassigment.be.Category;
import mymoviesassigment.be.Movie;
import mymoviesassigment.bll.Manager;
import mymoviesassigment.bll.exceptions.bllException;
import mymoviesassigment.gui.exceptions.modelException;
import mymoviesassigment.bll.LogicFacade;

/**
 *
 * @author nedas
 */
public class CategoryModel {

    private static final CategoryModel categorySingleton = new CategoryModel();
    private final LogicFacade logiclayer;
    private ObservableList<Category> allCategory;

    /*
    Initialises the logic layer manager
     */
    private CategoryModel() {
        try {
            logiclayer = new Manager();
        } catch (IOException e) {
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
    public ObservableList<Category> getAllCategories() throws modelException {
        allCategory = FXCollections.observableArrayList();
        try {
            allCategory.addAll(logiclayer.getAllCategories());
        } catch (bllException ex) {
            throw new modelException(ex.getMessage());
        }
        return allCategory;
    }

    public ObservableList<Category> getCurrentCategories() throws modelException {
        return allCategory;
    }

    public void createPlaylist(String name) throws modelException {
        Category createdCategory;
        try {
            createdCategory = logiclayer.createCategory(name);
            allCategory.add(createdCategory);
        } catch (bllException ex) {
            throw new modelException(ex.getMessage());
        }
    }

    public void editPlaylist(Category editingList, int categoryIndex, String name) throws modelException {
        Category updatedCategory;
        try {
            updatedCategory = logiclayer.updatedCategory(editingList, name);
            allCategory.set(categoryIndex, updatedCategory);
        } catch (bllException ex) {
            throw new modelException(ex.getMessage());
        }
    }

    public void deletePlaylist(Category selectedItem, int selectedIndex) throws modelException {
        try {
            logiclayer.deleteCategory(selectedItem);
            allCategory.remove(selectedIndex);
        } catch (bllException ex) {
            throw new modelException(ex.getMessage());
        }
    }

    public void addToCategory(Category selectedItem, int selectedIndex, Movie selectedMovie) throws modelException {
        try {
            logiclayer.addToCategory(selectedItem, selectedMovie);
            List<Movie> newList = selectedItem.getAllMoviesInCategory();
            newList.add(selectedMovie);
            selectedItem.setAllMoviesInCategory(newList);
            selectedItem.setMovieCount(selectedItem.getMovieCount() + 1);
            allCategory.set(selectedIndex, selectedItem);
        } catch (bllException ex) {
            throw new modelException(ex.getMessage());
        }
    }

    public void removeMovieFromCategory(Category selectedItem, int selectedIndex, Movie selectedMovie, int movieIndex) throws modelException {
        try {
            logiclayer.removeFromCategory(selectedItem, selectedMovie);
            List<Movie> newList = selectedItem.getAllMoviesInCategory();
            newList.remove(movieIndex);
            selectedItem.setAllMoviesInCategory(newList);
            selectedItem.setMovieCount(selectedItem.getMovieCount() - 1);
            allCategory.set(selectedIndex, selectedItem);
        } catch (bllException ex) {
            throw new modelException(ex.getMessage());
        }
    }
}
