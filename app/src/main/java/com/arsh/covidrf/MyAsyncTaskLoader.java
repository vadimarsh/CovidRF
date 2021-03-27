package com.arsh.covidrf;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.arsh.covidrf.Consts.URL_GET_STATUS_RF;

public class MyAsyncTaskLoader extends AsyncTaskLoader<String> {
    public MyAsyncTaskLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        Request request = new Request.Builder().url(URL_GET_STATUS_RF).build();
        String result = "";
        OkHttpClient client = new OkHttpClient();
        try {
            Response responce = client.newCall(request).execute();
            result = responce.body().string();

        } catch (IOException e) {
            result = "No Internet";
            e.printStackTrace();
        }
        return result;
    }
}
