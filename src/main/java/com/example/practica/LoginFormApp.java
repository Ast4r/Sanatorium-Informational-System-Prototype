package com.example.practica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginFormApp extends Application {
    //Форма для логина, с которой начинается приложение. Все формы описаны отдельно fxml файлами в папке resources
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/practica/login_form.fxml"));
        AnchorPane root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Логин");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
