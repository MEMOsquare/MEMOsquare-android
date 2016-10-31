package com.estsoft.memosquare.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estsoft.memosquare.R;
import com.estsoft.memosquare.adapters.MainMymemoRecyclerViewAdapter;
import com.estsoft.memosquare.database.MemoService;
import com.estsoft.memosquare.database.ServiceGenerator;

import com.estsoft.memosquare.models.MemoModel;
import com.estsoft.memosquare.models.ModelList;


import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by sun on 2016-10-18.
 */

public class MainTabMymemoFragment extends android.support.v4.app.Fragment {

    private ModelList<MemoModel> memoList;
    private List<MemoModel> list = Collections.emptyList();;

    @BindView(R.id.recyclerview) RecyclerView recView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //1. inflate the layout for this fragment.
        View rootView = inflater.inflate(R.layout.fragment_main_tab_mymemo, container, false);
        ButterKnife.bind(this, rootView);

        //2. find recyclerview (Butterknife does)
        recView.setHasFixedSize(true);

        //3. DB connection(Async) - get Memo List
        MemoService service = ServiceGenerator.createService(MemoService.class);
        Call<ModelList<MemoModel>> call = service.getmymemos();
        call.enqueue(new Callback<ModelList<MemoModel>>(){
            @Override
            public void onResponse(Call<ModelList<MemoModel>> call, Response<ModelList<MemoModel>> response) {
                if (response.isSuccessful()) {
                    memoList = response.body();
                    list = memoList.getModellist();
                    for (MemoModel memo : list){
                        Timber.d(memo.toString()+"\n");
                    }
                } else {
                    //fragment: TODO: MainActivity-mymemofragment no data일 때 처리
                }
            }

            @Override
            public void onFailure(Call<ModelList<MemoModel>> call, Throwable t) {
                Log.d("Error", t.getMessage());
                list = Collections.emptyList();
            }
        });


        //4. set recyclerview adapter
        MainMymemoRecyclerViewAdapter  adapter = new MainMymemoRecyclerViewAdapter(list);
        recView.setAdapter(adapter);


        //5. create LinearLayoutManager - it determines the time to recycle the views.
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recView.setLayoutManager(llm);

        return rootView;
    }
}
