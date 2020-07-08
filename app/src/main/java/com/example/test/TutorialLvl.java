package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.LinkedBlockingQueue;

public class TutorialLvl extends AppCompatActivity {

    private Handler handler;
    private Button buttonShowAmountLetters;
    private Button buttonShowOneLetter;
    private Button buttonSongName;

    private int delay = 450;
    private int width;
    private int idButton;
    private ImageView blueRow1;
    private ImageView blueRow2;

    private ImageView blueRow6;
    private ImageView blueRow7;
    private TextView textSongName;
    private TextView amoutnLettes;
    private TextView textViewCoins;
    private TextView textViewStars;

    private SharedPreferences preferencesProgress;
    private SharedPreferences preferencesPrizes;

    private final String PREFERENCESPrizes = "Preferences.prizes";
    private final String PREFERENCESProgress = "Preferences.progress";



    private LinkedBlockingQueue<Dialog> dialogsToShow = new LinkedBlockingQueue<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_lvl);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE);
        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE);

        blueRow1 = findViewById(R.id.imageBlueRow1);
        blueRow2 = findViewById(R.id.imageBlueRow2);
        blueRow6 = findViewById(R.id.imageBlueRow6);
        blueRow7 = findViewById(R.id.imageBlueRow7);
        textViewCoins = findViewById(R.id.tutorialCoins);
        textViewStars = findViewById(R.id.tutorialStars);

        buttonShowAmountLetters = findViewById(R.id.buttonShowAmountLetters);
        buttonShowOneLetter = findViewById(R.id.buttonShowOneLetter);
        buttonSongName = findViewById(R.id.buttonSongName);

        amoutnLettes = findViewById(R.id.amoutnLettes);
        textSongName = findViewById(R.id.textSongName);

        buttonShowAmountLetters.getWidth();
        blueRow1.setX(width / 4 - 170);
        blueRow2.setX((width / 2) - 40);
        // blueRow1.getLocationInWindow(test1);


        //  LinkedBlockingQueue<Dialog> dialogsToShow = new LinkedBlockingQueue<>();
        coinsStarsUpDate();
        showInfo(R.string.helloFriend, 0);
        showInfo(R.string.firstHelp, 0);
        showInfo(R.string.secondHelp1, 1);


    }

    private void showDialog(final Dialog dialog) {
        if (dialogsToShow.isEmpty()) {
            dialog.show();
        }
        dialogsToShow.offer(dialog);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface d) {
                dialogsToShow.remove(dialog);
                if (!dialogsToShow.isEmpty()) {
                    if (dialogsToShow.peek() != null) {
                        dialogsToShow.peek().show();
                    }
                }
            }
        });
    }


    private void showInfo(int message, final int number) {

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
                if (number == 1) {
                    buttonShowAmountLetters.setEnabled(true);
                    blueRow1.setVisibility(View.VISIBLE);
                } else if (number == 2) {
                    buttonShowOneLetter.setEnabled(true);
                    blueRow1.setX(width / 4 + 150);
                    blueRow1.setVisibility(View.VISIBLE);
                } else if (number == 3) {
                    buttonSongName.setEnabled(true);
                    blueRow2.setVisibility(View.VISIBLE);
                } else if (number == 4) {
                    blueRow6.setVisibility(View.INVISIBLE);
                    blueRow7.setVisibility(View.INVISIBLE);
                    blueRow2.setVisibility(View.VISIBLE);
                    blueRow2.setX(width / 2 + 200);
                    showInfo(R.string.answerHelp, 5);
                    SharedPreferences.Editor editor = preferencesProgress.edit();
                    editor.putInt("solved" + 0 + 0, 1);
                    editor.apply();
                } else if (number == 5) {
                    showInfo(R.string.lastText, 6);
                } else if (number == 6) {
                    finishTutorial();
                }
            }
        });
        showDialog(builder);
    }

    private void finishTutorial() {

        Intent intent_finish = new Intent();
        intent_finish.putExtra("key", 100);
        setResult(RESULT_OK, intent_finish);
        TutorialLvl.this.finish();
    }

    public void onShowAmountLetters(View view) {
        useHelp(R.string.showLettersAmount, buttonShowAmountLetters);
    }

    public void onShowOneLetter(View view) {
        useHelp(R.string.showOneLetter, buttonShowOneLetter);
    }

    public void onShowSongName(View view) {
        useHelp(R.string.showSongName, buttonSongName);
    }


    private void useHelp(int message, final Button buttonPressed) {
        handler = new Handler();
        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.help);

        TextView text = builder.findViewById(R.id.textInform);
        final Button buttonYes = builder.findViewById(R.id.buttonHelpYes);
        final Button buttonNo = builder.findViewById(R.id.buttonHelpNo);
        buttonNo.setEnabled(false);

        text.setText(message);

        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.67f);
        layout.setMargins(50, 50, 50, 50);
        text.setLayoutParams(layout);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonPressed.equals(buttonShowAmountLetters)) {
                    builder.cancel();
                    buttonPressed.setEnabled(false);
                    setTextViewAmountLetters(-1);
                    amoutnLettes.setVisibility(View.VISIBLE);
                    blueRow1.setVisibility(View.INVISIBLE);

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            showInfo(R.string.secondHelp2, 2);
                        }
                    }, delay);

                } else if (buttonPressed.equals(buttonShowOneLetter)) {
                    builder.cancel();
                    buttonPressed.setEnabled(false);
                    blueRow1.setVisibility(View.INVISIBLE);
                    chooseOneLetter();
                } else if (buttonPressed.equals(buttonSongName)) {
                    builder.cancel();
                    buttonPressed.setEnabled(false);
                    blueRow2.setVisibility(View.INVISIBLE);
                    // blueRow3.setVisibility(View.INVISIBLE);
                    textSongName.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            showInfo(R.string.commonText, 4);
                            blueRow6.setVisibility(View.VISIBLE);
                            blueRow7.setVisibility(View.VISIBLE);
                            // showInfo(R.string.lastText, 6);
                        }
                    }, delay);
                }
            }
        });
        builder.show();
    }


    private void setTextViewAmountLetters(int id) {
        StringBuilder lettersAmount = new StringBuilder();
        String correctAnswer = "Мумий Тролль";
        String choosenLetter = "";
        if (id >= 0) {
            choosenLetter = String.valueOf(correctAnswer.charAt(id)).toUpperCase();
        }
        for (int i = 0; i < correctAnswer.length(); i++) {
            if (i == id) {
                lettersAmount.append(choosenLetter).append(" ");
            } else {
                if (!String.valueOf(correctAnswer.charAt(i)).equals(" ")) {
                    lettersAmount.append("_ ");
                } else {
                    lettersAmount.append("   ");
                }
            }
        }

        amoutnLettes.setText(lettersAmount.toString().trim());
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
            layout.setMargins(0, 50, 0, 50);
            imageView.setLayoutParams(layout);

            if (!String.valueOf(correctAnswer.charAt(i)).equals(" ")) {
                imageView.setImageResource(R.drawable.button_letter);
                imageView.setId(i);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        idButton = v.getId();
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
            } else {
                imageView.setImageResource(R.mipmap.vybor_bukvy_podskazki_space);
            }
            linearLayout.addView(imageView);
        }
        builder.show();
    }

    private void coinsStarsUpDate() {
        textViewCoins.setText(String.valueOf(preferencesPrizes.getInt("coins", 0)));
        textViewStars.setText(String.valueOf(preferencesPrizes.getInt("stars", 0)));
    }
}

