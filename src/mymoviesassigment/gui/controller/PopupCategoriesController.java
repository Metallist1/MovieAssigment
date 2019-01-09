/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mymoviesassigment.be.Category;
import mymoviesassigment.gui.model.CategoryModel;
import mymoviesassigment.gui.model.MovieModel;

/**
 * FXML Controller class
 *
 * @author nedas
 */
public class PopupCategoriesController implements Initializable {

    @FXML
    private Label specificFunctionLabel;
    @FXML
    private TextField categoryNameField;
    @FXML
    private Label errorLabel;

    private CategoryModel categoryModel;
    private boolean isEditing = false;
    private Category editingList;
    private int categoryIndex;
    MainWindowController controller1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryModel = CategoryModel.getInstance();
    }

    @FXML
    private void saveCategoryName(ActionEvent event) {
        String name = categoryNameField.getText().trim(); //Eliminates all white spaces (fron and back of the string)
        if (name != null && name.length() > 0 && name.length() < 50) { //If the string is not null and doesnt excede the databases char length
            if (!isEditing) {
                categoryModel.createPlaylist(name);
                errorLabel.setText("Success: Successfully created the playlist");
            } else {
                categoryModel.editPlaylist(editingList,categoryIndex, name);
                errorLabel.setText("Success: Successfully renamed the playlist");
            }
        } else {
            errorLabel.setText("Error : Check if the name you inserted is valid");
        }
        controller1.refreshCategoryList(); // Refreshes the list on the main window to reflect changes
    }

    @FXML
    private void goBackFromCategory(ActionEvent event) {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.close();
    }

    void setInfo(Category selectedItem , int categoryIndex) {
        isEditing = true;
        editingList = selectedItem;
        categoryNameField.setText(selectedItem.getName());
        this.categoryIndex=categoryIndex;
    }

    /*
    Sets up the controller of the main window
     */
    void setController(MainWindowController controller1) {
        this.controller1 = controller1;
        if (isEditing) {
            specificFunctionLabel.setText("Editing Category");
        } else {
            specificFunctionLabel.setText("Create Category");
        }
    }

}
