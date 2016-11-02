package com.estsoft.memosquare.activities;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.estsoft.memosquare.R;
import com.estsoft.memosquare.adapters.WebBrowserPagerAdapter;
import com.estsoft.memosquare.fragments.WebBrowserMemoListFragment;
import com.estsoft.memosquare.fragments.WebBrowserMemoViewFragment;
import com.estsoft.memosquare.fragments.WebBrowserMemoWriteFragment;
import com.estsoft.memosquare.fragments.WebBrowserWebViewFragment;
import com.estsoft.memosquare.utils.EditTextOnKeyListener;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

// Webbrowser 상태에서 항상 떠있을 액티비티
// 한 액티비티 안에서 changeMode를 통해 메모보기, 쓰기 등 여러 형태로 바꾼다
public class WebBrowserActivity extends AppCompatActivity {

    // 모드의 종류
    public static final int WEBBROWSING = 1000;
    public static final int MEMOWRITE = 1001;
    public static final int MEMOVIEW = 1002;

    // 뷰페이저 아이템 번호
    private static final int WEBBROWSERFRAGMENT = 0;
    private static final int WEBMEMOLISTFRAGMENT = 1;

    // 위젯 할당
    @BindView(R.id.web_appbar) AppBarLayout mAppBarLayout;
    @BindView(R.id.web_url_input) EditText mUrlInput;
    @BindView(R.id.go_url_btn) ImageButton mGoUrl;
    @BindView(R.id.square_btn) ImageButton mSquareButton;
    @BindView(R.id.web_tabLayout) TabLayout mTabLayout;
    @BindView(R.id.web_viewpager) ViewPager mViewPager;
    @BindView(R.id.web_memo_container) FrameLayout mMemoContainer;
    @BindView(R.id.web_fab) FloatingActionButton mFab;

    // resource 불러오기
    @BindString(R.string.basic_url) String basicUrl;
    @BindString(R.string.tab_web) String tabWeb;
    @BindString(R.string.tab_memo) String tabMemo;
    @BindColor(R.color.colorGray) int gray;
    @BindColor(R.color.colorLightGray) int lightGray;
    @BindColor(R.color.colorDarkGray) int darkGray;
    @BindColor(R.color.colorDarkBlue) int darkBlue;

    // Fragment 관련 변수
    private WebBrowserWebViewFragment mWebBrowserWebViewFragment;
    private WebBrowserMemoListFragment mWebBrowserMemoListFragment;
    private WebBrowserMemoWriteFragment mWebBrowserMemoWriteFragment;
    private WebBrowserMemoViewFragment mWebBrowserMemoViewFragment;
    private FragmentManager mFragmentManager;

    // 현재 상태를 저장하기 위한 변수
    private int currentMode;    // 현재 모드
    private boolean toggleAvailable;    // 토글 가능여부 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webbrowser);
        ButterKnife.bind(this);

        // 첫 모드는 웹 뷰 모드
        currentMode = WEBBROWSING;

