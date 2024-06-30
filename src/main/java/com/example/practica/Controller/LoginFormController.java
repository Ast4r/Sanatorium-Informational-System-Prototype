package com.example.practica.Controller;

import com.example.practica.DAO.FullName;
import com.example.practica.Service.AuthenticationService;
import com.example.practica.DTO.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    //Визуальные элементы формы
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    //Форма имеет единственную кнопку для попытки входа в систему.
    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> handleLogin());
    }

    //Проверяет введенные значения, если им соотвествует запись в базе данных, то разрешает вход в систему.
    private void handleLogin() {
        String username = usernameField.getText();
        FullName fullName = new FullName(username);

        String password = passwordField.getText();

        if (checkCredentials(fullName, password)) {
            messageLabel.setText("Успешный вход!");
            User user = new User(username, password, "Doctor");
            AuthenticationService.setCurrentUser(user);
            openDoctorForm();
        } else {
            messageLabel.setText("Неправильное имя или пароль");
        }
    }

    //Делает запрос, которые проверяет есть ли пользователь с указанным именем и паролем в БД.
    private boolean checkCredentials(FullName fullName, String password) {
        if(!fullName.isFullNameCorrect())
            return false;

        String query = "SELECT 1 FROM doctors WHERE first_name = ? AND last_name = ? AND password = ?";

        return DBConnectionController.searchInDB(query, fullName.getFirstName(), fullName.getLastName(), password);
    }

    //Открывает форму доктора, если данные пользователя верны.
    private void openDoctorForm() {
        try {
            // Загрузка FXML файла новой формы доктора.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/practica/doctor_form.fxml"));
            Parent root = loader.load();

            // Создание новой сцены.
            Stage stage = new Stage();
            stage.setTitle("Форма доктора");
            stage.setScene(new Scene(root));

            // Показать сцену.
            stage.show();

            // Закрыть текущее окно входа.
            loginButton.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
