package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FirstPremiya extends AppCompatActivity {


    ImageView lvl11, lvl12, lvl13, lvl14, lvl15, lvl16, lvl17, lvl18, lvl19;

    Intent intent;
    int coins;
    private int picture;
    private static final int CODEFORLVL11 = 1;
    private String artistSong;
    TextView textViewCoins;
    private int lvlID, viewID;
    private int minDuration;
    private int sumDuration;

    private final String PREFERENCES = "LVLInfo";

    private boolean[] lvlsPast;
    private boolean lvlPast;
    String[] artist ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_premiya);
        coins = 1000;
        intent = new Intent(FirstPremiya.this, Lvl1.class);
        textViewCoins = findViewById(R.id.textViewCoins);
        textViewCoins.setText(String.valueOf(coins));

        lvl11 = findViewById(R.id.lvl10);
        lvl12 = findViewById(R.id.lvl11);
        lvl13 = findViewById(R.id.lvl12);
        lvl14 = findViewById(R.id.lvl13);
        lvl15 = findViewById(R.id.lvl14);
        lvl16 = findViewById(R.id.lvl15);
        lvl17 = findViewById(R.id.lvl16);
        lvl18 = findViewById(R.id.lvl17);
        lvl19 = findViewById(R.id.lvl18);


        lvlsPast = new boolean[] {false, false, false, false, false, false, false, false, false};
        //pastLevels =


        lvl11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = R.drawable.artist_1_1_pugacheva;
                artist = new String[]{"алла пугачева", "пугачева", "алла борисовна", "пугачева алла", "алла борисовна пугачева", ""};
                artistSong = "Песня: Миллион алых роз\nАльбом: Миллион роз";
                lvlID = 0;
                viewID = v.getId();
                startLvl();
            }
        });


        lvl12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picture = R.drawable.artist_1_2_timati;
                artist = new String[]{"тимати", "тимур юнусов", "timati", "тимур сказка", "тимур беноевский", "теймураз", ""};
                lvlID = 1;
                viewID = v.getId();

                startLvl();
            }
        });

        lvl13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = R.drawable.artist_1_3_bi2;
                artist = new String[]{"би 2", "би-2", "би два", "би2", ""};
                lvlID = 2;
                viewID = v.getId();
                startLvl();
            }
        });

        lvl14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = R.drawable.artist_1_4_mihaylov;
                artist = new String[]{"стас михайлов", "михайлов", "станислав михайлов", "михайлов станислав", ""};
                lvlID = 3;
                viewID = v.getId();
                startLvl();
            }
        });

        lvl15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = R.drawable.artist_1_5_leps;
                artist = new String[]{"григорий лепс", "лепс григорий", "лепс", "григорий лепсверидзе", "плепс", "лепсверидзе григорий", ""};
                lvlID = 4;
                viewID = v.getId();
                startLvl();
            }
        });

        lvl16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = R.drawable.artist_1_6_elka;
                artist = new String[]{"елка", "елизавета иванцив", "иванцив елизавета", "иванцив лиза", "лиза иванцив", "иванцив", ""};
                lvlID = 5;
                viewID = v.getId();
                startLvl();
            }
        });

        lvl17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = R.drawable.artist_1_7_bilan;
                artist = new String[]{"дима билан", "дима белан", "виктор билан", "виктор белан", "билан дима", "белан дима", "билан виктор", "белан виктор", "билан", "белан", ""};
                lvlID = 6;
                viewID = v.getId();
                startLvl();
            }
        });

        lvl18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = R.drawable.artist_1_8_shnur;
                artist = new String[]{"сергей шнуров", "шнур", "ленинград", "шнуров сергей", "шнуров", ""};
                lvlID = 7;
                viewID = v.getId();
                startLvl();
            }
        });

        lvl19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture = R.drawable.artist_1_9_gradskiy;
                artist = new String[]{"александр градский", "градский александр", "александр фрадкин", "фрадкин александр", "славяне", "скоморохи", ""};
                lvlID = 8;
                viewID = v.getId();
                startLvl();
            }
        });

    }

    private void startLvl(){
        intent.putExtra("coins", coins);
        intent.putExtra("artistName",artist);
        intent.putExtra("artistPicture",picture);
        intent.putExtra("artistSong",artistSong);
        intent.putExtra("lvlPast", lvlPast);

        startActivityForResult(intent,CODEFORLVL11);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODEFORLVL11){
            if(resultCode == RESULT_OK){
                coins = Integer.valueOf(data.getStringExtra("coinsFromLvl"));
                textViewCoins.setText(data.getStringExtra("coinsFromLvl"));

               lvlsPast[lvlID] = data.getBooleanExtra("lvlPast",false);
                sumDuration += data.getIntExtra("lvlDuration",0);							//суммирование времени прохождения уровней

                if (data.getBooleanExtra("lvlPast",false) && minDuration >  data.getIntExtra("lvlDuration",0)){			//вычисление наименьшего времени
                    minDuration = data.getIntExtra("lvlDuration",0);
                }


					switch (viewID){
					case (R.id.lvl10):
						lvl11.setImageResource(R.drawable.artist_1_1_pugacheva_little_done);
						break;
					case (R.id.lvl11):
						lvl12.setImageResource(R.drawable.artist_1_2_timati_little_done);
						break;
					case (R.id.lvl12):
						lvl13.setImageResource(R.drawable.artist_1_3_bi2_little_done);
						break;
					case (R.id.lvl13):
						lvl14.setImageResource(R.drawable.artist_1_4_mihaylov_little_done);
						break;
					case (R.id.lvl14):
						lvl15.setImageResource(R.drawable.artist_1_5_leps_little_done);
						break;
					case (R.id.lvl15):
						lvl16.setImageResource(R.drawable.artist_1_6_elka_little_done);
						break;
					case (R.id.lvl16):
						lvl17.setImageResource(R.drawable.artist_1_7_bilan_little_done);
						break;
					case (R.id.lvl17):
						lvl18.setImageResource(R.drawable.artist_1_8_shnur_little_done);
						break;
					case (R.id.lvl18):
						lvl19.setImageResource(R.drawable.artist_1_9_gradskiy_little_done);
						break;

					}
					//замена картинки на пройденную
              //  }
            }
        }
    }
/*
		intent_finish.putExtra("coinsFromLvl", intCoinsWon + coins);				//передаем количество монет
        intent_finish.putExtra("lvlPast", true);								//передаем факт прохождения уровня
		intent_finish.putExtra("lvlDuration", lvlDuration);						//передаем длительность прохождения уровня
		intent_finish.putExtra("lvlID", lvlID);
 */



}
