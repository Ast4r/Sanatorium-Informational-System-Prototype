package com.example.practica.Service;

import com.example.practica.DTO.User;

//Сервис аутентификации, нужен для идентификации того, какой сейчас пользователь использует систему,
//При выходе из формы доктора информация о текущем пользователе сбрасывается
public class AuthenticationService {

    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
}
