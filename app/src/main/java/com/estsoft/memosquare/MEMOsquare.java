package com.estsoft.memosquare;

import android.app.Application;
import android.util.Log;

import timber.log.Timber;

/**
 * Created by hokyung on 2016. 10. 13..
 */

public class MEMOsquare extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            // Release 버전에서의 로그 트리
            Timber.plant(new ReleaseTree());
        }
    }

    // 상용 버전에서의 로그
    private static class ReleaseTree extends Timber.Tree {

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {

            // 디버깅용 로그는 나타내지 않는다
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            // INFO 로그의 경우
            Log.i(tag, message);

            // ERROR와 WARNING
            if (t != null) {
                if (priority == Log.ERROR) {
                    Log.e(tag, message, t);
                } else if (priority == Log.WARN) {
                    Log.w(tag, message, t);
                }
            }
        }
    }
}
