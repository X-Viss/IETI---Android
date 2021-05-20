package com.paocu.xviss.network.requests;

import com.paocu.xviss.model.Travel;
import com.paocu.xviss.model.TravelOnLoad;
import com.paocu.xviss.model.util.Question;
import com.paocu.xviss.model.util.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BaseTravelService {
    @GET("bag/faq")
    public Call<List<Question>> getQuestions();

    @GET("bag/stores")
    public Call<List<Store>> getStores(@Query("category") String category);

    @GET("bag/travel")
    public Call<TravelOnLoad> getTravel(@Query("travelId") String travelId);
}
