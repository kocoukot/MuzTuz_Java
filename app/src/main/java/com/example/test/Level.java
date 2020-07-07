package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.commonFuncs.LevelsInfo;

import java.util.Calendar;

public class Level extends AppCompatActivity {

    private final int helpLettersAmount = 100;
    private final int helpOneLetter = 250;
    private final int helpSongName = 350;
    private final int helpAnswer = 500;

    private final int maxDuration = 7000;

    private int startWinAmount = 0;
    private int starsAmount = 0;


    private Integer lvlPast = 0;
    private int[] usedHelpsList = {0, 0, 0, 0};
    private boolean helpUsed = false;
    Integer helpPrice = 0;
    ImageView imageView;
    EditText editText;
    private String[] artistName;
    private TextView textViewAmountLetters, textViewCoins, textViewStars, textSongName;
    private String artistSong, correctAnswer;
    private Button buttonShowAmountLetters, buttonSongName, buttonShowOneLetter, buttonHelpAnswer, buttonSayAnswer;
    private int lvlID, intCoinsWon, coins, stars, artistPicture, coinsAtStart, lvlPremia;            //переделать в short?
    private long start, lvlDuration;

    SharedPreferences preferencesProgress, preferencesPrizes;

    final String PREFERENCESProgress = "Preferences.progress";
    final String PREFERENCESPrizes = "Preferences.prizes";

    private Toast toast1;
    Button[] buttonsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl);

        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE);
        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE);

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

    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = preferencesProgress.edit();
        editor.putInt("solved" + lvlPremia + lvlID, lvlPast);
        editor.apply();
        finishLvl();

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
                    startWinAmount += 10;
                    starsAmount += 1;

                }
                startWinAmount = startWinAmount == 0 ? 5 : startWinAmount;

                textCoinsWonAtFish.setText(String.valueOf(startWinAmount));
                textViewCoins.setText(String.valueOf(coins + startWinAmount));
                textViewStars.setText(String.valueOf(stars + starsAmount));

                SharedPreferences.Editor progressEditor = preferencesProgress.edit();
                progressEditor.putInt("solved" + lvlPremia + lvlID, lvlPast);
                progressEditor.putLong("lvlDuration" + lvlPremia + lvlID, lvlDuration);
                progressEditor.apply();

                SharedPreferences.Editor prizesEditor = preferencesPrizes.edit();
                prizesEditor.putInt("coins", coins + startWinAmount);
                prizesEditor.putInt("stars", stars + starsAmount);
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
            toast1 = Toast.makeText(this, "Для начала нужно ввести хоть какой-то ответ!", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 50);
            toast1.show();
        } else if (lvlPast != 1) {                                                        //если ответ неправильный, то сообщаем об этом
            toast1 = Toast.makeText(this, "Неправильный ответ!", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 50);
            toast1.show();
        }
    }

    private Integer helpsUsedAmount() {
        Integer amount = 3;
        for (int i = 0; i < usedHelpsList.length - 1; i++) {
            if (usedHelpsList[i] > 0) {
                amount -= 1;
            }
        }
        return amount;
    }

    public void onHelpUse(View view) {
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
        }

        //подсказка четвертая
        else if (buttonPressed.equals(buttonHelpAnswer)) {
            lvlPast = 1;
            SharedPreferences.Editor editor = preferencesProgress.edit();
            editor.putInt("solved" + lvlPremia + lvlID, lvlPast);
            editor.apply();
            toDoIfLvlPast();
            saveHelpUsed(3);
            CoinsChange();
        }
    }

    private void saveHelpUsed(Integer helpID) {
        usedHelpsList[helpID] = 1;
        SharedPreferences.Editor editor = preferencesProgress.edit();
        editor.putInt("helpUsed" + lvlPremia + lvlID + helpID, 1);
        editor.apply();
    }

    private void CoinsChange() {
        coins -= helpPrice;
        SharedPreferences.Editor editor = preferencesPrizes.edit();
        editor.putInt("coins", coins);
        editor.apply();
        textViewCoins.setText(String.valueOf(coins));
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

        //intentLvlPast.putExtra("lvlDuration", lvlDuration);
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
    }

    private void showToast(String ms) {
        toast1 = Toast.makeText(this, ms, Toast.LENGTH_SHORT);
        toast1.setGravity(Gravity.CENTER, 0, 50);
        toast1.show();
    }
}
