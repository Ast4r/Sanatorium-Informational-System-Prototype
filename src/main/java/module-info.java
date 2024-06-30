module com.example.practica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.practica to javafx.fxml;
    exports com.example.practica;
    exports com.example.practica.DTO;
    opens com.example.practica.DTO to javafx.fxml;
    exports com.example.practica.Controller;
    opens com.example.practica.Controller to javafx.fxml;
    exports com.example.practica.View;
    opens com.example.practica.View to javafx.fxml;
    exports com.example.practica.Service;
    opens com.example.practica.Service to javafx.fxml;
    exports com.example.practica.DAO;
    opens com.example.practica.DAO to javafx.fxml;
}