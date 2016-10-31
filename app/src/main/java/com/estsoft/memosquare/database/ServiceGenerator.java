package com.estsoft.memosquare.database;

import com.estsoft.memosquare.R;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sun on 2016-10-25.
 */

public class ServiceGenerator {

    public static final String API_BASE_URL = "http://192.168.22.71/api/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    //httpClient를 REST API로 생성해준다. (하나의 service이자 하나의 client가 된다)
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}