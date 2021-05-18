package com.paocu.xviss.activities.ui.login.model;

public class Token {
    String accessToken;

    public Token( String accessToken ){
        this.accessToken = accessToken;
    }

    public String getAccessToken(){
        return accessToken;
    }

    public void setAccessToken( String accessToken ){
        this.accessToken = accessToken;
    }
}

