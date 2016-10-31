package com.estsoft.memosquare.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.estsoft.memosquare.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016-10-26.
 */

public class RecyclerViewTestActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)RecyclerView recList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerviewtest);
        ButterKnife.bind(this);

        recList.setHasFixedSize(true);

        //LinearLayoutManager determines when it is time to recycle the views.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

    }
}
