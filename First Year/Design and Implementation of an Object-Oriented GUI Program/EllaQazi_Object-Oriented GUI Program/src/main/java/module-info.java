module com.example.assignment3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.assignment3 to javafx.fxml;
    exports com.example.assignment3;
}