package com.example.practica.DTO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
//Описывает услугу из базы данных, которая может оказываться пациенту
public class Service {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;

    public Service(int id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    //Конструктор для generic метода из DBConnectionController
    //На основе результата, полученного из базы данных запросом создает объект класса услуги.
    public Service(ResultSet set) throws SQLException {
        id = set.getInt("service_id");
        name = set.getString("service_name");
        description = set.getString("description");
        price = set.getBigDecimal("price");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

