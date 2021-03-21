package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.core.app.ActivityCompat;
import android.widget.EditText;
import android.widget.TextView;
import android.content.pm.PackageManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //String temp;
    TextView headerView;
    GPSTracker gps;
    //TextView tempView;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    EditText userInputST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        headerView = (TextView) findViewById(R.id.Header);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Menu nav_menu = bottomNavigationView.getMenu();
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                nav_menu.findItem(R.id.action_home).setVisible(false);
                switch (item.getItemId()) {
                    case R.id.action_weather:
                        //Toast.makeText(MainActivity.this, "Weather", Toast.LENGTH_SHORT).show();
                        checkInput();
                        break;
                    case R.id.action_GPS:
                       // Toast.makeText(MainActivity.this, "Weather", Toast.LENGTH_SHORT).show();
                        getLocation();
                    case R.id.action_map:
                       // Toast.makeText(MainActivity.this, "Map", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_history:
                        //Toast.makeText(MainActivity.this, "History", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });



        //tempView = (TextView) findViewById(R.id.TempView);
        //getWeather();
        headerView.setText("SimpleWeather");
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getWeather(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        //String cityname = "miami";

        //String url =

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                response -> {
                    //textView.setText("Response is: " + response.substring(0, 489)); //response is only length 489

                    try {
                        JSONObject sys = new JSONObject(response);

                        JSONObject main = sys.getJSONObject("main");
                        sendResponse(sys);
                        //temp = main.getString("temp");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //tempView.setText("Temprature: " + temp);


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "No Response", Toast.LENGTH_SHORT).show(); // change to toast

            }
        });
        queue.add(stringRequest);


    }

    public void sendResponse(JSONObject response){
        Intent intent = new Intent(this, Weather.class);
        intent.putExtra("Response", response.toString());
        startActivity(intent);
    }

    public void checkInput() {
        userInputST = (EditText) findViewById(R.id.searchbar);
        String userInput = userInputST.getText().toString();
        boolean isZipcode;
        try {
            Integer.parseInt(userInput);
            isZipcode = true;
        } catch (Exception e) {
            isZipcode = false;
        }
        String APIkey = getString(R.string.APIKEY);
        String url = "https://api.openweathermap.org/data/2.5/weather?/PAC0";
        if (isZipcode) {
            url += "&zip=" + userInput;
        } else {
            url += "&q=" + userInput;
        }
        url += "&appid=" + APIkey + "&units=imperial";
        getWeather(url);
    }

    public void getLocation(){
        gps = new GPSTracker(MainActivity.this);
        String APIkey = getString(R.string.APIKEY);
        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            String lat = Double.toString(latitude);
            String lon = Double.toString(longitude);
            String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat +"&lon=" + lon + "&appid=" + APIkey +"&units=imperial";
            getWeather(url);

            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lon", lon);

        }else {
            headerView.setText("ERROR: No Response");
        }
    }


}
/* Response
{
    "coord": {
        "lon": -80.0831,
        "lat": 26.3587
    },
    "weather": [
        {
            "id": 802,
            "main": "Clouds",
            "description": "scattered clouds",
            "icon": "03n"
        }
    ],
    "base": "stations",
    "main": {
        "temp": 295.51,
        "feels_like": 292.89,
        "temp_min": 294.82,
        "temp_max": 296.15,
        "pressure": 1025,
        "humidity": 56
    },
    "visibility": 10000,
    "wind": {
        "speed": 5.14,
        "deg": 70
    },
    "clouds": {
        "all": 40
    },
    "dt": 1615509332,
    "sys": {
        "type": 1,
        "id": 3394,
        "country": "US",
        "sunrise": 1615462421,
        "sunset": 1615505223
    },
    "timezone": -18000,
    "id": 4148411,
    "name": "Boca Raton",
    "cod": 200
}
 */