package com.github.adepalatis.ee461.recipez;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by michael on 5/1/16.
 */
public class RecipEZApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}