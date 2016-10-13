package com.estsoft.memosquare;

import android.app.Application;

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
        }
    }
}
