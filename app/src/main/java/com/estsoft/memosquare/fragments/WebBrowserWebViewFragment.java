package com.estsoft.memosquare.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.estsoft.memosquare.R;
import com.estsoft.memosquare.activities.WebBrowserActivity;
import com.estsoft.memosquare.utils.InjectScript;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by hokyung on 2016. 11. 1..
 * 오직 웹뷰만 있는 fragment
 */

public class WebBrowserWebViewFragment extends Fragment {

    // 위젯 할당
    @BindView(R.id.webview) WebView mWebView;

    // 부모 액티비티
    private WebBrowserActivity mActivity;

    // 웹페이지 로딩을 위한 handler
    private Handler mHandler;

    // resource 바인딩
    @BindString(R.string.basic_url) String mUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate view
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        ButterKnife.bind(this, view);

        // 부모 액티비티 연결
        mActivity = (WebBrowserActivity) getActivity();

        // 핸들러 할당
        mHandler = new Handler();

        // 웹뷰 세팅
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowUniversalAccessFromFileURLs(true);

        // 자바스크립트 객체 삽입
        mWebView.addJavascriptInterface(new JavaScriptInterface(), "INTERFACE");

        // 크롬 클라이언트 설정
        mWebView.setWebChromeClient(new WebChromeClient() {

            // 웹 콘솔 log 메세지 출력
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                Timber.d("console: " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });

        // 웹뷰 클라이언트 설정
        mWebView.setWebViewClient(new WebViewClient() {

            // 자바스크립트 파일 삽입을 위한 Util Class
            InjectScript mInjectScript = InjectScript.get(getActivity());

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mActivity.changeUrl(url);
                mActivity.setToggleAvailable(false);
                mHandler.removeCallbacksAndMessages(null);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Timber.d("webview client onPageFinished");

                final WebView webView = view;

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mActivity, "injection complete", Toast.LENGTH_SHORT).show();
                        mInjectScript.injectJavaScriptFiles(webView);
                        mActivity.setToggleAvailable(true);
                    }
                }, 3000);
            }
        });

        // 웹뷰에 주소창의 내용을 띄운다
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(mUrl);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        // 스래드 초기화
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    // 주소 직접 입력 시 동작 할 메소드
    public void goUrl(String url) {
        final String URL = url;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(URL);
            }
        });
    }

    // 뒤로가기 동작 메소드
    public boolean backPressed() {
        // 이전 기록이 있다면 거기로 이동
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return false;
        }
    }

    // 하이라이트 동작시키기
    public void highLight() {
        mWebView.loadUrl("javascript:memosquareCliping();");
    }

    class JavaScriptInterface {

        public JavaScriptInterface() {
            Timber.d("constructor");
        }

        // 웹에서 텍스트를 받아오기 위한 메소드
        @SuppressWarnings("unused")
        @JavascriptInterface
        public void getContent(String content) {
            final String CONTENT = content;
            Timber.d("getContent: " + CONTENT);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mActivity.addMemo(CONTENT);
                }
            });
        };

        // 웹페이지 전체 내용을 받아오기 위한 메소드
        // 디버깅용
        @SuppressWarnings("unused")
        @JavascriptInterface
        public void getPage(String page) {
            String temp = page;

            // 로그 하나의 최대 길이는 4000
            while (temp.length() > 4000) {
                Timber.d("page: " + temp.substring(0, 4000));
                temp = temp.substring(4000);
            }

            Timber.d("page: " + temp);
        }
    }
}
