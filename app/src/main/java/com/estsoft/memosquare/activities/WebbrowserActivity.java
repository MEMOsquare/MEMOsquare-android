package com.estsoft.memosquare.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.estsoft.memosquare.R;

import timber.log.Timber;

// Webbrowser 상태에서 항상 떠있을 액티비티
// 한 액티비티 안에서 changeMode를 통해 메모보기, 쓰기 등 여러 형태로 바꾼다
public class WebbrowserActivity extends AppCompatActivity {

    // 모드의 종류
    public static final int WEBBROWSING = 1000;
    public static final int MEMOWRITE = 1001;
    public static final int MEMOVIEW = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webbrowser);
    }

    // 모드에 따라 액티비티의 형태를 바꾼다
    public void changeMode(int mode) {
        switch (mode) {
            case WEBBROWSING:
                // 1. 툴바를 visible로
                // 2. web_memo_container를 gone으로
                // 3. web_fab를 visible로
                // viewpager는 처음 생성시에 만들고 없애지 않으므로 따로 설정하지 않는다
                break;
            case MEMOWRITE:
                // 1. 툴바를 gone으로
                // 2. web_memo_container를 visible로
                // 3. web_memo_container에 WebMomoWriteFragment를 담는다
                // 4. web_fab를 gone으로
                break;
            case MEMOVIEW:
                // 1. 툴바를 gone으로
                // 2. web_memo_container를 visible로
                // 3. web_memo_container에 WebMomoViewFragment를 담는다
                // 4. web_fab를 gone으로
                break;
            default:
                Timber.d("wrong mode number " + mode);
        }
    }
}
