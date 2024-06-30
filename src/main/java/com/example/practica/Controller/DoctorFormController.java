package com.example.practica.Controller;

import com.example.practica.Service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

//Форма доктора, открывающаяся после входа в систему
public class DoctorFormController {
    //Визуальные элементы, описанные в fxml файле формы.
    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField genderField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private DatePicker stayDurationPicker;

    @FXML
    private Button addPatientButton;

    @FXML
    private Button viewServicesButton;

    @FXML
    private Button assignServiceButton;

    @FXML
    private Button viewMedicamentsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private void initialize() {

        welcomeLabel.setText("Здравствуйте, " + AuthenticationService.getCurrentUser().getUsername());
        //Подписка на все события для кнопок, имеющихся на форме.
        addPatientButton.setOnAction(event -> tryInsertClient());
        viewServicesButton.setOnAction(event -> openServicesView());
        assignServiceButton.setOnAction(event -> openAssignServiceView());
        viewMedicamentsButton.setOnAction(event -> openMedicineView());
        logoutButton.setOnAction(event -> logout());
    }

    //Метод, который пытается добавить клиента и в случае успеха сообщает об этом в консоль.
    private void tryInsertClient() {
        if (insertClient()) {
            System.out.println("Клиент добавлен");
        }
    }

    //Метод, который добавляет запись в базу данных от возвращает результат работы(Добавлен/Не добавлен)
    private boolean insertClient() {
        //Проверка верно ли заполнены поля, в случае false вызывает сообщение об ошибке в заполнении полей.
        if (!areFieldsValid()) {
            showAlert("Ошибка", "Все поля должны быть заполнены", Alert.AlertType.ERROR);
            return false;
        }
        //Вся информация из полей помещается в переменные.
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String gender = genderField.getText();
        LocalDate dob = dobPicker.getValue();
        String address = addressField.getText();
        String phone = phoneField.getText();
        LocalDate stayDuration = stayDurationPicker.getValue();

        //Составляется строка для добавления записи о новом клиенте в базу данных.
        String insertSQL = "INSERT INTO clients (first_name, last_name, date_of_birth, gender," +
                " address, stay_duration, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
        //Класс DBConnectionController принимает запрос и пытается выполнить INSERT, после чего возвращает результат операции
        return DBConnectionController.insertIntoDB(insertSQL, firstName, lastName, dob, gender, address, stayDuration, phone);
    }

    //Метод проверяет не пустые ли поля, которые необходимы для создания записи.
    private boolean areFieldsValid() {
        return !firstNameField.getText().isEmpty()
                && !lastNameField.getText().isEmpty()
                && !genderField.getText().isEmpty()
                && dobPicker.getValue() != null
                && !addressField.getText().isEmpty()
                && !phoneField.getText().isEmpty()
                && stayDurationPicker.getValue() != null;
    }

    //Показывает сообщение об ошибке.
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //Открытие формы для просмотра всех услуг, имеющихся в базе данных.
    private void openServicesView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/practica/services_view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Медицинские услуги");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Открытие формы для назначения услуги пациенту.
    private void openAssignServiceView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/practica/assign_service_view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Назначить услугу");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Открытие формы для просмотра всех хранимых лекарств.
    private void openMedicineView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/practica/medicine_view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Медицинские препараты");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Выход из учетной записи доктора и возвращение к окну логина.
    private void logout() {
        AuthenticationService.logout();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/practica/login_form.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Логин");
            stage.setScene(new Scene(root));
            stage.show();

            // Закрытие текущего окна
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

