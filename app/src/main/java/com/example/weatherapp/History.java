package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class History extends AppCompatActivity {
    String temp;
    String min;
    String max;
    String clouds;
    String winds;
    String windr;
    String pressure;
    String humidity;
    Date fsunrise;
    Date fsunset;
    Long sunrisedt;
    Long sunsetdt;
    String sunrise;
    String sunset;

    Long dt;
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        try {
            JSONObject hist = new JSONObject(getIntent().getStringExtra("Response"));
            JSONArray main = hist.getJSONArray("daily");

            for(int i = 0; i < main.length();i++){
                JSONObject x = main.getJSONObject(i);
                JSONObject y = x.getJSONObject("temp");
                JSONArray z = x.getJSONArray("weather");
                JSONObject q = z.getJSONObject(0);
                temp = y.getString("day");
                min = y.getString("min");
                max = y.getString("max");
                pressure = x.getString("pressure");
                humidity = x.getString("humidity");
                winds = x.getString("wind_speed");
                windr = x.getString("wind_deg");
                dt = x.getLong("dt");
                sunrisedt = x.getLong("sunrise");
                sunsetdt = x.getLong("sunset");
                clouds = q.getString("main");
                date = new java.util.Date(dt*1000);
                fsunrise = new java.util.Date(sunrisedt * 1000);
                fsunset = new java.util.Date(sunsetdt * 1000);
                String fdate = new SimpleDateFormat("MM-dd-yy").format(date);
                sunrise = new SimpleDateFormat("hh:mm:ss").format(fsunrise);
                sunset = new SimpleDateFormat("hh:mm:ss").format(fsunset);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}