package com.paocu.xviss.activities.ui.register.service;

import com.paocu.xviss.activities.ui.register.model.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {

    @POST("/subs")
    Call<AutenticationResponse> subcribeClient(@Body AuthenticationRequest authenticationRequest);

}
