package com.paocu.xviss.network.requests;

import com.paocu.xviss.model.GeneritToUserRolWeatherOrCategory;
import com.paocu.xviss.model.util.Country;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CreateTravelServicce {

    @POST("api/create/rol")
    Call<String> postSelectTravelerRol(@Body List<GeneritToUserRolWeatherOrCategory> generitToUserRolWeatherOrCategoryList, @Query ("id") String id);

    @PUT("api/create/destiny")
    Call<Void> putSelectDestiny(@Body Country country, @Query("id") String travleId);

    @PUT("api/create/titlehour")
    Call<Void> putTitleAndHour(@Query("title") String title, @Query("date") String date, @Query("id") String id);

}
