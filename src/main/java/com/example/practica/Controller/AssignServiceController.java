package com.example.practica.Controller;

import com.example.practica.DAO.FullName;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

//Этот класс отвечает за назначение услуг пациенту.

public class AssignServiceController {
    //Визуальные элементы формы
    @FXML
    private TextField patientNameField;

    @FXML
    private TextField serviceNameField;

    @FXML
    private DatePicker serviceDatePicker;

    @FXML
    private Button assignServiceButton;

    @FXML
    private void initialize() {
        //Подписка на событие по добавлению услуги, в случае если введенные данные верны сообщает пользователю.
        assignServiceButton.setOnAction(event -> {
            try {
                if (assignServiceToPatient())
                    System.out.println("Услуга добавлена");
                else
                    showAlert("Ошибка", "Не все поля заполнены", Alert.AlertType.ERROR);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    //Данный метод берет информацию из полей формы, проверяет ее и пытается добавить указанную услугу пациенту.
    private boolean assignServiceToPatient() throws Exception {
        String patientName = patientNameField.getText();
        FullName patientFullName = new FullName(patientName);

        String serviceName = serviceNameField.getText();
        LocalDate serviceDate = serviceDatePicker.getValue();

        //Проверка не пустые ли поля.
        if (patientName.isEmpty() || !patientFullName.isFullNameCorrect() || serviceName.isEmpty() || serviceDate == null) {

            return false;
        }
        //Конвертация в используемой в базе данных тип.
        LocalDate sqlServiceDate = Date.valueOf(serviceDate).toLocalDate();

        //Ищет ID пациента по его имени.
        String findClientByNameSQL = "SELECT * FROM clients WHERE first_name = ? AND last_name = ?";
        Integer client_id = DBConnectionController.executeQuery(findClientByNameSQL,
                resultSet -> resultSet.getInt("client_id"), patientFullName.getFirstName(), patientFullName.getLastName()).get(0);

        //Ищет ID услуги по ее названию.
        String findServiceByNameSQL = "SELECT * FROM medical_services WHERE service_name = ?";
        Integer service_id = DBConnectionController.executeQuery(findServiceByNameSQL, resultSet -> resultSet.getInt("service_id"), serviceName).get(0);

        // Вызов метода для вставки данных в базу данных.
        String assignSQL = "INSERT INTO client_services (client_id, service_id, date_of_service) VALUES (?, ?, ?)";
        return DBConnectionController.insertIntoDB(assignSQL, client_id, service_id, sqlServiceDate);
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
