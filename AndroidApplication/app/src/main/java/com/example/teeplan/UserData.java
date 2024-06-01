package com.example.teeplan;

public class UserData {
    public String first_name;
    public String last_name;
    public String email;
    public String password;

    public boolean is_admin = false;

    public UserData() {
    }
    public UserData(String first_name, String last_name, String email, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }
}
