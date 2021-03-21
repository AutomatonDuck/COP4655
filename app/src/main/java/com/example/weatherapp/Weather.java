package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Weather extends AppCompatActivity {
    String temp;
    TextView cityView;
    TextView tempView;
    TextView cloudsView;
    TextView dateView;
    TextView windView;
    TextView pressureView;
    TextView humidityView;
    TextView sunrsView;
    TextView latlonView;
    String city;
    String clouds;
    String winds;
    String windr;
    String pressure;
    String humidity;
    String lat;
    String lon;
    Date fsunrise;
    Date fsunset;
    Long sunrisedt;
    Long sunsetdt;
    String sunrise;
    String sunset;
    String id;
    Long dt;
    Date date;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        cityView = (TextView) findViewById(R.id.cityView);
        cloudsView = (TextView) findViewById(R.id.cloudsView);
        dateView = (TextView) findViewById(R.id.dateView);
        windView = (TextView) findViewById(R.id.windView);
        pressureView = (TextView) findViewById(R.id.pressureView);
        humidityView = (TextView) findViewById(R.id.humidityView);
        sunrsView = (TextView) findViewById(R.id.sunrsView);
        latlonView = (TextView) findViewById(R.id.latlonView);
        tempView = (TextView) findViewById(R.id.tempView);
        getSupportActionBar().hide();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navMenu2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Menu nav_menu = bottomNavigationView.getMenu();
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                nav_menu.findItem(R.id.action_weather).setVisible(false);
                nav_menu.findItem(R.id.action_GPS).setVisible(false);
                switch (item.getItemId()) {
                    case R.id.action_home:
                        toSearch();
                        //Toast.makeText(Weather.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_map:
                        toMap();
                        //Toast.makeText(Weather.this, "Map", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_history:
                        getHistory(url);
                        //Toast.makeText(Weather.this, "History", Toast.LENGTH_SHORT).show();

                        break;
                }
                return true;
            }
        });

        try {
            JSONObject weath = new JSONObject(getIntent().getStringExtra("Response"));
            JSONObject main = weath.getJSONObject("main");
            JSONObject sys = weath.getJSONObject("sys");
            JSONArray weather = weath.getJSONArray("weather");
            JSONObject weather2 = weather.getJSONObject(0);
            JSONObject coord = weath.getJSONObject("coord");
            JSONObject wind = weath.getJSONObject("wind");

            //JSONObject city = weath.getJSONObject("name");
            dt =  weath.getLong("dt");
            temp = main.getString("temp");
            city = weath.getString("name");
            id = weath.getString("id");
            //fix this // make another JSONOBJECT To pull deeper data
            clouds = weather2.getString("description");
            winds = wind.getString("speed");
            windr = wind.getString("deg");
            pressure = main.getString("pressure");
            humidity = main.getString("humidity");
            lat = coord.getString("lat");
            lon = coord.getString("lon");
            sunrisedt = sys.getLong("sunrise");
            sunsetdt = sys.getLong("sunset");



            date = new java.util.Date(dt*1000);
            fsunrise = new java.util.Date(sunrisedt * 1000);
            fsunset = new java.util.Date(sunsetdt * 1000);

            String fdate = new SimpleDateFormat("MM-dd-yy").format(date);
            sunrise = new SimpleDateFormat("hh:mm:ss").format(fsunrise);
            sunset = new SimpleDateFormat("hh:mm:ss").format(fsunset);


            cityView.setText(city);
            tempView.setText(temp +"\u00B0F");
            cloudsView.setText("Skies: " + clouds);
            windView.setText(winds + "/" + windr + "\u00B0");
            pressureView.setText(pressure + "mmhg");
            humidityView.setText(humidity);
            latlonView.setText(lat + "\u00B0/" + lon + "\u00B0");
            sunrsView.setText("Sunrise: "+ sunrise + " / " + "Sunset: " + sunset);
            dateView.setText(String.valueOf(fdate));

            String APIkey = getString(R.string.APIKEY);
            url = "https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&exclude=hourly,minutely,current&appid="+ APIkey+"&units=imperial";


        }catch (JSONException e){

            e.printStackTrace();
        }


    }
    public void toMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lon", lon);
        startActivity(intent);
    }
    public  void toSearch(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getHistory(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        //String cityname = "miami";
        //String APIkey = "6184e454348e716d1cb4b6f3124dc521";
        //String url =

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,

                response -> {
                    //textView.setText("Response is: " + response.substring(0, 489)); //response is only length 489
                    //System.out.println("***"+response.substring(0,500));
                    try {
                        JSONObject repo = new JSONObject(response);
                        sendResponse(repo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Weather.this, "No Response dood", Toast.LENGTH_SHORT).show(); // change to toast

            }
        });
        queue.add(stringRequest);


    }

    public void sendResponse(JSONObject response){
        Intent intent = new Intent(this, History.class);
        intent.putExtra("Response", response.toString());
        startActivity(intent);
    }
}