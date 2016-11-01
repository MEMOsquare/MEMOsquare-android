package com.estsoft.memosquare.utils;

import android.content.Context;
import android.util.Base64;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by hokyung on 2016. 10. 21..
 */

public class InjectScript {

    private static InjectScript sInjectScript;

    private Context mContext;
    private List<String> jsFiles;
    private List<String> cssFiles;

    public static InjectScript get(Context context) {
        if (sInjectScript == null) {
           sInjectScript = new InjectScript(context);
        }

        return sInjectScript;
    }

    private InjectScript(Context context) {
        mContext = context;

        jsFiles = new ArrayList<>();

//        jsFiles.add("js/medium-editor.js");
//        jsFiles.add("js/rangy-core.js");
//        jsFiles.add("js/rangy-cssclassapplier.js");
//        jsFiles.add("js/hammer.min.js");
        jsFiles.add("js/jquery-3.1.1.js");
//        jsFiles.add("js/jquery.mobile-events-1.0.5.js");

        // 내 javascript 파일은 맨 뒤로
//        jsFiles.add("js/function-script.js");
        jsFiles.add("js/memosquare.js");

        cssFiles = new ArrayList<>();

//        cssFiles.add("css/memosquare.css");
//        cssFiles.add("css/demo.css");
//        cssFiles.add("css/font-awesome.css");
//        cssFiles.add("css/medium-editor.css");
//        cssFiles.add("css/bootstrap.css");
    }

    public void injectJavaScriptFiles(WebView view) {
        for (String file : jsFiles) {
            Timber.d("inject javascript files " + file);
            injectJavaScriptFile(view, file);
        }
    }

    public void injectCSSFiles(WebView view) {
        for (String file : cssFiles) {
            Timber.d("inject css files " + file);
            injectCSSFile(view, file);
        }
    }

    private void injectJavaScriptFile(WebView view, String scriptFile) {
        InputStream input;
        try {
            input = mContext.getAssets().open(scriptFile);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();

            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            view.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('body').item(0);" +
                    "var script = document.createElement('script');" +
                    "script.type = 'text/javascript';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "script.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(script)" +
                    "})();");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void injectCSSFile(WebView view, String cssFile) {
        view.loadUrl("javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var link = document.createElement('link');" +
                "link.rel = 'stylesheet';" +
                "link.href = '" + cssFile + "';" +
                "link.type = 'text/css';" +
                "parent.prepend(link)" +
                "})();");
//        String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssFile + "\" />";
//        view.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
    }

    private void injectEditableClass(WebView view) {
        view.loadUrl("javascript:(function() {" +
                "var body = document.getElementsByTagName('body').item(0);" +
                "body.classList.add('editable');" +
                "})();");
    }
}
