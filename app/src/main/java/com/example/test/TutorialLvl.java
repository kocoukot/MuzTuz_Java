package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.LinkedBlockingQueue;

public class TutorialLvl extends AppCompatActivity {

    Handler handler;
    Button buttonShowAmountLetters, buttonShowOneLetter, buttonSongName;
    Button buttonSayAnswer;
    ImageView firstBlock, secondBlock, thirdBlock;
    int delay = 450;
    int idButton;
    ImageView blueRow1,blueRow2,blueRow3, blueRow4,  blueRow6, blueRow7 ;
    TextView textSongName;
    TextView amoutnLettes;

    LinkedBlockingQueue<Dialog> dialogsToShow = new LinkedBlockingQueue<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_lvl);


        blueRow1 = findViewById(R.id.imageBlueRow1);
        blueRow2 = findViewById(R.id.imageBlueRow2);
        blueRow3 = findViewById(R.id.imageBlueRow3);
        blueRow6 = findViewById(R.id.imageBlueRow6);
        blueRow7 = findViewById(R.id.imageBlueRow7);

        buttonShowAmountLetters = findViewById(R.id.buttonShowAmountLetters);
        buttonShowAmountLetters.setClickable(false);

        buttonShowOneLetter = findViewById(R.id.buttonShowOneLetter);
        buttonShowOneLetter.setClickable(false);

        buttonSongName = findViewById(R.id.buttonSongName);
        buttonSongName.setClickable(false);


        buttonSayAnswer = findViewById(R.id.buttonSayAnswer);

        amoutnLettes = findViewById(R.id.amoutnLettes);
        firstBlock = findViewById(R.id.fistBlock);
        secondBlock = findViewById(R.id.secodBlock);
        thirdBlock = findViewById(R.id.thirdBlock);
        textSongName = findViewById(R.id.textSongName);




     //  LinkedBlockingQueue<Dialog> dialogsToShow = new LinkedBlockingQueue<>();

        showInfo(R.string.helloFriend, 0);
        showInfo(R.string.firstHelp, 0);
        showInfo(R.string.secondHelp1, 1);



    }


    void showDialog(final Dialog dialog) {
        if (dialogsToShow.isEmpty()) {
            dialog.show();
        }
        dialogsToShow.offer(dialog);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface d) {
                dialogsToShow.remove(dialog);
                if (!dialogsToShow.isEmpty()) {
                    dialogsToShow.peek().show();
                }
            }
        });
    }



    private void showInfo (int message, final int number) {

        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.inform);

        TextView text = builder.findViewById(R.id.textInform);
        final Button button = builder.findViewById(R.id.buttonInformOK);
        text.setText(message);

        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.67f);
        layout.setMargins(50,50,50,50);
        text.setLayoutParams(layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
                if (number == 1){
                    buttonShowAmountLetters.setBackground(TutorialLvl.this.getDrawable(R.mipmap.podskazka_kolichestvo_bukv));
                    blueRow1.setVisibility(View.VISIBLE);
                    buttonShowAmountLetters.setClickable(true);
                }else if (number == 2){
                    buttonShowOneLetter.setClickable(true);
                    buttonShowOneLetter.setBackground(TutorialLvl.this.getDrawable(R.mipmap.podskazka_lubay_bukva));
                    blueRow2.setVisibility(View.VISIBLE);
                }
                else if (number == 3){
                    buttonSongName.setClickable(true);
                    buttonSongName.setBackground(TutorialLvl.this.getDrawable(R.mipmap.podskazka_albom_pesny));
                    blueRow3.setVisibility(View.VISIBLE);
                }
                else if(number == 6){
                    finishTutorial();
                }

            }
        });

        showDialog(builder);


    }

private void finishTutorial(){

    Intent intent_finish = new Intent();
    intent_finish.putExtra("key", 100);
    setResult(RESULT_OK, intent_finish);
    TutorialLvl.this.finish();
}

    public void onShowAmountLetters(View view) {useHelp(R.string.showLettersAmount,buttonShowAmountLetters ); }

    public void onShowOneLetter(View view) {useHelp(R.string.showOneLetter,buttonShowOneLetter ); }

    public void onShowSongName(View view) {useHelp(R.string.showSongName,buttonSongName );  }






    private void useHelp(int message, final Button buttonPressed){
        handler = new Handler();
        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.help);

        TextView text = builder.findViewById(R.id.textInform);
        final Button buttonYes = builder.findViewById(R.id.buttonHelpYes);
        final Button buttonNo = builder.findViewById(R.id.buttonHelpNo);
        buttonNo.setClickable(false);

        text.setText(message);

        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.67f);
        layout.setMargins(50,50,50,50);
        text.setLayoutParams(layout);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonPressed.equals(buttonShowAmountLetters)){
                    builder.cancel();
                    firstBlock.setVisibility(View.VISIBLE);
                    buttonPressed.setClickable(false);
                    setTextViewAmountLetters(-1);
                    amoutnLettes.setVisibility(View.VISIBLE);
                    blueRow1.setVisibility(View.INVISIBLE);

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            showInfo(R.string.secondHelp2, 2);
                        }
                    }, delay);

                }else if (buttonPressed.equals(buttonShowOneLetter)){
                    builder.cancel();
                    secondBlock.setVisibility(View.VISIBLE);
                    buttonPressed.setClickable(false);
                    blueRow2.setVisibility(View.INVISIBLE);
                    chooseOneLetter();
                } else if (buttonPressed.equals(buttonSongName)){
                    builder.cancel();
                    thirdBlock.setVisibility(View.VISIBLE);
                    buttonPressed.setClickable(false);
                    blueRow3.setVisibility(View.INVISIBLE);
                    textSongName.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            showInfo(R.string.commonText, 0);
                            blueRow6.setVisibility(View.VISIBLE);
                            blueRow7.setVisibility(View.VISIBLE);
                            showInfo(R.string.lastText, 6);
                        }
                    }, delay);
                }
            }
        });
        builder.show();
    }



    private void setTextViewAmountLetters(int id){
        String lettersAmount = "";
        String correctAnswer = "Мумий Тролль";
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

        amoutnLettes.setText(lettersAmount.trim());
    }


    private void chooseOneLetter() {

        String correctAnswer = "Мумий Тролль";
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
                        amoutnLettes.setVisibility(View.VISIBLE);
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                showInfo(R.string.thirdHelp, 3);
                            }
                        }, delay);

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

}

