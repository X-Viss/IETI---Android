package com.paocu.xviss.activities.ui.login.model;

public class LoginModel {

    private String userName;
    private String password;

    public LoginModel(String email, String password){
        this.userName=email;
        this.password= password;
    }

    public String getEmail() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}