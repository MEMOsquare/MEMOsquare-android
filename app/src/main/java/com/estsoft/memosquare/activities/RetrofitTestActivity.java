package com.estsoft.memosquare.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.estsoft.memosquare.R;
import com.estsoft.memosquare.database.MemoService;
import com.estsoft.memosquare.database.ServiceGenerator;
import com.estsoft.memosquare.models.MemoModel;
import com.estsoft.memosquare.models.ModelList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sun on 2016-10-25.
 */

public class RetrofitTestActivity extends AppCompatActivity implements View.OnClickListener{
    ModelList<MemoModel> memoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofittest);

        Button btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(this);

    }

        @Override
        public void onClick(View view) {
            Log.d("TAG", "Button Clicked");

            MemoService service = ServiceGenerator.createService(MemoService.class);
            Call<ModelList<MemoModel>> call = service.getmymemos();
            call.enqueue(new Callback<ModelList<MemoModel>>(){
                @Override
                public void onResponse(Call<ModelList<MemoModel>> call, Response<ModelList<MemoModel>> response) {
                    Log.d("RESPONSE", "onresponse");
                    if (response.isSuccessful()) {
                        Log.d("SUCCESS", "success");
                        memoList = response.body();
                        Log.d("RESPONSEBODY",response.body().toString());
                        for (MemoModel memo : memoList.getModellist()){
                            Log.d("SUCCESS", memo.toString());
                        }

                    } else {
                        // error response, no access to resource?
                    }
                }

                @Override
                public void onFailure(Call<ModelList<MemoModel>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
        }

}

