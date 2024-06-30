package com.example.practica.DAO;

//Класс, который описывает имя, используемое для идентификации пользователя или пациента в системе.
public class FullName {
    private String fullName;
    private String firstName;
    private String lastName;

    public FullName(String fullName){
        this.fullName = fullName;
        splitFullName();
    }
    public FullName(String firstName, String lastName){
        this.fullName = firstName + " " + lastName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //Разделение полного имени на Имя и Фамилию
    private void splitFullName(){
        firstName = fullName.split(" ")[0];
        lastName = fullName.split(" ")[1];
    }

    //Проверка состоит ли полное имя из двух слов.
    public boolean isFullNameCorrect(){
        if(fullName.contains(" "))
            return true;
        else
            return false;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
