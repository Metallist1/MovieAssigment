/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.gui.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import mymoviesassigment.gui.exceptions.modelException;
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
    private TableColumn<Movie, String> nameColumn;
    @FXML
    private TableColumn<Movie, Integer> timeColumn;
    @FXML
    private TableColumn<Category, String> CategoryNames;
    @FXML
    private TableColumn<Category, Integer> totalMovieCount;
    @FXML
    private TableColumn<Movie, String> CatMovieName;
    @FXML
    private TableColumn<Movie, Integer> imdbRating;
    @FXML
    private TableColumn<Movie, Integer> userRating;
    @FXML
    private TableView<Category> categoryTableView;
    @FXML
    private TableView<Movie> moviesInCategory;
    @FXML
    private TableView<Movie> movieTableView;

    private ObservableList<Movie> observableListMovie;
    private ObservableList<Category> observableListCategory;
    private CategoryModel categoryModel;
    private MovieModel movieModel;
    @FXML
    private ChoiceBox<Integer> ratingChoice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ratingChoice.setItems(FXCollections.observableArrayList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        );
        categoryModel = CategoryModel.getInstance();
        movieModel = MovieModel.getInstance();
        try {
            observableListMovie = movieModel.getAllMovies(); //Loads all movies
        } catch (modelException ex) {
            setUpAlert(ex.getMessage());
        }
        try {
            observableListCategory = categoryModel.getAllCategories(); //Loads all categories
        } catch (modelException ex) {
            setUpAlert(ex.getMessage());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -2);
        Date d1 = calendar.getTime();
        java.util.Date utilStartDate = d1;
        java.sql.Date date = new java.sql.Date(utilStartDate.getTime());
        for (Movie movie : observableListMovie) {
            if (movie.getLastView() != null) {
                if (movie.getLastView().before(date) && movie.getUserRating() < 6) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("You currently have movies that you watched 2 years ago and rated with a score of 6 or lower. Please consider deleting them.");
                    alert.showAndWait();
                    break;
                }
            }
        }
        movieTableView.setItems(observableListMovie);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        userRating.setCellValueFactory(new PropertyValueFactory<>("userRating"));
        imdbRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("lastView"));

        categoryTableView.setItems(observableListCategory);
        CategoryNames.setCellValueFactory(new PropertyValueFactory<>("name"));
        totalMovieCount.setCellValueFactory(new PropertyValueFactory<>("movieCount"));

        CatMovieName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    private void rateMovie(ActionEvent event) {
        if (moviesInCategory.getSelectionModel().getSelectedIndex() != -1 && ratingChoice.getSelectionModel().getSelectedIndex() != -1) {
            Movie movieToBeRated = null;
            int movieIndex = 0;
            for (Movie movie : movieTableView.getItems()) {
                if (movie.getName().equals(moviesInCategory.getSelectionModel().getSelectedItem().getName())) {
                    movieToBeRated = movieTableView.getItems().get(movieIndex);
                    break;
                }
                movieIndex++;
            }
            if (movieToBeRated != null) {
                try {
                    movieModel.updateMovieRating(movieToBeRated, movieIndex, ratingChoice.getSelectionModel().getSelectedItem());
                    refreshMovieList(false);
                } catch (modelException ex) {
                    setUpAlert(ex.getMessage());
                }
            }
        }
    }

    @FXML
    private void search(KeyEvent event) {
        if (searchTextBox.getText() == null || searchTextBox.getText().length() <= 0) {
            //If there is no value inserted. Set up normal movies
            refreshMovieList(false);
        } else { //Else call method from movie filter by specifying both the movie list and the query
            ObservableList<Movie> foundMovieList;
            try {
                foundMovieList = movieModel.search(movieModel.getCurrentMovies(), searchTextBox.getText());
                if (foundMovieList != null) { //If anything is returned. Display it.
                    movieTableView.setItems(foundMovieList);
                }
            } catch (modelException ex) {
                setUpAlert(ex.getMessage());
            }
        }
    }

    @FXML
    private void playMovie(ActionEvent event) {
        try {
            play();
        } catch (modelException ex) {
            setUpAlert(ex.getMessage());
        } catch (IOException ex) {
            setUpAlert(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            setUpAlert(ex.getMessage());
        }
    }

    private void play() throws modelException, IOException, IllegalArgumentException {
        Desktop.getDesktop().open(new File(moviesInCategory.getSelectionModel().getSelectedItem().getUrl()));
        movieModel.updateMovieDate(moviesInCategory.getSelectionModel().getSelectedItem(), moviesInCategory.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void createCategory(ActionEvent event) {
        try {
            setUpScenes(1, false);
        } catch (IOException ex) {
            setUpAlert(ex.getMessage());
        }
    }

    @FXML
    private void editCategory(ActionEvent event) {
        if (categoryTableView.getSelectionModel().getSelectedIndex() != -1) {
            try {
                setUpScenes(1, true);
            } catch (IOException ex) {
                setUpAlert(ex.getMessage());
            }
        }
    }

    @FXML
    private void deleteCategory(ActionEvent event) {
        if (categoryTableView.getSelectionModel().getSelectedIndex() != -1) {
            try {
                categoryModel.deletePlaylist(categoryTableView.getSelectionModel().getSelectedItem(), categoryTableView.getSelectionModel().getSelectedIndex()); // calls delete categories from category table
                moviesInCategory.getItems().clear(); //clears items in category movie view
                refreshCategoryList(); //refreshes the list for the changes to take place
            } catch (modelException ex) {
                setUpAlert(ex.getMessage());
            }
        }
    }

    @FXML
    private void removeMovie(ActionEvent event) {
        if (moviesInCategory.getSelectionModel().getSelectedIndex() != -1 && categoryTableView.getSelectionModel().getSelectedIndex() != -1) {
            try {
                System.out.println(moviesInCategory.getSelectionModel().getSelectedItem().getID());
                categoryModel.removeMovieFromCategory(categoryTableView.getSelectionModel().getSelectedItem(), categoryTableView.getSelectionModel().getSelectedIndex(), moviesInCategory.getSelectionModel().getSelectedItem(), moviesInCategory.getSelectionModel().getFocusedIndex());
                refreshCatList();
            } catch (modelException ex) {
                setUpAlert(ex.getMessage());
            }
        }
    }

    @FXML
    private void addMovie(ActionEvent event) {
        if (categoryTableView.getSelectionModel().getSelectedIndex() != -1 && movieTableView.getSelectionModel().getSelectedIndex() != -1) {
            try {
                categoryModel.addToCategory(categoryTableView.getSelectionModel().getSelectedItem(), categoryTableView.getSelectionModel().getSelectedIndex(), movieTableView.getSelectionModel().getSelectedItem());
                refreshCatList();
            } catch (modelException ex) {
                setUpAlert(ex.getMessage());
            }
        }
    }

    @FXML
    private void createMovie(ActionEvent event) {
        try {
            setUpScenes(2, false);
        } catch (IOException ex) {
            setUpAlert(ex.getMessage());
        }
    }

    @FXML
    private void editMovie(ActionEvent event) {
        if (movieTableView.getSelectionModel().getSelectedIndex() != -1) {
            try {
                setUpScenes(2, true);
            } catch (IOException ex) {
                setUpAlert(ex.getMessage());
            }
        }
    }

    @FXML
    private void deleteMovie(ActionEvent event) {
        if (movieTableView.getSelectionModel().getSelectedIndex() != -1) {
            try {
                movieModel.deleteMovie(movieTableView.getSelectionModel().getSelectedItem(), movieTableView.getSelectionModel().getSelectedIndex()); // calls delete playlist from playlistModel
                refreshMovieList(true);
            } catch (modelException ex) {
                setUpAlert(ex.getMessage());
            }
        }
    }

    private void setUpScenes(int whichScene, boolean isEditing) throws IOException {
        Parent root1;
        if (whichScene == 1) { //If the scene needed is category view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mymoviesassigment/gui/view/popupCategories.fxml"));
            root1 = (Parent) fxmlLoader.load();
            if (isEditing) {
                fxmlLoader.<PopupCategoriesController>getController().setInfo(categoryTableView.getSelectionModel().getSelectedItem(), categoryTableView.getSelectionModel().getFocusedIndex()); // Tells the playlist controller class that the method will be editing its name
            }
            fxmlLoader.<PopupCategoriesController>getController().setController(this); //Sets controler by default for both creating and editing categories
        } else { // If the scene needed is movie view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mymoviesassigment/gui/view/popupMovie.fxml"));
            root1 = (Parent) fxmlLoader.load();
            if (isEditing) {
                fxmlLoader.<PopupMovieController>getController().setInfo(movieTableView.getSelectionModel().getSelectedItem(), movieTableView.getSelectionModel().getFocusedIndex());// Tells the song controller class that the method will be editing song info
            }
            fxmlLoader.<PopupMovieController>getController().setController(this); //Sets controler by default for both creating and editing movies
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1, 800, 800));
        stage.centerOnScreen();
        stage.show();
    }

    void refreshMovieList(boolean editing) {
        if (editing) {
            try {
                observableListCategory = categoryModel.getAllCategories();
                refreshCatList();
            } catch (modelException ex) {
                setUpAlert(ex.getMessage());
            }
        } else {
            try {
                observableListMovie = movieModel.getCurrentMovies();
                movieTableView.setItems(observableListMovie);
            } catch (modelException ex) {
                setUpAlert(ex.getMessage());
            }
        }
    }

    void refreshCategoryList() {
        try {
            observableListCategory = categoryModel.getCurrentCategories();
            categoryTableView.setItems(observableListCategory);
        } catch (modelException ex) {
            setUpAlert(ex.getMessage());
        }
    }

    @FXML
    private void displayMoviesInCategory(MouseEvent event) {
        if (categoryTableView.getSelectionModel().getSelectedIndex() != -1) {
            moviesInCategory.getItems().clear();
            List<Movie> toBeAddedMovieList = categoryTableView.getSelectionModel().getSelectedItem().getAllMoviesInCategory(); //Gets specific playlist song list
            for (int x = toBeAddedMovieList.size() - 1; x >= 0; x--) { //counts down from the bottom to top so the last song to be added would play last.
                moviesInCategory.getItems().add(toBeAddedMovieList.get(x)); //adds the song to the table
            }
        }
    }

    private void refreshCatList() {
        moviesInCategory.getItems().clear();
        refreshCategoryList();
        if (categoryTableView.getSelectionModel().getSelectedIndex() != -1) {
            List<Movie> toBeAddedMovieList = categoryTableView.getSelectionModel().getSelectedItem().getAllMoviesInCategory(); //Gets specific playlist song list
            for (int x = toBeAddedMovieList.size() - 1; x >= 0; x--) { //counts down from the bottom to top so the last song to be added would play last.
                moviesInCategory.getItems().add(toBeAddedMovieList.get(x)); //adds the song to the table
            }
        }
    }

    protected void setUpAlert(String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
