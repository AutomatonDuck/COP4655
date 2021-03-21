package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class History extends AppCompatActivity {
    String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        try {
            JSONObject hist = new JSONObject(getIntent().getStringExtra("Response"));
            //System.out.println(hist);
          // System.out.println(temp);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}

