package com.paocu.xviss.network.requests;

import com.paocu.xviss.model.GeneritToUserRolWeatherOrCategory;
import com.paocu.xviss.model.util.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CreateTravelServicce {

    @POST("api/create/rol")
    Call<String> postSelectTravelerRol( @Body List<GeneritToUserRolWeatherOrCategory> rolList, @Query ("id") String travelI);

    @PUT("api/create/destiny")
    Call<Void> putSelectDestiny(@Body Country couuntry, @Query("id") String travleId);
}
