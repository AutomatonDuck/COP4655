package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    Long dt;
    Date date;

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






        }catch (JSONException e){
            cityView.setText("ERROR");
            e.printStackTrace();
        }


    }
    public void toMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lon", lon);
        startActivity(intent);
    }
    public  void toSearch(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}