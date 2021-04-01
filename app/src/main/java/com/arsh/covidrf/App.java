package com.arsh.covidrf;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class App extends Application {
    private static final String DEFAULT_TAG = "default";
    private static App app;
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static synchronized App getApp(){
        return app;
    }
    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(DEFAULT_TAG);
        getRequestQueue().add(request);
    }
    public void cancelRequests(String tag){
        getRequestQueue().cancelAll(tag);
    }
}
