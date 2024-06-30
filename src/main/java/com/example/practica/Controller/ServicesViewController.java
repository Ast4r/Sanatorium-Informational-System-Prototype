package com.example.practica.Controller;

import com.example.practica.DTO.Service;
import com.example.practica.Mapper.ResultSetMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

//Класс, который управляет просмотром всех имеющихся услуг в БД санатория.
public class ServicesViewController {
    //Визуальные элементы формы.
    @FXML
    private TableView<Service> servicesTable;

    @FXML
    private TableColumn<Service, Integer> idColumn;

    @FXML
    private TableColumn<Service, String> nameColumn;

    @FXML
    private TableColumn<Service, String> descriptionColumn;

    @FXML
    private TableColumn<Service, BigDecimal> priceColumn;
    //Список услуг, найденных в базе данных.
    private ObservableList<Service> serviceList;

    @FXML
    private void initialize() {
        serviceList = FXCollections.observableArrayList();

        //Создание полей для всех столбцов в таблице.
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Загружает все услуги из БД в таблицу.
        loadServicesFromDatabase();
    }

    private void loadServicesFromDatabase() {
        String query = "SELECT * FROM medical_services";
        //Делает запрос на все записи услуг в БД. Результат возвращается списком со всеми найденными услугами.
        serviceList = DBConnectionController.executeQuery(query, resultSet -> new Service(resultSet));
        //Добавляет записи услуг из списка в таблицу.
        servicesTable.setItems(serviceList);
        servicesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}

