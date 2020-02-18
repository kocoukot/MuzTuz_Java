package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PremiaChoose extends AppCompatActivity {


    ArrayList<Integer> arrayListPercents = new ArrayList<>();
    ArrayList<Button> arrayListButtons;
    private static final int CODEFORTUTORIAL = 1;
    private static final int CODEFORFIRSTPREMIYA = 2;
    final String PREFERENCES = "testColorTutorial2222";
    final String PREFERENCESCOINS = "CoinsAmount";
    Button buttonTutorial, buttonLvl1, buttonLvl2, buttonLvl3, buttonLvl4, buttonLvl5, buttonLvl6, buttonLvl7;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premia_choose);

        arrayListButtons = new ArrayList<>();

        buttonTutorial = findViewById(R.id.buttonTutorial);
        buttonLvl1 = findViewById(R.id.lvl1);
        buttonLvl2 = findViewById(R.id.lvl2);
        buttonLvl3 = findViewById(R.id.lvl3);
        buttonLvl4 = findViewById(R.id.lvl4);
        buttonLvl5 = findViewById(R.id.lvl5);
        buttonLvl6 = findViewById(R.id.lvl6);
        buttonLvl7 = findViewById(R.id.lvl7);


        if(arrayListButtons.size() == 0) {
            arrayListButtons.add(buttonTutorial);
            arrayListButtons.add(buttonLvl1);
            arrayListButtons.add(buttonLvl2);
            arrayListButtons.add(buttonLvl3);
            arrayListButtons.add(buttonLvl4);
            arrayListButtons.add(buttonLvl5);
            arrayListButtons.add(buttonLvl6);
            arrayListButtons.add(buttonLvl7);
        }


        SharedPreferences preferencesRestore = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        for (int i = 0; i < preferencesRestore.getInt("length",0); i++){
            arrayListPercents.add(preferencesRestore.getInt(String.valueOf(i),0));
            arrayListButtons.get(i).setBackground(PremiaChoose.this.getDrawable(arrayListPercents.get(i)));
        }



        if(arrayListPercents.size() == 0) {
            arrayListPercents.add(R.mipmap.obychenie_0);
            arrayListPercents.add(R.mipmap.lvl11);
            arrayListPercents.add(R.mipmap.lvl21);
            arrayListPercents.add(R.mipmap.lvl31);
            arrayListPercents.add(R.mipmap.lvl41);
            arrayListPercents.add(R.mipmap.lvl51);
            arrayListPercents.add(R.mipmap.lvl61);
            arrayListPercents.add(R.mipmap.lvl71);
        }


        buttonTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PremiaChoose.this, TutorialLvl.class);
                startActivityForResult(intent,CODEFORTUTORIAL);
            }
        });



    }

    void onSaveState(){
        Integer [] levels = arrayListPercents.toArray(new Integer[0]);
        SharedPreferences preferencesSave = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesSave.edit();

        for (int i = 0; i < levels.length; i++){
            editor.putInt(String.valueOf(i), levels[i]);
        }
        editor.putInt("length", levels.length);
        editor.apply();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODEFORTUTORIAL){
            if(resultCode == RESULT_OK){
                if(data.getIntExtra("key",0) == 100){
                    buttonTutorial.setBackground(PremiaChoose.this.getDrawable(R.mipmap.obychenie_1));
                    arrayListPercents.set(0,R.mipmap.obychenie_1);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onSaveState();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        onSaveState();
    }

    public void onFirstPremiya(View view) {
        Intent intent = new Intent(PremiaChoose.this, FirstPremiya.class);
        startActivityForResult(intent,CODEFORFIRSTPREMIYA);

    }





}
