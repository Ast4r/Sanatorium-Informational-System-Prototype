package com.example.practica.Controller;


import com.example.practica.DTO.Medicine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
//Этот класс отвечает за показ всех лекарств, имеющихся у санатория.
public class MedicineViewController {
    //Столбцы таблицы, в которой будут перечислены медикаменты.
    @FXML
    private TableView<Medicine> medicinesTable;

    @FXML
    private TableColumn<Medicine, Integer> idColumn;

    @FXML
    private TableColumn<Medicine, String> nameColumn;

    @FXML
    private TableColumn<Medicine, String> descriptionColumn;

    @FXML
    private TableColumn<Medicine, String> releaseFormColumn;

    @FXML
    private TableColumn<Medicine, String> dosageColumn;

    @FXML
    private TableColumn<Medicine, LocalDate> expirationDateColumn;

    @FXML
    private TableColumn<Medicine, String> countryColumn;

    @FXML
    private TableColumn<Medicine, Integer> quantityColumn;
    //Список медикаментов, найденных в базе данных.
    private ObservableList<Medicine> medicineList;

    @FXML
    private void initialize() {

        medicineList = FXCollections.observableArrayList();

        //Создание полей для всех столбцов в таблице.
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        releaseFormColumn.setCellValueFactory(new PropertyValueFactory<>("releaseForm"));
        dosageColumn.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        expirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        //Загружает все медикаменты из БД в таблицу.
        loadMedicineFromDatabase();
    }

    private void loadMedicineFromDatabase() {
        String query = "SELECT * FROM medicaments";
        //Делает запрос на все записи медикаментов в БД. Результат возвращается списком со всеми полученными лекарствами.
        medicineList = DBConnectionController.executeQuery(query, resultSet -> new Medicine(resultSet));
        //Добавляет записи из списка на таблицу.
        medicinesTable.setItems(medicineList);
        medicinesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}