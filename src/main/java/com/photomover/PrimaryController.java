package com.photomover;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class PrimaryController {

    @FXML
    private TextField sourceTextField;

    @FXML
    private TextField destinationTextField;

    @FXML
    private TextField optionsTextField;

    @FXML
    private Button optionsBrowseButton;

    @FXML
    private CheckBox moveFilesCheckbox;

    @FXML
    private TextField pathpattern; // Added for the path pattern TextField

    @FXML
    private TextField renamepattern; // Added for the rename pattern TextField

    @FXML
    private Label pathPatternPreview;

    @FXML
    private Label renamePatternPreview;

    @FXML
    private RadioButton sameButton; // Added for the radio button

    @FXML
    private RadioButton everythingButton; // Added for the radio button

    @FXML
    private void browseSourceFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Source Folder");
        Stage stage = (Stage) sourceTextField.getScene().getWindow();
        java.io.File selectedFolder = directoryChooser.showDialog(stage);
        if (selectedFolder != null) {
            sourceTextField.setText(selectedFolder.getAbsolutePath());
        }
    }

    @FXML
    private void browseDestinationFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Destination Folder");
        Stage stage = (Stage) destinationTextField.getScene().getWindow();
        java.io.File selectedFolder = directoryChooser.showDialog(stage);
        if (selectedFolder != null) {
            destinationTextField.setText(selectedFolder.getAbsolutePath());
        }
    }

    @FXML
    private void browseOptionsFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Destination Folder");
        Stage stage = (Stage) optionsTextField.getScene().getWindow();
        java.io.File selectedFolder = directoryChooser.showDialog(stage);
        if (selectedFolder != null) {
            optionsTextField.setText(selectedFolder.getAbsolutePath());
        }
    }

@SuppressWarnings("unchecked")
@FXML
    private void guide() {
        // Create a new stage for the path guide popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Path Format Guide");

        // Create a TableView to display the guide in a structured format
        TableView<GuideEntry> tableView = new TableView<>();

        // Define columns for the TableView
        TableColumn<GuideEntry, String> placeholderColumn = new TableColumn<>("Placeholder");
        placeholderColumn.setCellValueFactory(new PropertyValueFactory<>("placeholder"));
        placeholderColumn.setMinWidth(150);

        TableColumn<GuideEntry, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setMinWidth(250);

        tableView.getColumns().addAll(placeholderColumn, descriptionColumn);

        // Populate the TableView with data
        tableView.getItems().add(new GuideEntry("%yyyy", "Year (4 digits)"));
        tableView.getItems().add(new GuideEntry("%yy", "Year (2 digits)"));
        tableView.getItems().add(new GuideEntry("%M", "Month (Full name, e.g., January)"));
        tableView.getItems().add(new GuideEntry("%MA", "Month (Abbreviated, e.g., Jan)"));
        tableView.getItems().add(new GuideEntry("%mm", "Month (2 digits)"));
        tableView.getItems().add(new GuideEntry("%D", "Day (Full name, e.g., Monday)"));
        tableView.getItems().add(new GuideEntry("%DA", "Day (Abbreviated, e.g., Mon)"));
        tableView.getItems().add(new GuideEntry("%dd", "Day (2 digits)"));
        tableView.getItems().add(new GuideEntry("%o", "Original Filename"));

        // Create a VBox to hold the TableView and any additional content
        VBox vBox = new VBox(new Label("Path Format Guide"), tableView);
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);

        // Create a ScrollPane and set its content to the VBox
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene popupScene = new Scene(scrollPane, 500, 400);

        // Set the scene for the popup stage
        popupStage.setScene(popupScene);

        // Show the popup window and block interactions with other windows until it's closed
        popupStage.showAndWait();
    }

    // Inner class to represent a guide entry
    public static class GuideEntry {
        private final String placeholder;
        private final String description;

        public GuideEntry(String placeholder, String description) {
            this.placeholder = placeholder;
            this.description = description;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public String getDescription() {
            return description;
        }
    }

    @FXML
    private void initialize() {

        pathpattern.textProperty().addListener((observableValue, oldText, newText) -> updatePathPatternPreview(newText, false));

        renamepattern.textProperty().addListener((observableValue, oldText, newText) -> updatePathPatternPreview(newText, true));

        optionsTextField.setDisable(!moveFilesCheckbox.isSelected());
        optionsBrowseButton.setDisable(!moveFilesCheckbox.isSelected());

        moveFilesCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            optionsTextField.setDisable(!newValue);
            optionsBrowseButton.setDisable(!newValue);
        });

        sameButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                everythingButton.setSelected(false);
            }
        });

        everythingButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                sameButton.setSelected(false);
            }
        });

    }

    private void updatePathPatternPreview(String pathPattern, Boolean isRename) {
        String previewText = pathPattern.replace("%yyyy", "2023").replace("%yy", "23").replace("%MA", "Jul")
                .replace("%M", "July").replace("%mm", "07").replace("%DA", "Wed").replace("%D", "Wednesday")
                .replace("%dd", "21").replace("%o", "example.jpg");

        if (isRename) {
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
        String optionsFolderPath = optionsTextField.getText();

        Sorting.sort(sourceFolderPath, destinationFolderPath, optionsFolderPath, pathPattern, renamePattern);
    }
}