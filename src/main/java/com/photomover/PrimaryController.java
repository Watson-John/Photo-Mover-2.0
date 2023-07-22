package com.photomover;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;


public class PrimaryController {

    @FXML
    private TextField sourceTextField;

    @FXML
    private TextField destinationTextField;

    @FXML
    private TextField pathpattern; // Added for the path pattern TextField

    @FXML
    private TextField renamepattern; // Added for the rename pattern TextField

    @FXML
    private Label pathPatternPreview;

    @FXML
    private Label renamePatternPreview;

    @FXML
    private void browseSourceFolder() {
        // Open the folder selection dialog for Folder 1
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Source Folder");

        // Get the current stage from any JavaFX node (e.g., the sourceTextField)
        Stage stage = (Stage) sourceTextField.getScene().getWindow();

        // Show the folder selection dialog
        java.io.File selectedFolder = directoryChooser.showDialog(stage);

        // Update the sourceTextField with the selected folder path (if a folder was selected)
        if (selectedFolder != null) {
            sourceTextField.setText(selectedFolder.getAbsolutePath());
        }
    }

    @FXML
    private void browseDestinationFolder() {
        // Open the folder selection dialog for Folder 2
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Destination Folder");

        // Get the current stage from any JavaFX node (e.g., the destinationTextField)
        Stage stage = (Stage) destinationTextField.getScene().getWindow();

        // Show the folder selection dialog
        java.io.File selectedFolder = directoryChooser.showDialog(stage);

        // Update the destinationTextField with the selected folder path (if a folder was selected)
        if (selectedFolder != null) {
            destinationTextField.setText(selectedFolder.getAbsolutePath());
        }
    }

    @FXML
    private void guide() {
        // Create a new stage for the path guide popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Path Format Guide");

        // Create the content of the popup window (VBox with labels)
        VBox popupContent = new VBox();
        popupContent.setSpacing(10);
        popupContent.setPadding(new Insets(20));

        Label label1 = new Label("%yyyy - year 4 digit");
        Label label2 = new Label("%yy - year 2 digit");
        Label label3 = new Label("%M - month text (e.g., January)");
        Label label4 = new Label("%MA - month text abbreviated (e.g., Jan)");
        Label label5 = new Label("%mm - month 2 digit");
        Label label6 = new Label("%D - day text (e.g., Monday)");
        Label label7 = new Label("%DA - day text abbreviated (e.g., Mon)");
        Label label8 = new Label("%dd - day 2 digit");
        Label label9 = new Label("%o - Original Filename");

        popupContent.getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, label8, label9);

        // Create a ScrollPane and set its content to the VBox
        ScrollPane scrollPane = new ScrollPane(popupContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene popupScene = new Scene(scrollPane, 300, 250);

        // Set the scene for the popup stage
        popupStage.setScene(popupScene);

        // Show the popup window and block interactions with other windows until it's
        // closed
        popupStage.showAndWait();
    }

    @FXML
    private void initialize() {
        // Add a listener to the pathpattern TextField to update the preview Label
        // whenever the text changes
        pathpattern.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldText, String newText) {
                updatePathPatternPreview(newText, false);
            }
        });
    
        // Add a listener to the renamepattern TextField to update the preview Label
        // whenever the text changes
        renamepattern.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldText, String newText) {
                updatePathPatternPreview(newText, true);
            }
        });
    }
    


    private void updatePathPatternPreview(String pathPattern, Boolean isRename) {
        // Replace any placeholders in the path pattern (e.g., %yyyy, %mm, etc.) with a
        // sample value
        String previewText = pathPattern.replace("%yyyy", "2023")
                .replace("%yy", "23")
                .replace("%MA", "Jul")
                .replace("%M", "July")
                .replace("%mm", "07")
                .replace("%DA", "Wed")
                .replace("%D", "Wednesday")
                .replace("%dd", "21")
                .replace("%o", "example.jpg");

       
                if (isRename == true){
                    renamePatternPreview.setText("Rename Preview: " + previewText);
                } else {
                    pathPatternPreview.setText("Path Preview: " + previewText);
                }  
    }

    @FXML
    private void start() {
        String sourceFolderPath = sourceTextField.getText();
        String destinationFolderPath = destinationTextField.getText();
        String pathPattern = pathpattern.getText();
        String renamePattern = renamepattern.getText();
    
        Sorting.sort(sourceFolderPath, destinationFolderPath, pathPattern, renamePattern);
    }
    
}
