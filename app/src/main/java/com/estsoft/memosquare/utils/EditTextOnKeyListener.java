package com.estsoft.memosquare.utils;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;

import com.estsoft.memosquare.activities.WebBrowserActivity;

import timber.log.Timber;

/**
 * Created by hokyung on 2016. 11. 1..
 * 주소창에서의 엔터키 입력을 받기 위한 클래스
 */

public class EditTextOnKeyListener implements View.OnKeyListener {

    // 액티비티 구분을 위한 코드
    public static final int WEBBROWSERACTIVITY = 100;

    // 액티비티의 메소드를 실행시키기 위한 변수
    private Context mContext;
    private int mActivity;

    public EditTextOnKeyListener(Context context, int activity) {
        mContext = context;
        mActivity = activity;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        // 엔터키가 떼어질 때
        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
            // 웹뷰 액티비티의 주소창일 경우
            if (mActivity == WEBBROWSERACTIVITY) {
                ((WebBrowserActivity) mContext).goUrl();
                ((WebBrowserActivity) mContext).clearEditTextFocus();
            }
        }

        // 취소버튼 눌렀을 때
        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
            // 웹뷰 액티비티의 주소창일 경우
            if (mActivity == WEBBROWSERACTIVITY) {
                ((WebBrowserActivity) mContext).clearEditTextFocus();
            }
        }

        return true;
    }
}
