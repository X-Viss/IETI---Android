package com.paocu.xviss.network.requests;

import com.paocu.xviss.model.Travel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TravelService {


    @GET("api/travels")
    public Call<List<Travel>> getTravels(@Query("user") String username);

    @DELETE("api/travels")
    public Call<Void> deleteTravel(@Query("id") String travelId);
}
