package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    Button button;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  Intent intent = new Intent(this, StartScreen.class);
       // startActivity(intent);
        button = findViewById(R.id.buttonPlay);




    }

    public void onStartPlay(View view) {
        intent = new Intent(this,PremiaChoose.class);
        startActivity(intent);
    }

    public void onStatistic(View view) {
        intent = new Intent(this,Statistika.class);
        startActivity(intent);
    }
}
