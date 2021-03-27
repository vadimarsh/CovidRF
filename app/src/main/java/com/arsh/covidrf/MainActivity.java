package com.arsh.covidrf;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.arsh.covidrf.Consts.URL_GET_STATUS_RF;

public class MainActivity extends AppCompatActivity {
    TextView tvCured;
    TextView tvDeath;
    TextView tvTotal;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCured = findViewById(R.id.tvCured);
        tvDeath = findViewById(R.id.tvDeaths);
        tvTotal = findViewById(R.id.tvCases);
        progressBar = findViewById(R.id.progressBar);
        progressBar.animate();

        Loader<String> stringLoader = LoaderManager.getInstance(this).initLoader(1, null, new MyLoaderCallBack());
        stringLoader.forceLoad();

    }
class MyLoaderCallBack implements LoaderManager.LoaderCallbacks<String>{

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyAsyncTaskLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Gson gson = new Gson();
        CountryStatus obj = gson.fromJson(data,CountryStatus.class);
        tvCured.setText(""+obj.getRecovered());
        tvDeath.setText(""+obj.getDeaths());
        tvTotal.setText(""+obj.getCases());
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
}