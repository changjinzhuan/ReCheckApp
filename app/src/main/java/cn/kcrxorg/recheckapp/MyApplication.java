package cn.kcrxorg.recheckapp;

import android.app.Application;

/**
 * Created by chang on 2017/9/22.
 */

public class MyApplication extends Application {

    private static final String VALUE = "root";
    private String username;
    @Override
    public void onCreate() {
        super.onCreate();
        username=VALUE;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
