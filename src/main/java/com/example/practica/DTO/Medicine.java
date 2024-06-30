package com.example.practica.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

//Описывает лекарства из базы данных, которые могут выдать пациенту.
public class Medicine {
    private int id;
    private String name;
    private String description;
    private String releaseForm;
    private String dosage;
    private LocalDate expirationDate;
    private String country;
    private int quantity;

    public Medicine(int id, String name, String description, String releaseForm, String dosage, LocalDate expirationDate, String country, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseForm = releaseForm;
        this.dosage = dosage;
        this.expirationDate = expirationDate;
        this.country = country;
        this.quantity = quantity;
    }

    //Конструктор для generic метода из DBConnectionController
    //На основе результата, полученного из базы данных запросом создает объект класса лекарство.
    public Medicine(ResultSet set) throws SQLException {
        id = set.getInt("medicament_id");
        name = set.getString("name");
        description = set.getString("description");
        releaseForm = set.getString("release_form");
        dosage = set.getString("dosage");
        expirationDate = set.getDate("expiration_date").toLocalDate();
        country = set.getString("country");
        quantity = set.getInt("quantity");
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

    public String getReleaseForm() {
        return releaseForm;
    }

    public String getDosage() {
        return dosage;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public String getCountry() {
        return country;
    }

    public int getQuantity() {
        return quantity;
    }
}

