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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nedas
 */
public class PopupRatingController implements Initializable {

    @FXML
    private Label specificFunctionLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label movieName;
    @FXML
    private TextField ratingBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goBack(ActionEvent event) {
        Stage stage = (Stage) ratingBox.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void submitRating(ActionEvent event) {
    }

}
