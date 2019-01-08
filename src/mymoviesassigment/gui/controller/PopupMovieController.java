/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author nedas
 */
public class PopupMovieController implements Initializable {

    @FXML
    private Label specificFunctionLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ratingField;
    @FXML
    private Label timeField;
    @FXML
    private Label urlField;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField IMDBratingField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void chooseURL(ActionEvent event) {
    }

    @FXML
    private void goBack(ActionEvent event) {
    }

    @FXML
    private void saveMovie(ActionEvent event) {
    }
    
}
