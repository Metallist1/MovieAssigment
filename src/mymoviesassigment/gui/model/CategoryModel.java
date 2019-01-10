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
import mymoviesassigment.bll.LogicFacade;
import mymoviesassigment.bll.Manager;

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
    public ObservableList<Category> getAllCategories() {
        allCategory = FXCollections.observableArrayList();
        allCategory.addAll(logiclayer.getAllCategories());
        return allCategory;
    }

    public ObservableList<Category> getCurrentCategories() {
        return allCategory;
    }

    public void createPlaylist(String name) {
        Category createdCategory = logiclayer.createCategory(name);
        allCategory.add(createdCategory);
    }

    public void editPlaylist(Category editingList, int categoryIndex, String name) {
        Category updatedCategory = logiclayer.updatedCategory(editingList, name);
        allCategory.set(categoryIndex, updatedCategory);
    }

    public void deletePlaylist(Category selectedItem, int selectedIndex) {
        logiclayer.deleteCategory(selectedItem);
        allCategory.remove(selectedIndex);
    }

    public void addToCategory(Category selectedItem, Movie selectedMovie) {
        logiclayer.addToCategory(selectedItem, selectedMovie);
        List<Movie> newList = selectedItem.getAllMoviesInCategory();
        newList.add(selectedMovie);
        selectedItem.setAllMoviesInCategory(newList);
        selectedItem.setMovieCount(selectedItem.getMovieCount() + 1);
    }

    public void removeMovieFromCategory(Category selectedItem, Movie selectedMovie, int movieIndex) {
        logiclayer.removeFromCategory(selectedItem, selectedMovie);
        List<Movie> newList = selectedItem.getAllMoviesInCategory();
        newList.remove(movieIndex);
        selectedItem.setAllMoviesInCategory(newList);
        selectedItem.setMovieCount(selectedItem.getMovieCount() - 1);
    }
}
