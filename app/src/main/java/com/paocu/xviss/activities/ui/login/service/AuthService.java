package com.paocu.xviss.activities.ui.login.service;

import com.paocu.xviss.activities.ui.login.model.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/auth")
    Call<Token> login(@Body LoginModel loginModel);
}