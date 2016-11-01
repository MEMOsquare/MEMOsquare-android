package com.estsoft.memosquare.activities;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.estsoft.memosquare.R;
import com.estsoft.memosquare.adapters.WebBrowserPagerAdapter;
import com.estsoft.memosquare.fragments.WebBrowserMemoListFragment;
import com.estsoft.memosquare.fragments.WebBrowserWebViewFragment;

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

    // 위젯 할당
    @BindView(R.id.web_appbar) AppBarLayout mAppBarLayout;
    @BindView(R.id.web_toolbar) Toolbar mToolbar;
    @BindView(R.id.web_tabLayout) TabLayout mTabLayout;
    @BindView(R.id.web_viewpager) ViewPager mViewPager;
    @BindView(R.id.web_memo_container) FrameLayout mMemoContainer;
    @BindView(R.id.web_fab) FloatingActionButton mFab;

    // String 불러오기
    @BindString(R.string.tab_web) String tabWeb;
    @BindString(R.string.tab_memo) String tabMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webbrowser);
        ButterKnife.bind(this);

        // 1. Setup Toolbar
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // 2. Setup View Pager
        // 2.1 Creating PagerAdapter
        WebBrowserPagerAdapter adapter = new WebBrowserPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WebBrowserWebViewFragment(), tabWeb);
        adapter.addFragment(new WebBrowserMemoListFragment(), tabMemo);
        mViewPager.setAdapter(adapter);

        // 2.2 Connecting tabLayout, ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

        // 3. Setup Floating Action Button
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WebBrowserActivity.this, "fab is clicked", Toast.LENGTH_SHORT).show();
            }
        });
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
