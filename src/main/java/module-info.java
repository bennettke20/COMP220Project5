module com.files.comp220project5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.files.comp220project5 to javafx.fxml;
    exports com.files.comp220project5;
}