package com.arsh.covidrf;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.arsh.covidrf.Consts.URL_GET_STATUS_RF;

public class MainActivity extends AppCompatActivity {
    private TextView tvCured;
    private TextView tvDeath;
    private TextView tvTotal;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCured = findViewById(R.id.tvCured);
        tvDeath = findViewById(R.id.tvDeaths);
        tvTotal = findViewById(R.id.tvCases);
        progressBar = findViewById(R.id.progressBar);
        progressBar.animate();
        getDataFromApi();
    }

    private void getDataFromApi() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_GET_STATUS_RF, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject data = response;
                Gson gson = new Gson();
                CountryStatus obj = gson.fromJson(String.valueOf(data), CountryStatus.class);
                tvCured.setText("" + obj.getRecovered());
                tvDeath.setText("" + obj.getDeaths());
                tvTotal.setText("" + obj.getCases());
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("request", " succes!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("request", " error" + error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getApp().addToRequestQueue(request);
    }
}