package com.paocu.xviss.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author Santiago Carrillo
 * 4/23/19.
 */
public class RetrofitNetwork
{

    private Retrofit retrofit;

    private String BASE_URL = "http://192.168.0.14:8080";
    //private String BASE_URL = "http://192.168.0.3:8080";



    public RetrofitNetwork()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        retrofit = new Retrofit.Builder().baseUrl( BASE_URL ) //localhost for emulator
                .addConverterFactory( GsonConverterFactory.create(gson) ).build();
    }


    public RetrofitNetwork(final String token )
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor( new Interceptor()
        {
            @Override
            public okhttp3.Response intercept( Chain chain )
                    throws IOException
            {
                Request original = chain.request();

                Request request = original.newBuilder().header( "Accept", "application/json" ).header( "Authorization",
                        "Bearer "
                                + token ).method(
                        original.method(), original.body() ).build();
                return chain.proceed( request );
            }
        } );

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory(ScalarsConverterFactory.create() ).addConverterFactory(GsonConverterFactory.create(gson)).client(
                        httpClient.build() ).build();

    }

    public Object getRetrofitService(Class serviceClass){
        return retrofit.create(serviceClass);
    }

}