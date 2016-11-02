package com.estsoft.memosquare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.estsoft.memosquare.R;
import com.estsoft.memosquare.activities.WebBrowserActivity;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by hokyung on 2016. 11. 1..
 * 메모 쓰기 버튼 누를시 활성화되는 Fragment
 * 깔끔한 코드를 위해 OnClickListener를 implements하여 구현하였음
 */

public class WebBrowserMemoWriteFragment extends Fragment implements View.OnClickListener {

    // 위젯 할당
    @BindView(R.id.highlight_btn) Button mHighLightButton;
    @BindView(R.id.memo_title_input) EditText mTitleInput;
    @BindView(R.id.memo_content_input) EditText mContentInput;
    @BindView(R.id.secret_switch) Switch mSecretSwitch;
    @BindView(R.id.memo_save_btn) Button mSaveButton;

    // 리소스 할당
    @BindString(R.string.wait) String mWait;

    // 부모 액티비티
    private WebBrowserActivity mActivity;

    // 공개여부를 저장할 변수
    private boolean mIsSecret;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_web_memo_write, container, false);
        ButterKnife.bind(this, view);

        // 부모 액티비티 할당
        mActivity = (WebBrowserActivity) getActivity();

        // 공개 상태로 시작
        mIsSecret = false;

        // switch에 리스너 할당
        mSecretSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 비공개 상태로 변한 경우
                if (isChecked) {
                    mIsSecret = true;
                }
                // 공개 상태로 변한 경우
                else {
                    mIsSecret = false;
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.highlight_btn:
                // activity를 이용해서 하이라이트 활성화 후 데이터 받아오기까지
                // 하이라이트가 가능하다면
                if (mActivity.isToggleAvailable()) {
                    mActivity.toggleHighLight();
                }
                // 불가능하다면
                else {
                    Toast.makeText(mActivity, mWait, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.memo_save_btn:
                // 메모의 유효성 검사 후 저장
                // 공개여부도 같이 저장
                Toast.makeText(mActivity, "title: " + mTitleInput.getText().toString()
                        + "\ncontent: " + mContentInput.getText().toString(), Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                Timber.w("wrong view clicked");
        }
    }

    // 하이라이트 된 메모를 저장하는 메소드(WebBrowserActivity를 통해 WebViewFragment에서 사용)
    public void addMemo(String content) {
        // 기존 내용을 html로 받는다
        String memo = Html.toHtml(mContentInput.getText());

        // 메모가 빈칸이거나, 엔터가 쳐있던 경우
//        if (!memo.equals("") && !memo.substring(memo.length() - 1).equals("\n")) {
//            memo += "\n";
//        }

        // 이탤릭체로 변환 후 저장
        memo += "<i>\"" + content + "\"</i>";
        mContentInput.setText(Html.fromHtml(memo));
    }
}
