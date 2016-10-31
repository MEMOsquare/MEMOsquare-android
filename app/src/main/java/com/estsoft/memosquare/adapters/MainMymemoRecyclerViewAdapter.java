package com.estsoft.memosquare.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estsoft.memosquare.R;
import com.estsoft.memosquare.models.MemoModel;
import com.estsoft.memosquare.models.ModelList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016-10-26.
 */

public class MainMymemoRecyclerViewAdapter extends RecyclerView.Adapter<MainMymemoRecyclerViewAdapter.MemoViewHolder> {

    private List<MemoModel> memoList;

    //data를 생성자 인수로 받는다
    public MainMymemoRecyclerViewAdapter(List<MemoModel> memoList) {
        this.memoList = memoList;
    }

    //connet cardview and viewholder
    @Override
    public MainMymemoRecyclerViewAdapter.MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_main_mymemo, parent, false);

        return new MainMymemoRecyclerViewAdapter.MemoViewHolder(cardView);
    }

    //connect model(data) and viewholder
    @Override
    public void onBindViewHolder(MainMymemoRecyclerViewAdapter.MemoViewHolder memoViewHolder, int i) {
        MemoModel memo = memoList.get(i);
        memoViewHolder.title.setText(memo.getTitle());
        memoViewHolder.content.setText(memo.getContent());
        memoViewHolder.timestamp.setText(memo.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public static class MemoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title) TextView title;
        @BindView(R.id.tv_content) TextView content;
        @BindView(R.id.tv_timestamp) TextView timestamp;

        public MemoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