        // 1. Setup Url EditText, go Url Button
        mUrlInput.setText(basicUrl);
        mUrlInput.setOnKeyListener(new EditTextOnKeyListener(WebBrowserActivity.this
                , EditTextOnKeyListener.WEBBROWSERACTIVITY));
        mUrlInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // 포커스를 받을 경우 버튼 visible
                if (hasFocus) {
                    mGoUrl.setVisibility(View.VISIBLE);
                }
                // 포커스에서 벗어날 경우 버튼 gone
                else {
                    mGoUrl.setVisibility(View.GONE);
                }
            }
        });
        mGoUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUrl();
            }
        });

        // 2. Setup color of tabLayout
        mTabLayout.setBackgroundColor(lightGray);
        mTabLayout.setSelectedTabIndicatorColor(gray);
        mTabLayout.setTabTextColors(darkGray, darkBlue);

        // 2. Square Button Listener
        mSquareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미 MainActivity가 있으면 그냥 finish()
                // 없으면 startActivity 후 finish()
                Toast.makeText(WebBrowserActivity.this, "Square is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // 3. Setup View Pager
        // 3.1 Creating PagerAdapter
        WebBrowserPagerAdapter adapter = new WebBrowserPagerAdapter(getSupportFragmentManager());
        mWebBrowserWebViewFragment = new WebBrowserWebViewFragment();
        adapter.addFragment(mWebBrowserWebViewFragment, tabWeb);
        mWebBrowserMemoListFragment = new WebBrowserMemoListFragment();
        adapter.addFragment(mWebBrowserMemoListFragment, tabMemo);
        mViewPager.setAdapter(adapter);

        // 3.2 Connecting tabLayout, ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

        // 4. Setup Floating Action Button
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // memo write 모드로 변경
                changeMode(MEMOWRITE);
                Toast.makeText(WebBrowserActivity.this, "fab is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // 5. Setup Fragment Manager
        mFragmentManager = getSupportFragmentManager();
    }

    // 취소키 눌렸을 때
    @Override
    public void onBackPressed() {

        // 웹브라우징 모드일 경우
        if (currentMode == WEBBROWSING) {
            switch (mViewPager.getCurrentItem()) {
                case WEBBROWSERFRAGMENT:
                    // 브라우저에 방문했던 페이지가 있다면 거기로 간다
                    if (!mWebBrowserWebViewFragment.backPressed()) {
                        super.onBackPressed();
                    }
                    break;
                case WEBMEMOLISTFRAGMENT:
                    // 메모 리스트에서 취소키를 누를 시 웹뷰로 간다
                    mViewPager.setCurrentItem(WEBBROWSERFRAGMENT, true);
                    break;
                default:
                    Timber.d("wrong page number: " + mViewPager.getCurrentItem());
            }
        }
        // 메모 쓰기 모드일 경우
        else if (currentMode == MEMOWRITE) {
            // 메모 종료를 묻는 다이얼로그를 넣어야한다
            // 소프트 키보드 활성화 상태 일 시, 키보드를 없앤다

            // 웹브라우징 모드로 변환한다
            changeMode(WEBBROWSING);
        }
        // 메모 보기 모드일 경우
        else if (currentMode == MEMOVIEW) {
            // 웹브라우징 모드로 변환한다
            changeMode(WEBBROWSING);
        }
    }

    // 모드에 따라 액티비티의 형태를 바꾼다
    public void changeMode(int mode) {
        switch (mode) {
            case WEBBROWSING:
                // 1. 툴바를 visible로
                mAppBarLayout.setVisibility(View.VISIBLE);
                // 2. web_memo_container를 gone으로
                mMemoContainer.setVisibility(View.GONE);
                // 3. web_memo_view, web_memo_write fragment들을 null로 할당
                // current mode에 따라 fragment manager에서 지운 후 null로
                if (currentMode == MEMOWRITE) {
                    mFragmentManager.beginTransaction()
                            .remove(mWebBrowserMemoWriteFragment)
                            .commit();
                    mWebBrowserMemoWriteFragment = null;
                } else if (currentMode == MEMOVIEW) {
                    mFragmentManager.beginTransaction()
                            .remove(mWebBrowserMemoViewFragment)
                            .commit();
                    mWebBrowserWebViewFragment = null;
                } else {
                    Timber.d("wrong mode number");
                }
                // 4. web_fab를 visible로
                mFab.setVisibility(View.VISIBLE);
                // 5. current mode를 바꾼다
                currentMode = WEBBROWSING;
                // viewpager는 처음 생성시에 만들고 없애지 않으므로 따로 설정하지 않는다
                break;
            case MEMOWRITE:
                // 1. 툴바를 gone으로
                mAppBarLayout.setVisibility(View.GONE);
                // 2. web_memo_container를 visible로
                mMemoContainer.setVisibility(View.VISIBLE);
                // 3. web_memo_container에 WebMomoWriteFragment를 담는다
                if (mWebBrowserMemoWriteFragment == null) {
                    mWebBrowserMemoWriteFragment = new WebBrowserMemoWriteFragment();
                }
                // 웹 브라우징 모드의 경우 fragment를 새로 할당해야한다
                // 근데 일단 당장은 브라우징 모드에서밖에 못들어오는데
                if (currentMode == WEBBROWSING) {
                    mFragmentManager.beginTransaction()
                            .add(R.id.web_memo_container, mWebBrowserMemoWriteFragment)
                            .commit();
                }
                // 웹 브라우징 모드 이외의 경우
                else {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.web_memo_container, mWebBrowserMemoWriteFragment);
                }
                // 4. web_fab를 gone으로
                mFab.setVisibility(View.GONE);
                // 5. current mode를 변경한다
                currentMode = MEMOWRITE;
                break;
            case MEMOVIEW:
                // 1. 툴바를 gone으로
                mAppBarLayout.setVisibility(View.GONE);
                // 2. web_memo_container를 visible로
                mMemoContainer.setVisibility(View.VISIBLE);
                // 3. web_memo_container에 WebMomoViewFragment를 담는다
                if (mWebBrowserWebViewFragment == null) {
                    mWebBrowserMemoViewFragment = new WebBrowserMemoViewFragment();
                }
                // 웹 브라우징 모드의 경우 fragment를 새로 할당해야한다
                // 근데 일단 당장은 브라우징 모드에서밖에 못들어오는데
                if (currentMode == WEBBROWSING) {
                    mFragmentManager.beginTransaction()
                            .add(R.id.web_memo_container, mWebBrowserMemoViewFragment)
                            .commit();
                }
                // 웹 브라우징 모드 이외의 경우
                else {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.web_memo_container, mWebBrowserMemoViewFragment);
                }
                // 4. web_fab를 gone으로
                mFab.setVisibility(View.GONE);
                // 5. current mode를 바꾼다
                currentMode = MEMOVIEW;
                break;
            default:
                Timber.d("wrong mode number " + mode);
        }
    }

    // 1 웹 뷰를 위한 메소드들
    // 1.1 주소창 변경을 위한 메소드
    public void changeUrl(String url) {
        mUrlInput.setText(url);
    }

    // 1.2 주소창의 내용을 가져오기 위한 메소드
    public String getUrl() {
        return mUrlInput.getText().toString();
    }

    // 1.3 주소 이동을 위한 메소드(엔터키와 버튼 두가지 방식으로 동작)
    public void goUrl() {
        String url = mUrlInput.getText().toString();
        // 주소창이 비었을 경우
        if (url.equals("")) {
            return;
        }
        // 주소에 http가 포함이 안되어 있을 경우
        if (!url.substring(0, 4).equals("http")) {
            url = "http://" + url;
            changeUrl(url);
        }
        mWebBrowserWebViewFragment.goUrl(url);
    }

    // 1.4 EditText 엔터 후 포커스 없애기
    public void clearEditTextFocus() {
        // 포커스 제거
        mUrlInput.clearFocus();
        // 키보드 제거
        InputMethodManager mgr =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(mUrlInput.getWindowToken(), 0);
    }

    // 1.5  토글 가능여부 저장하기
    public void setToggleAvailable(boolean available) {
        Timber.d("toggle available: " + available);
        toggleAvailable = available;
    }

    // 2 웹뷰와 메모 쓰기의 상호 작용을 위한 메소드
    // 2.1 하이라이트 기능이 가능한지 반환
    public boolean isToggleAvailable() {
        return toggleAvailable;
    }

    // 2.2 하이라이트 기능 토글
    public void toggleHighLight() {
        mWebBrowserWebViewFragment.highLight();
    }

    // 2.3 하이라이트 내용 저장
    public void addMemo(String memo) {
        mWebBrowserMemoWriteFragment.addMemo(memo);
    }
}
