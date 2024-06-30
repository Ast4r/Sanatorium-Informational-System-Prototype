package com.example.practica.DTO;

//Представляет любого пользователя системы(это может быть не только врач)
public class User {
    private final String username;
    private final String password;
    //В зависимости от роли пользователя ему могут быть доступны разные окна и функции(Администратор, врач и т.д.)
    private final String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

}
