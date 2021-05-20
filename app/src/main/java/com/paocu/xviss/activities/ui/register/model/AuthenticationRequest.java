package com.paocu.xviss.activities.ui.register.model;
import androidx.room.TypeConverters;

import com.paocu.xviss.activities.ui.register.DateConverter;

import java.util.Date;

@TypeConverters(DateConverter.class)
public class AuthenticationRequest {
    private String userName;
    private String password;
    private Date birth;
    private String country;
    private Integer phone;

    public AuthenticationRequest(){
        // Constructor .
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }


}