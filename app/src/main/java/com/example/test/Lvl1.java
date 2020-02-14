package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Lvl1 extends AppCompatActivity {

    ImageView imageView;
    EditText editText;
    String[] artistName;
    TextView textViewAmountLetters, textViewCoins;
    String answer;
    String coins;
    int helpFirstPrise = 150;
    Button buttonShowAmountLetters;
    String newCoins;
    boolean correctAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl);
        imageView = findViewById(R.id.imageViewArtist);
        editText = findViewById(R.id.editTextTipedAnswer);
        textViewAmountLetters = findViewById(R.id.textViewAmoutnLettes);
        textViewCoins = findViewById(R.id.textViewCoins);
        buttonShowAmountLetters = findViewById(R.id.buttonShowAmountLetters);


        Intent newLvl = getIntent();
        artistName = newLvl.getStringArrayExtra("artistName");                  //Получение массива правильных ответов
        coins = newLvl.getStringExtra("coins");                                    //получение количества монет


        textViewCoins.setText(coins);

        String artistPicture = newLvl.getStringExtra("artistPicture");                 //получение изображения артиста
        int picture = Integer.valueOf(artistPicture);
        imageView.setImageResource(picture);


    }

    public void onCheckAnswer(View view) {

        answer = editText.getText().toString().trim();
        answer = answer.replaceAll("[ёЁ]", "е");

        for (int i = 0; i < artistName.length; i++) {
            if (answer.equals(artistName[i])) {
                correctAnswer = true;
                break;
            }
        }

        if (correctAnswer) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Поздравляем! Вы угадали!")
                    .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Lvl1.this.finish();
                        }
                    });
            dialog.show();
        } else {
            Toast toast1 = Toast.makeText(this, "Неправильный ответ!", Toast.LENGTH_LONG);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }
    }

    public void onShowOneLetter(View view) {
        String correctAnswer = artistName[0];
        String lettersAmount = "";

        for (int i = 0; i < correctAnswer.length(); i++) {
            if (!String.valueOf(correctAnswer.charAt(i)).equals(" ")) {
                lettersAmount += "_ ";
            } else {
                lettersAmount += "   ";
            }
        }
        textViewAmountLetters.setText(lettersAmount.trim());
        useHelp(R.string.showLettersAmount, buttonShowAmountLetters);


    }




    private void useHelp(int message, final Button buttonPressed) {

        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.help);

        TextView text = builder.findViewById(R.id.textInform);
        final Button buttonYes = builder.findViewById(R.id.buttonHelpYes);
        final Button buttonNo = builder.findViewById(R.id.buttonHelpNo);

        text.setText(message);

        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.67f);
        layout.setMargins(50, 50, 50, 50);
        text.setLayoutParams(layout);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonPressed.equals(buttonShowAmountLetters)) {
                    builder.cancel();

                    coinsCalc();

                    buttonPressed.setClickable(false);
                    textViewAmountLetters.setVisibility(View.VISIBLE);


//                } else if (buttonPressed.equals(buttonShowOneLetter)) {
//                    builder.cancel();
//                    secondBlock.setVisibility(View.VISIBLE);
//                    buttonPressed.setClickable(false);
//
//                    chooseOneLetter();
//                } else if (buttonPressed.equals(buttonSongName)) {
//                    builder.cancel();
//                    thirdBlock.setVisibility(View.VISIBLE);
//                    buttonPressed.setClickable(false);
//
//                    textSongName.setVisibility(View.VISIBLE);
//
//
//                } else if (buttonPressed.equals(buttonFriendHelp)) {
//                    builder.cancel();
//                    buttonPressed.setClickable(false);
//
//
//                  //  showPublicHelp();
                }


            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });
        builder.show();
    }


    private void coinsCalc(){
        int haveCoins = Integer.valueOf((String) textViewCoins.getText());
        Toast toast;
        if (haveCoins - helpFirstPrise >= 0){
            newCoins = String.valueOf(haveCoins - helpFirstPrise);
            textViewCoins.setText(newCoins);
        } else {
            toast = Toast.makeText(Lvl1.this, "К сожалению вас недостаточно монет", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

    }

    public void onHelpAboutHelp(View view) {
        final Dialog builder = new Dialog(this);
        builder.setContentView(R.layout.information);
//        final Button buttonInformation = builder.findViewById(R.id.buttonInformation);
//        buttonInformation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                builder.cancel();
//            }
//        });
        builder.show();
    }
}
