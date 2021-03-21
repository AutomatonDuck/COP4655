package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
            ArrayList wethList = new ArrayList(main.length());
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

                wethList.add(main.getString(i));
           /*     ArrayList<String> hList = new ArrayList<>();
                hList.add(0,fdate);
                hList.add(1,temp);
                hList.add(2,min);
                hList.add(3,max);
                hList.add(4,pressure);
                hList.add(5,humidity);
                hList.add(6, winds);
                hList.add(7,windr);

                hList.add(8,sunrise);
                hList.add(9,sunset);*/



            }
            ListView lv = (ListView) findViewById(R.id.histList);
            lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item,wethList));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}