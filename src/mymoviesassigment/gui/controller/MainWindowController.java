/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mymoviesassigment.be.Category;
import mymoviesassigment.be.Movie;
import mymoviesassigment.gui.model.CategoryModel;
import mymoviesassigment.gui.model.MovieModel;

/**
 * FXML Controller class
 *
 * @author nedas
 */
public class MainWindowController implements Initializable {

    @FXML
    private Button playButton;
    @FXML
    private Button rateMovie;
    @FXML
    private TextField searchTextBox;
    @FXML
    private TableColumn<?, ?> nameColumn;
    @FXML
    private TableColumn<?, ?> timeColumn;
    @FXML
    private TableColumn<?, ?> CategoryNames;
    @FXML
    private TableColumn<?, ?> totalMovieCount;
    @FXML
    private TableColumn<?, ?> CatId;
    @FXML
    private TableColumn<?, ?> CatMovieName;
    @FXML
    private TableColumn<?, ?> imdbRating;
    @FXML
    private TableColumn<?, ?> userRating;
    @FXML
    private TableView<Category> categoryTableView;
    @FXML
    private TableView<?> moviesInCategory;
    @FXML
    private TableView<Movie> movieTableView;

    private ObservableList<Movie> observableListMovie;
    private ObservableList<Category> observableListCategory;
    private CategoryModel categoryModel;
    private MovieModel movieModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryModel = CategoryModel.getInstance();
        movieModel = MovieModel.getInstance();
        observableListMovie = movieModel.getAllMovies(); //Loads all movies
        //observableListCategory = categoryModel.getAllCategories(); //Loads all categories
        movieTableView.setItems(observableListMovie);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        imdbRating.setCellValueFactory(new PropertyValueFactory<>("userRating"));
        userRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("lastView"));

        /*
        tableViewSongs.setItems(observableListSong);
        playlistSongNames.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlistSongTotalCount.setCellValueFactory(new PropertyValueFactory<>("songCount"));
        playlistSongTotalTime.setCellValueFactory(new PropertyValueFactory<>("totalTimeString"));

        playlistTableView.setItems(observableListPlay);
        songsInPlaylistName.setCellValueFactory(new PropertyValueFactory<>("title"));
        songInPlaylistID.setCellValueFactory(new PropertyValueFactory<>("IDinsideList"));
         */
    }

    @FXML
    private void rateMovie(ActionEvent event) {
    }

    @FXML
    private void search(KeyEvent event) {
    }

    @FXML
    private void displaySongsInPlaylist(MouseEvent event) {
    }

    @FXML
    private void playMovie(ActionEvent event) {
    }

    @FXML
    private void createCategory(ActionEvent event) throws IOException {
        setUpScenes(1, false);
    }

    @FXML
    private void editCategory(ActionEvent event) throws IOException {
        setUpScenes(1, true);
    }

    @FXML
    private void deleteCategory(ActionEvent event) {
    }

    @FXML
    private void moveMovieUp(ActionEvent event) {
    }

    @FXML
    private void moveMovieDown(ActionEvent event) {
    }

    @FXML
    private void removeMovie(ActionEvent event) {
    }

    @FXML
    private void addMovie(ActionEvent event) {
    }

    @FXML
    private void createMovie(ActionEvent event) throws IOException {
        setUpScenes(2, false);
    }

    @FXML
    private void editMovie(ActionEvent event) throws IOException {
        setUpScenes(2, true);
    }

    @FXML
    private void deleteMovie(ActionEvent event) {
        if (movieTableView.getSelectionModel().getSelectedIndex() != -1) {
            movieModel.deleteMovie(movieTableView.getSelectionModel().getSelectedItem(),movieTableView.getSelectionModel().getSelectedIndex()); // calls delete playlist from playlistModel
            refreshMovieList(true);
            
        }
    }

    private void setUpScenes(int whichScene, boolean isEditing) throws IOException {
        Parent root1;
        if (whichScene == 1) { //If the scene needed is playlist view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mymoviesassigment/gui/view/popupCategories.fxml"));
            root1 = (Parent) fxmlLoader.load();
            if (isEditing) {
                fxmlLoader.<PopupCategoriesController>getController().setInfo(categoryTableView.getSelectionModel().getSelectedItem()); // Tells the playlist controller class that the method will be editing its name
            }
            fxmlLoader.<PopupCategoriesController>getController().setController(this); //Sets controler by default for both creating and editing playlists
        } else { // If the scene needed is song view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mymoviesassigment/gui/view/popupMovie.fxml"));
            root1 = (Parent) fxmlLoader.load();
            if (isEditing) {
                fxmlLoader.<PopupMovieController>getController().setInfo(movieTableView.getSelectionModel().getSelectedItem(), movieTableView.getSelectionModel().getFocusedIndex());// Tells the song controller class that the method will be editing song info
            }
            fxmlLoader.<PopupMovieController>getController().setController(this); //Sets controler by default for both creating and editing songs
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1, 800, 800));
        stage.centerOnScreen();
        stage.show();
    }

    void refreshMovieList(boolean editing) {
        //movieTableView.getItems().clear();
        observableListMovie = movieModel.getCurrentMovies();
        movieTableView.setItems(observableListMovie);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        imdbRating.setCellValueFactory(new PropertyValueFactory<>("userRating"));
        userRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("lastView"));
    }
}
