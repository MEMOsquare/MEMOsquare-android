package com.estsoft.memosquare.database;

import com.estsoft.memosquare.models.MemoModel;
import com.estsoft.memosquare.models.ModelList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sun on 2016-10-25.
 */

public interface MemoService {
    @GET("memo/all")
    Call<ModelList<MemoModel>> getmymemos();
}
