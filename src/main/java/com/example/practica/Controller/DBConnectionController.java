package com.example.practica.Controller;

import com.example.practica.Mapper.ResultSetMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

//Класс, который контролирует всю работу приложения с базой данных.
public class DBConnectionController {

    //Информация для подключения к базе данных
    private static final String url = "jdbc:postgresql://localhost:5432/Sanatorium";
    private static final String dbUsername = "postgres";
    private static final String dbPassword = "0000";

    private static Connection connection;

    //Метод смотрит существует ли уже подключение к базе данных и в случае отрицательного ответа
    //Создает его сам. Открытие многих подключений может сильно снизить производительность.
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            connection.createStatement();
        }
        return connection;
    }

    //Ищет по запросы информацию в базе данных и в случае если она найдена возвращает true.
    public static boolean searchInDB(String query, String... args) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (int i = 1; i <= args.length; i++) {
                preparedStatement.setString(i, args[i - 1]);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Выполняет запрос с получением ответа от сервера, после чего помещает его в список и возвращает.
    //Чтобы правильно создать экземпляры для списка используется ResultSetMapper, в котором задаются все
    //Необходимые поля для конструктора класса, из которого состоит список.
    public static <T> ObservableList<T> executeQuery(String query, ResultSetMapper<T> mapper, String... args) {
        ObservableList<T> results = FXCollections.observableArrayList();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (int i = 1; i <= args.length; i++) {
                preparedStatement.setString(i, args[i - 1]);
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                T obj = mapper.map(resultSet);
                results.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    //Пытается добавить запись в базу данных и возвращает результат операции(true/false).
    public static boolean insertIntoDB(String query, Object... args) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (int i = 1; i <= args.length; i++) {
                if (args[i - 1] instanceof String)
                    preparedStatement.setString(i, String.valueOf(args[i - 1]));
                if (args[i - 1] instanceof LocalDate)
                    preparedStatement.setDate(i, Date.valueOf((LocalDate) args[i - 1]));
                if (args[i - 1] instanceof Integer) {
                    preparedStatement.setInt(i, (Integer) args[i - 1]);
                }
            }

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}