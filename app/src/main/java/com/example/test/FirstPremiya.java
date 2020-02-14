package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class FirstPremiya extends AppCompatActivity {


    ImageView lvl11, lvl12, lvl13, lvl14, lvl15, lvl16, lvl17, lvl18, lvl19;
    Intent intent;
    String coins = "1000";
    private static final int CODEFORLVL11 = 1;
    String picture;
    Drawable drawable;
    String[] artist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_premiya);

        intent = new Intent(FirstPremiya.this, Lvl1.class);

        lvl11 = findViewById(R.id.lvl11);
        lvl12 = findViewById(R.id.lvl12);
        lvl13 = findViewById(R.id.lvl13);
        lvl14 = findViewById(R.id.lvl14);
        lvl15 = findViewById(R.id.lvl15);
        lvl16 = findViewById(R.id.lvl16);
        lvl17 = findViewById(R.id.lvl17);
        lvl18 = findViewById(R.id.lvl18);
        lvl19 = findViewById(R.id.lvl19);


        lvl11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = String.valueOf(R.drawable.artist_1_1_pugacheva);
                artist = new String[]{"Алла Пугачева", "Пугачева", "Алла Борисовна", "Пугачева Алла", "Алла Пугачева", "Алла Борисовна Пугачева"};
                startLvl();
            }
        });


        lvl12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picture = String.valueOf(R.drawable.artist_1_2_timati);
                artist = new String[]{"Тимати", "Тимур Юнусов", "Timati", "Тимур Сказка", "Тимур Беноевский", "Теймураз"};
                startLvl();
            }
        });

        lvl13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = String.valueOf(R.drawable.artist_1_3_bi2);
                artist = new String[]{"Би 2", "Би-2", "БИ 2", "БИ-2", "БИ два"};
                startLvl();
            }
        });

        lvl14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = String.valueOf(R.drawable.artist_1_4_mihaylov);
                artist = new String[]{"Стас Михайлов", "Михайлов", "Станислав Михайлов", "Михайлов Станислав"};
                startLvl();
            }
        });

        lvl15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = String.valueOf(R.drawable.artist_1_5_leps);
                artist = new String[]{"Григорий Лепс", "Лепс Григорий", "Лепс", "Григорий Лепсверидзе", "Плепс", "Лепсверидзе Григорий"};
                startLvl();
            }
        });

        lvl16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = String.valueOf(R.drawable.artist_1_6_elka);
                artist = new String[]{"елка", "Елизавета Иванцив", "Иванцив Елизавета", "Иванцив Лиза", "Лиза Иванцив", "Иванцив"};
                startLvl();
            }
        });

        lvl17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = String.valueOf(R.drawable.artist_1_7_bilan);
                artist = new String[]{"Дима Билан", "Дима Белан", "Виктор Билан", "Виктор Белан", "Билан Дима", "Белан Дима", "Билан Виктор", "Белан Виктор", "Билан", "Белан"};
                startLvl();
            }
        });

        lvl18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = String.valueOf(R.drawable.artist_1_8_shnur);
                artist = new String[]{"Сергей Шнуров", "Шнур", "Ленинград", "Шнуров Сергей", "Шнуров"};
                startLvl();
            }
        });

        lvl19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = String.valueOf(R.drawable.artist_1_9_gradskiy);
                artist = new String[]{"Александр Градский", "Градский Александр", "Александр Фрадкин", "Фрадкин Александр", "Славяне", "Скоморохи"};
                startLvl();
            }
        });

    }

    private void startLvl(){
        intent.putExtra("coins", String.valueOf(coins));
        intent.putExtra("artistName",artist);
        intent.putExtra("artistPicture",picture);
        startActivityForResult(intent,CODEFORLVL11);
    }

}
