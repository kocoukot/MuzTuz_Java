package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
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
    private TextView textViewAmountLetters, textViewCoins, textSongName;
    private String answer, coins,artistSong, newCoins, correctAnswer;
    private final int helpLettersAmount = 150;
    private final int helpOneLetter = 150;
    private final int helpSongName = 250;
    TextView textCoinsWon;
    Button buttonShowAmountLetters, buttonSongName, buttonShowOneLetter, buttonSayAnswer;
    private boolean correctAnswerBool = false;
    private boolean lvlPast = false;
    private boolean helpUsed = false;
    private int idButton;
    private int starsForLvl = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl);



        imageView = findViewById(R.id.imageViewArtist);
        editText = findViewById(R.id.editTextTipedAnswer);
        textViewAmountLetters = findViewById(R.id.textViewAmoutnLettes);
        textViewCoins = findViewById(R.id.textViewCoins);

        buttonShowAmountLetters = findViewById(R.id.buttonShowAmountLetters);
        buttonShowOneLetter = findViewById(R.id.buttonShowOneLetter);
        buttonSongName = findViewById(R.id.buttonSongName);
        buttonSayAnswer = findViewById(R.id.buttonSayAnswer);


        textSongName = findViewById(R.id.textSongName);


        Intent newLvl = getIntent();
        artistName = newLvl.getStringArrayExtra("artistName");          //Получение массива правильных ответов
        correctAnswer = artistName[0];
        artistSong = newLvl.getStringExtra("artistSong");
        coins = newLvl.getStringExtra("coins");                                    //получение количества монет
        lvlPast = newLvl.getBooleanExtra("lvlPast", false);

        textViewCoins.setText(coins);
        textSongName.setText(artistSong);

        String artistPicture = newLvl.getStringExtra("artistPicture");                 //получение изображения артиста
        int picture = Integer.valueOf(artistPicture);
        imageView.setImageResource(picture);

        if (lvlPast){
            buttonShowAmountLetters.setClickable(false);
            buttonShowOneLetter.setClickable(false);
            buttonSongName.setClickable(false);
            buttonSayAnswer.setClickable(false);

            editText.setClickable(false);
            editText.setCursorVisible(false);
            editText.setEnabled(false);
            String text = "";
            for (int i = 0; i < correctAnswer.length(); i++ ){
                text += correctAnswer.charAt(i) + " ";

            }

            textViewAmountLetters.setText(text.trim().toUpperCase());
            textViewAmountLetters.setVisibility(View.VISIBLE);
            textSongName.setVisibility(View.VISIBLE);
        }

    }

    public void onCheckAnswer(View view) {

        answer = editText.getText().toString().trim().toLowerCase();
        answer = answer.replaceAll("[ёЁ]", "е");

        for (int i = 0; i < artistName.length; i++) {
            if (answer.equals(artistName[i])) {
                correctAnswerBool = true;
                break;
            }
        }

        if (correctAnswerBool) {

            final Dialog builder = new Dialog(this);
            builder.setCanceledOnTouchOutside(false);
            builder.setContentView(R.layout.layout_end_lvl);

            ImageView imageStar1 = builder.findViewById(R.id.imageStar1);
            ImageView imageStar2 = builder.findViewById(R.id.imageStar2);
            ImageView imageStar3 = builder.findViewById(R.id.imageStar3);
            textCoinsWon = builder.findViewById(R.id.textCoinsWon);
            Button buttonOk = builder.findViewById(R.id.buttonFinishLvl);

            if (helpUsed){
                starsForLvl -= 1;
            }

            switch (starsForLvl){
                case (1):
                    imageStar1.setImageResource(R.drawable.zvezda1_prizovogo_okna);
                    textCoinsWon.setText("100");
                    break;
                case (2):
                    imageStar1.setImageResource(R.drawable.zvezda1_prizovogo_okna);
                    imageStar2.setImageResource(R.drawable.zvezda2_prizovogo_okna);
                    textCoinsWon.setText("200");
                    break;
                case (3):
                    imageStar1.setImageResource(R.drawable.zvezda1_prizovogo_okna);
                    imageStar2.setImageResource(R.drawable.zvezda2_prizovogo_okna);
                    imageStar3.setImageResource(R.drawable.zvezda3_prizovogo_okna);
                    textCoinsWon.setText("300");
                    break;
            }


            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishLvl(Integer.valueOf(textCoinsWon.getText().toString()) + Integer.valueOf(textViewCoins.getText().toString()));
                }
            });

            builder.show();
        }





        else {
            Toast toast1 = Toast.makeText(this, "Неправильный ответ!", Toast.LENGTH_LONG);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }
    }




    public void onShowLettersAmount(View view) {
        setTextViewAmountLetters(-1);
        useHelp(R.string.showLettersAmount, buttonShowAmountLetters);
    }

    public void onShowSongName(View view) {
        useHelp(R.string.showSongName, buttonSongName);
    }

    public void onShowLetter(View view) {
        useHelp(R.string.showOneLetter, buttonShowOneLetter);
    }



    private void useHelp(int message, final Button buttonPressed) {
        helpUsed = true;
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

                    if (coinsCalc(helpLettersAmount)){
                        buttonPressed.setClickable(false);
                        buttonPressed.setBackground(Lvl1.this.getDrawable(R.mipmap.podskazka_kolichestvo_bukv_zakrita));
                        textViewAmountLetters.setVisibility(View.VISIBLE);
                    }

                } else if (buttonPressed.equals(buttonSongName)) {
                    builder.cancel();

                    if (coinsCalc(helpOneLetter)){
                        buttonPressed.setClickable(false);
                        buttonPressed.setBackground(Lvl1.this.getDrawable(R.mipmap.podskazka_albom_pesny_zakrita));
                        textSongName.setVisibility(View.VISIBLE);
                    }
                }

                else if (buttonPressed.equals(buttonShowOneLetter)) {
                    builder.cancel();

                    if (coinsCalc(helpSongName)){
                        buttonPressed.setClickable(false);
                        buttonPressed.setBackground(Lvl1.this.getDrawable(R.mipmap.podskazka_lubay_bukva_zakrita));
                         chooseOneLetter();
                        buttonShowAmountLetters.setClickable(false);
                        buttonShowAmountLetters.setBackground(Lvl1.this.getDrawable(R.mipmap.podskazka_kolichestvo_bukv_zakrita));
                    }
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


    private void chooseOneLetter() {

        String correctAnswer = artistName[0];
        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.show_one_letter);

        LinearLayout linearLayout = builder.findViewById(R.id.layoutHelpShowOneLetter);

        for (int i = 0; i < correctAnswer.length(); i++) {
            ImageView imageView = new ImageView(this);

            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setMargins(0,50,0,50);
            imageView.setLayoutParams(layout);

            if (!String.valueOf(correctAnswer.charAt(i)).equals(" ")) {
                imageView.setImageResource(R.drawable.button_letter);
                imageView.setId(i);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        idButton =  v.getId();
                        builder.cancel();
                        setTextViewAmountLetters(idButton);
                        textViewAmountLetters.setVisibility(View.VISIBLE);

                    }
                });
            }
            else{
                imageView.setImageResource(R.mipmap.vybor_bukvy_podskazki_space);
            }
            linearLayout.addView(imageView);
        }


        builder.show();

    }


    private void setTextViewAmountLetters(int id){
        String lettersAmount = "";
        String choosenLetter = "";
        if (id >= 0) {
            choosenLetter = String.valueOf(correctAnswer.charAt(id)).toUpperCase();
        }
        for (int i = 0; i < correctAnswer.length(); i++) {
            if (i == id){
                lettersAmount += choosenLetter + " ";
            } else {
                if (!String.valueOf(correctAnswer.charAt(i)).equals(" ")) {
                    lettersAmount += "_ ";
                } else {
                    lettersAmount += "   ";
                }
            }
        }

        textViewAmountLetters.setText(lettersAmount.trim());
    }






    private void finishLvl(int coinsAmount){

        Intent intent_finish = new Intent();
        intent_finish.putExtra("coinsFromLvl", coinsAmount);
        intent_finish.putExtra("lvlPast", true);
        setResult(RESULT_OK, intent_finish);
        Lvl1.this.finish();
    }






    private boolean coinsCalc(int helpPrice){

        int haveCoins = Integer.valueOf((String) textViewCoins.getText());
        Toast toast;
        if (haveCoins - helpPrice >= 0){
            newCoins = String.valueOf(haveCoins - helpPrice);
            textViewCoins.setText(newCoins);
            return true;
        } else {
            toast = Toast.makeText(Lvl1.this, "К сожалению у вас недостаточно монет", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,350);
            toast.show();
            return false;
        }
    }




}
