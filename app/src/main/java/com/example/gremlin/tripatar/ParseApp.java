package com.example.gremlin.tripatar;

import android.app.Application;
import android.content.res.Resources;

import com.parse.Parse;

/**
 * Created by gremlin on 05/03/16.
 */
public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Resources res = getResources();
//        Parse.initialize(this, res.getString(R.string.parse_app_id), res.getString(R.string.parse_client_key));
    }
}
