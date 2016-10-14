package com.estsoft.memosquare.activities;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.estsoft.memosquare.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class WebViewTestActivity extends AppCompatActivity {

    @BindView(R.id.webview_progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.webview_test) WebView mWebView;

    private Uri mUri;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_test);
        ButterKnife.bind(this);

//        Timber.d("onCreate");

        mProgressBar.setMax(100);

        mUri = Uri.parse("www.naver.com")
                .buildUpon()
                .build();

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                getSupportActionBar().setSubtitle(title);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        mWebView.loadUrl("http://www.naver.com");
    }
}
