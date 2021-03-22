package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navMenu3);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Menu nav_menu = bottomNavigationView.getMenu();
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home:
                        toSearch();
                        //Toast.makeText(Weather.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_weather:
                        //Toast.makeText(History.this, "Weather is above", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_GPS:
                        //Toast.makeText(History.this, "Weather is above", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_map:
                        //Toast.makeText(Weather.this, "Map", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_history:
                        //Toast.makeText(Weather.this, "History", Toast.LENGTH_SHORT).show();

                        break;
                }
                return true;
            }
        });




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



                ArrayList<String> hList = new ArrayList<>();
                hList.add(0,"Date: "+ fdate+"\n");
                hList.add(1,"Avg Temp: "+temp+"F\n");
                hList.add(2,"Min temp: "+min+"F\n");
                hList.add(3,"Max Temp: "+max+"F\n");
                hList.add(4,"Pressure: "+pressure+"mmgh\n");
                hList.add(5,"Humidity: "+humidity+"\n");
                hList.add(6,"Wind Speed: "+winds+"\n");
                hList.add(7,"Wind Direction :"+windr+"\n");
                hList.add(8,"Sunrise: "+sunrise+"\n");
                hList.add(9,"Sunset: "+sunset+"\n");

                wethList.add(hList);

            }
            ListView lv = (ListView) findViewById(R.id.histList);
            lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item,wethList));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void toSearch(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void toMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}