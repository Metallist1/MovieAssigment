/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mymoviesassigment.be.Category;
import mymoviesassigment.gui.exceptions.modelException;
import mymoviesassigment.gui.model.CategoryModel;

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
    private ObservableList<Category> observableListCategory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryModel = CategoryModel.getInstance();
        try {
            observableListCategory = categoryModel.getCurrentCategories(); //Loads all movies
        } catch (modelException ex) {
            controller1.setUpAlert(ex.getMessage());
        }
    }

    @FXML
    private void saveCategoryName(ActionEvent event) throws modelException {
        boolean isFound = false;
        String name = categoryNameField.getText().trim(); //Eliminates all white spaces (fron and back of the string)
        if (name != null && name.length() > 0 && name.length() < 50) { //If the string is not null and doesnt excede the databases char length
            for (Category category : observableListCategory) {
                if (category.getName() == null ? name == null : category.getName().equals(name)) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                if (!isEditing) {
                    categoryModel.createPlaylist(name);
                    errorLabel.setText("Success: Successfully created the playlist");
                } else {
                    categoryModel.editPlaylist(editingList, categoryIndex, name);
                    errorLabel.setText("Success: Successfully renamed the playlist");
                }
            } else {
                errorLabel.setText("Error : Names should not be the same");
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

    void setInfo(Category selectedItem, int categoryIndex) {
        isEditing = true;
        editingList = selectedItem;
        categoryNameField.setText(selectedItem.getName());
        this.categoryIndex = categoryIndex;
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
