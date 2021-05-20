package com.paocu.xviss.activities.ui.register.model;

public class AutenticationResponse {

    private String response;

    public AutenticationResponse() {

    }

    public AutenticationResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return response;
    }
}