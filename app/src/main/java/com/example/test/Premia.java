package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.commonFuncs.GridAdapter;
import com.example.test.commonFuncs.LevelsInfo;

public class Premia extends AppCompatActivity {

    private static final int CODEFORLVL = 3;
    private String artistSong;
    TextView textViewCoins, textViewStars;
    private int minDuration;
    private int sumDuration;
    private int selectedLvl;

    SharedPreferences preferencesProgress, preferencesPrizes;

    final String PREFERENCESProgress = "Preferences.progress";
    final String PREFERENCESPrizes = "Preferences.prizes";

    private Integer[] levelsSolvedList;
    private boolean nextPremiaIsOpened;

    private int premiaID;
    GridView gridView;
    GridAdapter adapter;
    private Integer[] levelsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_premiya);
        Intent premiaIntent = getIntent();


        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE);
        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE);

        textViewCoins = findViewById(R.id.premiaCoins);
        textViewStars = findViewById(R.id.premiaStars);


        premiaID = premiaIntent.getIntExtra("premiaIDChoosed", 0);
        levelsList = new LevelsInfo().premiaImagesList[premiaIntent.getIntExtra("premiaIDChoosed", 0)];
        levelsSolvedList = new Integer[levelsList.length];
        for (int i = 0; i < levelsList.length; i++) {
            levelsSolvedList[i] = preferencesProgress.getInt("solved" + premiaID + i, 0);
        }

        gridView = findViewById(R.id.gridView);

        adapter = new GridAdapter(Premia.this, levelsList, levelsSolvedList);


        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startLvl(position);
            }
        });
        coinsStarsUpDate();
        nextPremiaIsOpened = hasOpenedNextPremia();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void startLvl(Integer position) {
        selectedLvl = position;
        Intent intent = new Intent(getApplicationContext(), Level.class);
        intent.putExtra("position", position);
        intent.putExtra("premiaIDtoLVL", premiaID);
        startActivityForResult(intent, CODEFORLVL);
    }

    private void coinsStarsUpDate() {
        textViewCoins.setText(String.valueOf(preferencesPrizes.getInt("coins", 0)));
        textViewStars.setText(String.valueOf(preferencesPrizes.getInt("stars", 0)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODEFORLVL) {
            coinsStarsUpDate();
            levelsSolvedList[selectedLvl] = preferencesProgress.getInt("solved" + premiaID + selectedLvl, 0);
            adapter.notifyDataSetChanged();
            if (hasOpenedNextPremia() & !nextPremiaIsOpened) {
                nextPremiaIsOpened = hasOpenedNextPremia();
                showInfo("Поздравляем с открытием следующей премии!");
            }
//            if (resultCode == RESULT_OK) {
//               // data.getIntExtra("lvlID", 0);
//                levelsSolvedList[selectedLvl] = preferencesProgress.getInt("solved" + premiaID + selectedLvl, 0);
//                adapter.notifyDataSetChanged();
//            }
//            else if (resultCode == RESULT_CANCELED){
//                //data.getIntExtra("lvlID", 0);
//             //   levelsSolvedList[data.getIntExtra("lvlID", 0)] = preferencesProgress.getInt("solved" + premiaID + data.getIntExtra("lvlID", 0), 0);
//               // adapter.notifyDataSetChanged();
//            }
        }
    }

    private boolean hasOpenedNextPremia() {
        if ((((Double.valueOf(levelsSolvedAmount(premiaID)) / Double.valueOf(new LevelsInfo().premiaDisksList[premiaID].length)) * 100) >= new LevelsInfo().requaredAmount) && premiaID < 6) {
            return true;
        } else {
            return false;
        }
    }

    private Integer levelsSolvedAmount(Integer premia) {
        Integer levelsSolvedAmount = 0;
        for (int i = 0; i < new LevelsInfo().premiaDisksList[premia].length; i++) {
            if (preferencesProgress.getInt("solved" + premia + i, 0) == 1) {
                levelsSolvedAmount += 1;
            }
        }
        return levelsSolvedAmount;
    }

    private void showInfo(String message) {

        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.inform);

        TextView text = builder.findViewById(R.id.textInform);
        final Button button = builder.findViewById(R.id.buttonInformOK);
        text.setText(message);


        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.67f);
        layout.setMargins(50, 50, 50, 50);
        text.setLayoutParams(layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });
        builder.show();
    }

}
