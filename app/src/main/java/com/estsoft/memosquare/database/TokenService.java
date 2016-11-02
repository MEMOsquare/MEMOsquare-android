package com.estsoft.memosquare.database;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by sun on 2016-10-31.
 */

public interface TokenService {
    @POST("sign_in/")
    Call<ResponseBody> getresult();
}
