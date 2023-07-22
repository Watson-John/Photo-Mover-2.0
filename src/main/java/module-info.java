module com.photomover {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.photomover to javafx.fxml;
    exports com.photomover;
}
