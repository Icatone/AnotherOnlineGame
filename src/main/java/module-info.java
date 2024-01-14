module com.example.anotheronlinegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.anotheronlinegame to javafx.fxml;
    exports com.example.anotheronlinegame;
}