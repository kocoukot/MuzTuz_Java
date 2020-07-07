package com.example.test.commonFuncs;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class CommonFunc extends AppCompatActivity {

    final String PREFERENCES = "testMuzTus";

    public void setStarsCoins(TextView coins, TextView stars){
        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        coins.setText(String.valueOf(preferences.getInt("coins", 0)));
        stars.setText(String.valueOf(preferences.getInt("stars", 0)));
    }
}

