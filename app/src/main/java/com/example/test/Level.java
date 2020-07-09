package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.test.audio.MusicPlayerService;
import com.example.test.audio.SoundsPlayerService;
import com.example.test.commonFuncs.LevelsInfo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class Level extends AppCompatActivity implements RewardedVideoAdListener {

    private final int helpLettersAmount = 100;
    private final int helpOneLetter = 250;
    private final int helpSongName = 350;
    private final int helpAnswer = 500;

    private final int maxDuration = 7000;

    private int coinsWinAmount = 0;
    private int starsAmount = 0;


    private Integer lvlPast = 0;
    private int[] usedHelpsList = {0, 0, 0, 0};
    private Integer helpPrice = 0;
    private ImageView imageView;
    private ImageView freeCoinsImage;
    private EditText editText;
    private String[] artistName;
    private TextView textViewAmountLetters, textViewCoins, textViewStars, textSongName;
    private String artistSong, correctAnswer;
    private Button buttonShowAmountLetters, buttonSongName, buttonShowOneLetter, buttonHelpAnswer, buttonSayAnswer;
    private int lvlID,  coins, stars, artistPicture, lvlPremia;            //переделать в short?
    private long start, lvlDuration;

    private SharedPreferences preferencesProgress;
    private SharedPreferences preferencesPrizes;
    private SharedPreferences preferencesSounds;
    private final String PREFERENCESSounds = "Preferences.sounds";
    private final String PREFERENCESProgress = "Preferences.progress";
    private final String PREFERENCESPrizes = "Preferences.prizes";

    private Toast toast1;
    private Button[] buttonsList;
    private RewardedVideoAd mRewardedVideoAd;
    private ImageView musicButton, soundsButton;
    private boolean musicOff = false;


   //String freeID = "ca-app-pub-8364051315582457/5955782184";

   // test
   String freeID =  "ca-app-pub-3940256099942544/5224354917";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl);

        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE);
        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE);
        preferencesSounds =  getSharedPreferences(PREFERENCESSounds, MODE_PRIVATE);

        if (preferencesPrizes.getInt("freeCoinsCounter", 0) >= 5) {
            SharedPreferences.Editor prizesEditor = preferencesPrizes.edit();
            prizesEditor.putInt("freeCoinsCounter", 0);
            prizesEditor.apply();
        }

        setViews();                            //ищем все вью
        setLvlInformation();                //получаем входную информацию
        lvlPast = preferencesProgress.getInt("solved" + lvlPremia + lvlID, 0);

        for (int i = 0; i < usedHelpsList.length; i++) {
            Integer helpUsed = preferencesProgress.getInt("helpUsed" + lvlPremia + lvlID + i, 0);
            usedHelpsList[i] = helpUsed;
        }
        lvlBuild();                            //строим уровень по полученным данным (картинка, ответ, песня, состояние музыки и звуков, монеты)

        if (lvlPast == 1) {                                    //если уровень пройден
            toDoIfLvlPast();
        } else {
            start = System.currentTimeMillis();                //запускаем счетчик

        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener( this);
        loadRewardedVideoAd();


    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = preferencesProgress.edit();
        editor.putInt("solved" + lvlPremia + lvlID, lvlPast);
        editor.apply();
        finishLvl();

    }




    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
        if (!musicOff) {
            if (preferencesSounds.getBoolean("musicPlay", true)) {
                MusicPlayerService.resume(this);
            }
        }
        musicOff = false;
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
        if (!musicOff){
            MusicPlayerService.pause();
        }
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    //проверка введенного ответа
    public void onCheckAnswer(View view) {

        String answer = editText.getText().toString().trim().toLowerCase();        //берем ответ, переводим в строку, обрезаем пробелы и приводим к нижнему регистру
        answer = answer.replaceAll("[ёЁ]", "е");                    //меняем все ё на е

        for (String s : artistName) {                //перебор всех ответов
            if (answer.equals(s)) {
                long finish = System.currentTimeMillis();            //останавливаем счетчик
                lvlDuration = finish - start;
                lvlPast = 1;
                SoundsPlayerService.start(this, SoundsPlayerService.SOUND_WIN,preferencesSounds.getBoolean("soundsPlay", true));

                //если правильный ответ угадан
                final Dialog builder = new Dialog(this);
                builder.setCanceledOnTouchOutside(false);
                builder.setContentView(R.layout.layout_end_lvl);

                ImageView imageStar1 = builder.findViewById(R.id.imageStar1);            //звезды оценки
                ImageView imageStar2 = builder.findViewById(R.id.imageStar2);
                ImageView imageStar3 = builder.findViewById(R.id.imageStar3);
                ImageView[] imageViews = {imageStar1, imageStar2, imageStar3};
                Integer[] starsList = {R.drawable.zvezda1_prizovogo_okna, R.drawable.zvezda2_prizovogo_okna, R.drawable.zvezda3_prizovogo_okna};

                TextView textCoinsWonAtFish = builder.findViewById(R.id.textCoinsWonAtFish);
                Button buttonOk = builder.findViewById(R.id.buttonFinishLvl);

                for (int i = 0; i < helpsUsedAmount(); i++) {
                    imageViews[i].setImageResource(starsList[i]);
                    coinsWinAmount += 10;
                    starsAmount += 1;

                }

                coinsWinAmount = coinsWinAmount == 0 ? 5 : coinsWinAmount;
                int mult = 1;
                if (lvlDuration < maxDuration) {
                    mult = 2;
                    textCoinsWonAtFish.setText(coinsWinAmount + " x2");
                } else {
                    textCoinsWonAtFish.setText(String.valueOf(coinsWinAmount));

                }
                textViewCoins.setText(String.valueOf(coins + coinsWinAmount * mult));
                textViewStars.setText(String.valueOf(stars + starsAmount));

                SharedPreferences.Editor progressEditor = preferencesProgress.edit();
                progressEditor.putInt("solved" + lvlPremia + lvlID, lvlPast);
                progressEditor.putLong("lvlDuration" + lvlPremia + lvlID, lvlDuration);
                progressEditor.apply();

                SharedPreferences.Editor prizesEditor = preferencesPrizes.edit();
                prizesEditor.putInt("coins", coins + coinsWinAmount * mult);
                prizesEditor.putInt("stars", stars + starsAmount);
                if (preferencesPrizes.getInt("freeCoinsCounter", 0) > 0 ){
                    prizesEditor.putInt("freeCoinsCounter", preferencesPrizes.getInt("freeCoinsCounter", 0) + 1);

                }
                prizesEditor.apply();

                buttonOk.setOnClickListener(new View.OnClickListener() {                //заканчиваем уровень после нажатия ОК
                    @Override
                    public void onClick(View v) {
                        builder.cancel();
                        finishLvl();
                    }
                });

                builder.show();
                break;
            }
        }
        if (answer.isEmpty()) {
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_WRONG_ANSWER,preferencesSounds.getBoolean("soundsPlay", true));

            toast1 = Toast.makeText(this, "Для начала нужно ввести хоть какой-то ответ!", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 50);
            toast1.show();
        } else if (lvlPast != 1) {
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_WRONG_ANSWER,preferencesSounds.getBoolean("soundsPlay", true));

            //если ответ неправильный, то сообщаем об этом
            toast1 = Toast.makeText(this, "Неправильный ответ!", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 50);
            toast1.show();
        }
    }

    private Integer helpsUsedAmount() {
        int amount = 3;
        for (int i = 0; i < usedHelpsList.length - 1; i++) {
            if (usedHelpsList[i] > 0) {
                amount -= 1;
            }
        }
        return amount;
    }

    public void onHelpUse(View view) {
        SoundsPlayerService.start(this, SoundsPlayerService.SOUND_APPEAR_VIEW,preferencesSounds.getBoolean("soundsPlay", true));

        int message = 0;
        Button button = null;
        if (coinsCalc(helpPrice(view.getId()))) {
            switch (view.getId()) {
                case (R.id.buttonShowAmountLetters):
                    setTextViewAmountLetters(-1);
                    message = R.string.showLettersAmount;
                    button = buttonShowAmountLetters;
                    break;
                case (R.id.buttonShowOneLetter):
                    message = R.string.showOneLetter;
                    button = buttonShowOneLetter;
                    break;
                case (R.id.buttonSongName):
                    message = R.string.showSongName;
                    button = buttonSongName;
                    break;
                case (R.id.buttonHelpAnswer):
                    message = R.string.showAnswerHelp;
                    button = buttonHelpAnswer;
                    break;
            }
            useHelp(message, button);
        }
    }


    private Integer helpPrice(Integer helpId) {
        switch (helpId) {
            case (R.id.buttonShowAmountLetters):
                helpPrice = helpLettersAmount;
                break;
            case (R.id.buttonShowOneLetter):
                helpPrice = helpOneLetter;
                break;
            case (R.id.buttonSongName):
                helpPrice = helpSongName;
                break;
            case (R.id.buttonHelpAnswer):
                helpPrice = helpAnswer;
                break;
        }
        return helpPrice;
    }


    private void useHelp(final int message, final Button buttonPressed) {
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
                buttonPressed.setClickable(false);
                builder.cancel();
                helpButtonYes(buttonPressed);
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


    private void helpButtonYes(Button buttonPressed) {
        //подсказка первая
        if (buttonPressed.equals(buttonShowAmountLetters)) {
            buttonPressed.setEnabled(false);
            textViewAmountLetters.setVisibility(View.VISIBLE);
            CoinsChange();
            saveHelpUsed(0);
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_SPEND_MONEY,preferencesSounds.getBoolean("soundsPlay", true));

        }

        //подсказка вторая
        else if (buttonPressed.equals(buttonShowOneLetter)) {
            chooseOneLetter();

        }

        //подсказка третья
        else if (buttonPressed.equals(buttonSongName)) {
            buttonPressed.setEnabled(false);
            textSongName.setVisibility(View.VISIBLE);
            saveHelpUsed(2);
            CoinsChange();
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_SPEND_MONEY,preferencesSounds.getBoolean("soundsPlay", true));

        }

        //подсказка четвертая
        else if (buttonPressed.equals(buttonHelpAnswer)) {
            lvlPast = 1;
            SharedPreferences.Editor editor = preferencesProgress.edit();
            editor.putInt("solved" + lvlPremia + lvlID, lvlPast);
            editor.apply();

            SharedPreferences.Editor prizesEditor = preferencesPrizes.edit();
            prizesEditor.putInt("freeCoinsCounter", 1);
            prizesEditor.apply();

            toDoIfLvlPast();
            saveHelpUsed(3);
            CoinsChange();
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_GAMEOVER,preferencesSounds.getBoolean("soundsPlay", true));

        }
    }

    private void saveHelpUsed(Integer helpID) {
        usedHelpsList[helpID] = 1;
        SharedPreferences.Editor editor = preferencesProgress.edit();
        editor.putInt("helpUsed" + lvlPremia + lvlID + helpID, 1);
        editor.apply();
    }

    private void CoinsChange() {
        final Handler handler = new Handler();
        final int[] firstNum = {coins};
        final int secondNum = coins - helpPrice;
        //int step = 1;
        handler.post(new Runnable() {
            @Override
            public void run() {
                textViewCoins.setText(String.valueOf(firstNum[0]));

                if (firstNum[0] >= secondNum+4){
                    firstNum[0] -= 5;
                }
                handler.postDelayed(this, (long) 0.0001);
            }
        });

        coins -= helpPrice;
        SharedPreferences.Editor editor = preferencesPrizes.edit();
        editor.putInt("coins", coins);
        editor.apply();
    }


    private void chooseOneLetter() {

        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.show_one_letter);
        LinearLayout linearLayout = builder.findViewById(R.id.layoutHelpShowOneLetter);

        for (int i = 0; i < correctAnswer.length(); i++) {
            ImageView imageView = new ImageView(this);

            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setMargins(1, 50, 1, 50);
            layout.weight = 1;
            imageView.setLayoutParams(layout);

            if (!String.valueOf(correctAnswer.charAt(i)).equals(" ")) {
                imageView.setImageResource(R.drawable.button_letter);
                imageView.setId(i);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SoundsPlayerService.start(Level.this, SoundsPlayerService.SOUND_SPEND_MONEY,preferencesSounds.getBoolean("soundsPlay", true));

                        buttonShowOneLetter.setEnabled(false);
                        buttonShowAmountLetters.setEnabled(false);
                        builder.cancel();
                        CoinsChange();
                        saveHelpUsed(1);
                        setTextViewAmountLetters(v.getId());
                        textViewAmountLetters.setVisibility(View.VISIBLE);

                    }
                });
            } else {
                imageView.setImageResource(R.mipmap.vybor_bukvy_podskazki_space);
            }
            linearLayout.addView(imageView);
        }
        builder.show();

    }

    // задаем тект для подсказки по количеству букв и выбранной букве
    private void setTextViewAmountLetters(int id) {
        SharedPreferences.Editor editor = preferencesProgress.edit();
        editor.putInt("choosenLetterID" + lvlPremia + lvlID, id);
        editor.apply();

        StringBuilder lettersAmount = new StringBuilder();
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
        textViewAmountLetters.setText(lettersAmount.toString().trim());
    }


    //закрываем уровень
    private void finishLvl() {

        Intent intentLvlPast = new Intent();
        Intent intentLvlExited = new Intent();
        intentLvlPast.putExtra("lvlID", lvlID);

        intentLvlExited.putExtra("lvlID", lvlID);
        setResult(RESULT_OK, intentLvlPast);
        setResult(RESULT_CANCELED, intentLvlExited);
        this.finish();
    }


    private boolean coinsCalc(int helpPrice) {
        if (coins - helpPrice >= 0) {
            return true;
        } else {
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_WARNING_VIEW,preferencesSounds.getBoolean("soundsPlay", true));

            toast1 = Toast.makeText(Level.this, "К сожалению у Вас недостаточно монет. Для подсказки необходимо " + helpPrice + " монет.", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 350);
            toast1.show();
            return false;
        }
    }


    private void setViews() {

        imageView = findViewById(R.id.imageViewArtist);
        editText = findViewById(R.id.editTextTipedAnswer);
        textViewAmountLetters = findViewById(R.id.textViewAmoutnLettes);
        textViewCoins = findViewById(R.id.levelCoins);
        textViewStars = findViewById(R.id.levelStars);

        // hints
        buttonShowAmountLetters = findViewById(R.id.buttonShowAmountLetters);
        buttonShowOneLetter = findViewById(R.id.buttonShowOneLetter);
        buttonSongName = findViewById(R.id.buttonSongName);
        buttonHelpAnswer = findViewById(R.id.buttonHelpAnswer);

        buttonSayAnswer = findViewById(R.id.buttonSayAnswer);
        textSongName = findViewById(R.id.textSongName);
        freeCoinsImage = findViewById(R.id.freeCoins);
        musicButton = findViewById(R.id.levelMusic);
        soundsButton = findViewById(R.id.levelSounds);

        buttonsList = new Button[]{buttonShowAmountLetters, buttonShowOneLetter, buttonSongName, buttonHelpAnswer, buttonSayAnswer};

    }


    private void setLvlInformation() {

        Intent newLvl = getIntent();
        lvlID = newLvl.getIntExtra("position", 0);
        lvlPremia = newLvl.getIntExtra("premiaIDtoLVL", 0);

        artistPicture = new LevelsInfo().premiaImagesList[lvlPremia][lvlID];
        artistName = new LevelsInfo().correctAnswersList[lvlPremia][lvlID];
        correctAnswer = artistName[0];
        artistSong = new LevelsInfo().albomsList[lvlPremia][lvlID];
        coinsStarsUpDate();
        if (preferencesPrizes.getInt("freeCoinsCounter", 0) == 0) {
            freeCoinsImage.setVisibility(View.VISIBLE);
        } else {
            freeCoinsImage.setVisibility(View.INVISIBLE);
        }

        //Правильный ответ
        // lvlPast = newLvl.getBooleanExtra("lvlPast", false);                //получение информации о факте прохождения песни
    }

    private void lvlBuild() {
        textSongName.setText(artistSong);                            //присвоение вьюхе песни
        imageView.setImageResource(artistPicture);                                //присвоение вьюхе картинки артиста

        if (usedHelpsList[0] == 1) {
            textViewAmountLetters.setVisibility(View.VISIBLE);
            buttonShowAmountLetters.setEnabled(false);
        }
        if (usedHelpsList[1] == 1) {
            buttonShowAmountLetters.setEnabled(false);
            buttonShowOneLetter.setEnabled(false);
            textViewAmountLetters.setVisibility(View.VISIBLE);
            setTextViewAmountLetters(preferencesProgress.getInt("choosenLetterID" + lvlPremia + lvlID, -1));
        }
        if (usedHelpsList[2] == 1) {
            buttonSongName.setEnabled(false);
            textSongName.setVisibility(View.VISIBLE);
        }
    }

    private void toDoIfLvlPast() {

        for (Button b : buttonsList) {
            b.setEnabled(false);
        }

        editText.setClickable(false);                //блокируем эдит текст
        editText.setCursorVisible(false);
        editText.setEnabled(false);

        StringBuilder text = new StringBuilder();                                // присваиваем и открываем ответ
        for (int i = 0; i < correctAnswer.length(); i++) {
            text.append(correctAnswer.charAt(i)).append(" ");
        }
        textViewAmountLetters.setText(text.toString().trim().toUpperCase());
        textViewAmountLetters.setVisibility(View.VISIBLE);

        textSongName.setVisibility(View.VISIBLE);                //открываем название песни
    }

    private void coinsStarsUpDate() {
        coins = preferencesPrizes.getInt("coins", 0);
        stars = preferencesPrizes.getInt("stars", 0);
        textViewCoins.setText(String.valueOf(preferencesPrizes.getInt("coins", 0)));
        textViewStars.setText(String.valueOf(preferencesPrizes.getInt("stars", 0)));
        if (preferencesSounds.getBoolean("musicPlay", true)){
            musicButton.setImageResource(R.drawable.buton_music_on);
        } else {
            musicButton.setImageResource(R.drawable.buton_music_off);
        }

        if (preferencesSounds.getBoolean("soundsPlay", true)) {
            soundsButton.setImageResource(R.drawable.buton_sound_on);
        } else {
            soundsButton.setImageResource(R.drawable.buton_sound_off);
        }
    }

    public void onMelody(View view) {
        SharedPreferences.Editor editor = preferencesSounds.edit();

        if (preferencesSounds.getBoolean("musicPlay", true)){

            musicButton.setImageResource(R.drawable.buton_music_off);
            MusicPlayerService.pause();
            editor.putBoolean("musicPlay", false);

        } else {
            musicButton.setImageResource(R.drawable.buton_music_on);
            MusicPlayerService.resume(this);
            editor.putBoolean("musicPlay", true);
        }
        editor.apply();
    }

    public void onSounds(View view) {
        SharedPreferences.Editor editor = preferencesSounds.edit();

        if (preferencesSounds.getBoolean("soundsPlay", true)){
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_OFF_MUSIC,true);

            soundsButton.setImageResource(R.drawable.buton_sound_off);
            editor.putBoolean("soundsPlay", false);

        } else {
            soundsButton.setImageResource(R.drawable.buton_sound_on);
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_ON_MUSIC,true);
            editor.putBoolean("soundsPlay", true);
        }
        editor.apply();
    }


    public void onGetFreeCoins(View view) {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd( freeID,
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewarded(RewardItem reward) {
        SharedPreferences.Editor prizesEditor = preferencesPrizes.edit();
        prizesEditor.putInt("coins", coins + 150);
        prizesEditor.putInt("freeCoinsCounter", 1);
        prizesEditor.apply();
        coinsStarsUpDate();
        freeCoinsImage.setVisibility(View.INVISIBLE);
        SoundsPlayerService.start(this, SoundsPlayerService.SOUND_GOT_COINS,preferencesSounds.getBoolean("soundsPlay", true));

    }


    @Override
    public void onRewardedVideoAdLeftApplication() {
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
    }

    @Override
    public void onRewardedVideoAdLoaded() {
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoCompleted() {
    }

}
