package com.example.practica.Controller;

import jdk.jfr.StackTrace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.postgresql.util.PSQLException;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionControllerTest {

    @Test
    void searchExistRecordInDB() {
        String query = "SELECT * FROM medical_services WHERE service_name = ?";
        String name = "Физиотерапия";

        Boolean result = DBConnectionController.searchInDB(query, name);
        assertTrue(result);
    }
    @Test
    void searchNotExistRecordInDB() {
        String query = "SELECT * FROM medical_services WHERE service_name = ?";
        String name = "Солнечные ванные";

        Boolean result = DBConnectionController.searchInDB(query, name);
        assertFalse(result);
    }

    @Test
    void insertTwoTimesTheSameIntoDB() {
        String query = "INSERT INTO clients (first_name, last_name, date_of_birth, gender," +
                " address, stay_duration, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String first_name = "Тимур";
        String last_name = "Ахаян";
        LocalDate dateOfBirth = LocalDate.of(2005, Month.MAY, 15);
        String gender = "Мужской";
        String address = "Воронеж";
        LocalDate stayDuration = LocalDate.of(2024, Month.JULY, 3);
        String phoneNumber = "9050221321";

        assertTrue(DBConnectionController.insertIntoDB(query, first_name, last_name, dateOfBirth,
                gender, address, stayDuration, phoneNumber));
        assertFalse(DBConnectionController.insertIntoDB(query, first_name, last_name, dateOfBirth,
                gender, address, stayDuration, phoneNumber));
    }

    @Test
    void insertWithoutArgumentIntoDB() {
        String query = "INSERT INTO clients (first_name, date_of_birth, gender," +
                " address, stay_duration, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
        String first_name = "Йохан";
        LocalDate dateOfBirth = LocalDate.now();
        String gender = "Мужской";
        String address = "Воронеж";
        LocalDate stayDuration = LocalDate.now();
        String phoneNumber = "39912313";

        assertThrows(PSQLException.class, () -> DBConnectionController.insertIntoDB(query, first_name,
                dateOfBirth, gender, address, stayDuration, phoneNumber));
    }

    @Test
    void insertDeleteCommandIntoDB(){
        String query = "DELETE FROM clients WHERE first_name = ?";
        String name = "Тимур";

        Boolean result = DBConnectionController.insertIntoDB(query, name);
        assertFalse(result);
    }
}