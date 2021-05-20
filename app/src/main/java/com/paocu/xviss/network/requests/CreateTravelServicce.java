package com.paocu.xviss.network.requests;

import com.paocu.xviss.model.GeneritToUserRolWeatherOrCategory;
import com.paocu.xviss.model.util.Country;
import com.paocu.xviss.model.util.ListCategories;

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

    @PUT("api/create/weather")
    Call<ListCategories> putWeatherByUserRolSelected(@Body List<GeneritToUserRolWeatherOrCategory> generitToUserRolWeatherOrCategoryList, @Query ("id") String id);

    @PUT("api/create/category/accessories")
    Call<Void> putAccessoriesByUserRolSelected(@Body List<GeneritToUserRolWeatherOrCategory> accessories, @Query ("id") String id);

    @PUT("api/create/category/onhand")
    Call<Void> putOnHandByUserRolSelected(@Body List<GeneritToUserRolWeatherOrCategory> onhand, @Query ("id") String id);

    @PUT("api/create/category/cleanliness")
    Call<Void> putCleanlinessByUserRolSelected(@Body List<GeneritToUserRolWeatherOrCategory> cleanliness, @Query ("id") String id);

    @PUT("api/create/category/medicine")
    Call<Void> putMedicineByUserRolSelected(@Body List<GeneritToUserRolWeatherOrCategory> medicine, @Query ("id") String id);

    @PUT("api/create/category/clothes")
    Call<Void> putClothesByUserRolSelected(@Body List<GeneritToUserRolWeatherOrCategory> clothes, @Query ("id") String id);
}
