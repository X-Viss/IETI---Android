package com.paocu.xviss.network.requests;

import com.paocu.xviss.model.util.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BaseTravelService {
    @GET("bag/faq")
    public Call<List<Question>> getQuestions();
}
