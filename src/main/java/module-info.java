
module com.photomover {

    requires javafx.controls;

    requires javafx.fxml;

    requires com.github.mjeanroy.exiftool;

    opens com.photomover to javafx.fxml;

    exports com.photomover;

    requires transitive javafx.graphics;

}
