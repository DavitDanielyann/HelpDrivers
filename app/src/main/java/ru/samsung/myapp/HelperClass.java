package ru.samsung.myapp;

public class HelperClass {
    String name, email, username, password;

    public HelperClass() {
        // Default constructor required for Firebase
    }

    public HelperClass(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
