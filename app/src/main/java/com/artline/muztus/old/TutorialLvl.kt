package com.artline.muztus.old;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.artline.muztus.R;
import com.artline.muztus.audio.MusicPlayerService;
import com.artline.muztus.audio.SoundsPlayerService;
import com.artline.muztus.commonFuncs.LevelsInfo;

import java.util.concurrent.LinkedBlockingQueue;

public class TutorialLvl extends AppCompatActivity {

    private final int delay = 450;
    private final String PREFERENCESSounds = "Preferences.sounds";
    private final String PREFERENCESPrizes = "Preferences.prizes";
    private final String PREFERENCESProgress = "Preferences.progress";
    private final LinkedBlockingQueue<Dialog> dialogsToShow = new LinkedBlockingQueue<>();
    private Handler handler;
    private Button buttonShowAmountLetters, buttonShowOneLetter, buttonSongName, buttonAnswerHelp;
    private Button buttonSayAnswer;
    private EditText editText;
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
    private SharedPreferences preferencesSounds;
    private boolean musicOff = false;
    private ImageView musicButton, soundsButton;
    private Toast toast1;
    private String correctAnswer;
    private String[] artistName;
    private Integer lvlPast = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_lvl);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        Intent intent = getIntent();
        musicOff = intent.getBooleanExtra("musicOff", false);

        artistName = new LevelsInfo().correctAnswersList[0][0];
        correctAnswer = artistName[0];

        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE);
        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE);
        preferencesSounds = getSharedPreferences(PREFERENCESSounds, MODE_PRIVATE);

        blueRow1 = findViewById(R.id.imageBlueRow1);
        blueRow2 = findViewById(R.id.imageBlueRow2);
        blueRow6 = findViewById(R.id.imageBlueRow6);
        blueRow7 = findViewById(R.id.imageBlueRow7);
        textViewCoins = findViewById(R.id.tutorialCoins);
        textViewStars = findViewById(R.id.tutorialStars);
        editText = findViewById(R.id.editTextTutorial);

        buttonShowAmountLetters = findViewById(R.id.buttonShowAmountLetters);
        buttonShowOneLetter = findViewById(R.id.buttonShowOneLetter);
        buttonSongName = findViewById(R.id.buttonSongName);
        buttonAnswerHelp = findViewById(R.id.buttonHelpAnswer);
        buttonSayAnswer = findViewById(R.id.buttonSayAnswer);

        amoutnLettes = findViewById(R.id.amoutnLettes);
        textSongName = findViewById(R.id.textSongName);

        musicButton = findViewById(R.id.tutorialMusic);
        soundsButton = findViewById(R.id.tutorialSounds);

        buttonShowAmountLetters.getWidth();
        blueRow1.setX(width / 4 - 170);
        blueRow2.setX((width / 2) - 40);
        // blueRow1.getLocationInWindow(test1);


        blueRow1.setBackgroundResource(R.drawable.row_anim_left);
        blueRow2.setBackgroundResource(R.drawable.row_anim_right);
        blueRow6.setBackgroundResource(R.drawable.row_anim_right);
        blueRow7.setBackgroundResource(R.drawable.row_anim_right);

        AnimationDrawable animationLeft = (AnimationDrawable) blueRow1.getBackground();
        AnimationDrawable animationRight = (AnimationDrawable) blueRow2.getBackground();
        AnimationDrawable animationSix = (AnimationDrawable) blueRow6.getBackground();
        AnimationDrawable animationSeven = (AnimationDrawable) blueRow7.getBackground();

        animationLeft.start();
        animationRight.start();
        animationSix.start();
        animationSeven.start();


        //   LinkedBlockingQueue<Dialog> dialogsToShow = new LinkedBlockingQueue<>();
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
                    buttonAnswerHelp.setEnabled(true);
