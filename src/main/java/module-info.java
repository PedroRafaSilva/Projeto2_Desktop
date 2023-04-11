module com.example.projeto2_desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires Projeto2;
    requires java.sql;


    opens com.example.projeto2_desktop to javafx.fxml;
    exports com.example.projeto2_desktop;
}