package com.estsoft.memosquare.database;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by sun on 2016-10-31.
 */

public interface TokenService {
    @POST("sign_in/")
    Call<String> getresult();
}