//                    SharedPreferences.Editor editor = preferencesProgress.edit();
//                    editor.putInt("solved" + 0 + 0, 1);
//                    editor.apply();
                } else if (number == 5) {
                    blueRow2.setVisibility(View.INVISIBLE);
                    buttonSayAnswer.setEnabled(true);
                    editText.setClickable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.setFocusable(true);
                    editText.setFreezesText(false);
                    editText.setCursorVisible(true);
                    editText.setContextClickable(true);
                } else if (number == 6) {
                    showInfo(R.string.lastText, 7);
                    SharedPreferences.Editor editor = preferencesProgress.edit();
                    editor.putInt("solved" + 0 + 0, 1);
                    editor.apply();
                } else if (number == 7) {
                    finishTutorial();
                }
            }
        });
        showDialog(builder);
    }

    private void finishTutorial() {
        musicOff = true;
        SharedPreferences.Editor editor = preferencesProgress.edit();
        editor.putInt("solved" + 0 + 0, 1);
        editor.apply();
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

    public void onShowAnswer(View view) {
        useHelp(R.string.showAnswerHelp, buttonAnswerHelp);
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
                    textSongName.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            showInfo(R.string.commonText, 4);
                            blueRow6.setVisibility(View.VISIBLE);
                            blueRow7.setVisibility(View.VISIBLE);
                            // showInfo(R.string.lastText, 6);
                        }
                    }, delay);
                } else if (buttonPressed.equals(buttonAnswerHelp)) {
                    amoutnLettes.setText("М У М И Й  Т Р О Л Л Ь");
                    showInfo(R.string.lastText, 7);

                    builder.cancel();

                }
            }
        });
        builder.show();
    }

    public void onCheckAnswer(View view) {
        String answer = editText.getText().toString().trim().toLowerCase();        //берем ответ, переводим в строку, обрезаем пробелы и приводим к нижнему регистру
        for (String s : artistName) {                //перебор всех ответов
            if (answer.equals(s)) {
                lvlPast = 1;
                SoundsPlayerService.start(this, SoundsPlayerService.SOUND_WIN, preferencesSounds.getBoolean("soundsPlay", true));
                //если правильный ответ угадан
                amoutnLettes.setText("М У М И Й  Т Р О Л Л Ь");

                SharedPreferences.Editor progressEditor = preferencesProgress.edit();
                progressEditor.putInt("solved" + 0 + 0, 1);
                progressEditor.apply();
                showInfo(R.string.lastText, 7);
                break;
            }
        }
        if (answer.isEmpty()) {
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_WRONG_ANSWER, preferencesSounds.getBoolean("soundsPlay", true));
            toast1 = Toast.makeText(this, "Для начала нужно ввести хоть какой-то ответ!", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 50);
            toast1.show();
        } else if (lvlPast != 1) {
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_WRONG_ANSWER, preferencesSounds.getBoolean("soundsPlay", true));

            //если ответ неправильный, то сообщаем об этом
            toast1 = Toast.makeText(this, "Неправильный ответ!", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 50);
            toast1.show();
        }
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
        if (preferencesSounds.getBoolean("musicPlay", true)) {
            musicButton.setImageResource(R.drawable.button_music);
        } else {
            musicButton.setImageResource(R.drawable.buton_music_off);
        }

        if (preferencesSounds.getBoolean("soundsPlay", true)) {
            soundsButton.setImageResource(R.drawable.button_sound);
        } else {
            soundsButton.setImageResource(R.drawable.buton_sound_off);
        }
    }

    public void onMelody(View view) {
        SharedPreferences.Editor editor = preferencesSounds.edit();

        if (preferencesSounds.getBoolean("musicPlay", true)) {

            musicButton.setImageResource(R.drawable.buton_music_off);
            MusicPlayerService.pause();
            editor.putBoolean("musicPlay", false);

        } else {
            musicButton.setImageResource(R.drawable.button_music);
            MusicPlayerService.resume(this);
            editor.putBoolean("musicPlay", true);
        }
        editor.apply();
    }

    public void onSounds(View view) {
        SharedPreferences.Editor editor = preferencesSounds.edit();

        if (preferencesSounds.getBoolean("soundsPlay", true)) {
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_OFF_MUSIC, true);

            soundsButton.setImageResource(R.drawable.buton_sound_off);
            // MusicPlayerService.pause();
            editor.putBoolean("soundsPlay", false);

        } else {
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_ON_MUSIC, true);
            soundsButton.setImageResource(R.drawable.button_sound);
            //  MusicPlayerService.resume(this);
            editor.putBoolean("soundsPlay", true);
        }
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        musicOff = true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                musicOff = false;
                return false;
            case KeyEvent.KEYCODE_HOME:
                musicOff = false;
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!musicOff) {
            MusicPlayerService.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!musicOff) {
            if (preferencesSounds.getBoolean("musicPlay", true)) {
                MusicPlayerService.resume(this);
            }
        }
        musicOff = false;
    }
}

