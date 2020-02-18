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

    private final int helpLettersAmount = 150;
    private final int helpOneLetter = 400;
    private final int helpSongName = 250;
    private final int maxDuration = 30000;
    private final int priceOneStar = 100;
    private final int priceTwoStars = 200;
    private final int priceThreeStars = 300;
    private boolean correctAnswerBool = false;
    private boolean lvlPast = false;
    private boolean helpUsed = false;
    private int starsForLvl = 3;
    ImageView imageView;
    EditText editText;
    private String[] artistName;
    private TextView textViewAmountLetters, textViewCoins, textSongName;
    private String artistSong, correctAnswer;
    private Button buttonShowAmountLetters, buttonSongName, buttonShowOneLetter, buttonSayAnswer;
    private int lvlID, intCoinsWon, coins,artistPicture, coinsAtStart;            //переделать в short?
    private long start, lvlDuration ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl);


        setViews();							//ищем все вью
        setLvlInformation();				//получаем входную информацию
        lvlBuild();							//строим уровень по полученным данным (картинка, ответ, песня, состояние музыки и звуков, монеты)


        if (lvlPast){									//если уровень пройден
            toDoIfLvlPast();
        } else{
            start = System.currentTimeMillis();				//запускаем счетчик
        }

    }


    //проверка введенного ответа
    public void onCheckAnswer(View view) {

        String answer = editText.getText().toString().trim().toLowerCase();		//берем ответ, переводим в строку, обрезаем пробелы и приводим к нижнему регистру
        answer = answer.replaceAll("[ёЁ]", "е");					//меняем все ё на е

        for (String s : artistName) {                //перебор всех ответов
            if (answer.equals(s)) {
                long finish = System.currentTimeMillis();            //останавливаем счетчик
                lvlDuration = finish - start;
                correctAnswerBool = true;
                break;
            }
        }

        if (correctAnswerBool) {                        //если правильный ответ угадан
            final Dialog builder = new Dialog(this);
            builder.setCanceledOnTouchOutside(false);
            builder.setContentView(R.layout.layout_end_lvl);

            ImageView imageStar1 = builder.findViewById(R.id.imageStar1);            //звезды оценки
            ImageView imageStar2 = builder.findViewById(R.id.imageStar2);
            ImageView imageStar3 = builder.findViewById(R.id.imageStar3);
            TextView textCoinsWonAtFish = builder.findViewById(R.id.textCoinsWonAtFish);
            Button buttonOk = builder.findViewById(R.id.buttonFinishLvl);

            if (helpUsed) {                        //минус звезда за использование подсказок
                starsForLvl -= 1;
            }
            if (lvlDuration > maxDuration) {            //минус звезда за время
                starsForLvl -= 1;
            }

            switch (starsForLvl) {                    //проверяем сколько звезд мы выиграли и монет заработали
                case (1):
                    imageStar1.setImageResource(R.drawable.zvezda1_prizovogo_okna);
                    intCoinsWon = priceOneStar;
                    textCoinsWonAtFish.setText(R.string.OneStar);
                    break;
                case (2):
                    imageStar1.setImageResource(R.drawable.zvezda1_prizovogo_okna);
                    imageStar2.setImageResource(R.drawable.zvezda2_prizovogo_okna);
                    intCoinsWon = priceTwoStars;
                    textCoinsWonAtFish.setText(R.string.TwoStars);
                    break;
                case (3):
                    imageStar1.setImageResource(R.drawable.zvezda1_prizovogo_okna);
                    imageStar2.setImageResource(R.drawable.zvezda2_prizovogo_okna);
                    imageStar3.setImageResource(R.drawable.zvezda3_prizovogo_okna);
                    intCoinsWon = priceThreeStars;
                    textCoinsWonAtFish.setText(R.string.ThreeStars);
                    break;
            }


            buttonOk.setOnClickListener(new View.OnClickListener() {                //заканчиваем уровень после нажатия ОК
                @Override
                public void onClick(View v) {
                    finishLvl();
                }
            });

            builder.show();
        }

        else {														//если ответ неправильный, то сообщаем об этом
            Toast toast1 = Toast.makeText(this, "Неправильный ответ!", Toast.LENGTH_LONG);
            toast1.setGravity(Gravity.CENTER, 0, 50);
            toast1.show();
        }
    }

    public void onHelpUse(View view) {
        int message = 0;
        Button button = null;

        switch (view.getId()){
            case (R.id.buttonShowAmountLetters):
                setTextViewAmountLetters(-1);
                message	= R.string.showLettersAmount;
                button = buttonShowAmountLetters;
                break;
            case (R.id.buttonShowOneLetter):
                message	= R.string.showOneLetter;
                button = buttonShowOneLetter;
                break;
            case (R.id.buttonSongName):
                message	= R.string.showSongName;
                button = buttonSongName;
                break;
        }
        useHelp(message, button);
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


    private void helpButtonYes(Button buttonPressed){
        //подсказка первая
        if (buttonPressed.equals(buttonShowAmountLetters)) {
            if (coinsCalc(helpLettersAmount)){							//проверка достатка монет
                buttonPressed.setBackground(Lvl1.this.getDrawable(R.mipmap.podskazka_kolichestvo_bukv_zakrita));
                textViewAmountLetters.setVisibility(View.VISIBLE);
            }
        }

        //подсказка вторая
        else if (buttonPressed.equals(buttonShowOneLetter)) {
            if (coinsCalc(helpOneLetter)){							//проверка достатка монет
                buttonPressed.setBackground(Lvl1.this.getDrawable(R.mipmap.podskazka_lubay_bukva_zakrita));
                chooseOneLetter();
                buttonShowAmountLetters.setClickable(false);
                buttonShowAmountLetters.setBackground(Lvl1.this.getDrawable(R.mipmap.podskazka_kolichestvo_bukv_zakrita));
            }
        }

        //подсказка третья
        else if (buttonPressed.equals(buttonSongName)) {
            if (coinsCalc(helpSongName)){							//проверка достатка монет
                buttonPressed.setBackground(Lvl1.this.getDrawable(R.mipmap.podskazka_albom_pesny_zakrita));
                textSongName.setVisibility(View.VISIBLE);
            }
        }
    }


    private void chooseOneLetter() {

        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.show_one_letter);
        LinearLayout linearLayout = builder.findViewById(R.id.layoutHelpShowOneLetter);

        for (int i = 0; i < correctAnswer.length(); i++) {
            ImageView imageView = new ImageView(this);

            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setMargins(1,50,1,50);
            imageView.setLayoutParams(layout);

            if (!String.valueOf(correctAnswer.charAt(i)).equals(" ")) {
                imageView.setImageResource(R.drawable.button_letter);
                imageView.setId(i);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.cancel();
                        setTextViewAmountLetters(v.getId());
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

    // задаем тект для подсказки по количеству буков и выбранной букве
    private void setTextViewAmountLetters(int id){
        StringBuilder lettersAmount = new StringBuilder();
        String choosenLetter = "";
        if (id >= 0) {
            choosenLetter = String.valueOf(correctAnswer.charAt(id)).toUpperCase();
        }
        for (int i = 0; i < correctAnswer.length(); i++) {
            if (i == id){
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
    private void finishLvl(){

        Intent intentLvlPast = new Intent();

        intentLvlPast.putExtra("coinsFromLvl", String.valueOf(coins + intCoinsWon));
        intentLvlPast.putExtra("lvlPast", true);
        intentLvlPast.putExtra("lvlDuration", lvlDuration);
        intentLvlPast.putExtra("lvlID", lvlID);

        setResult(RESULT_OK, intentLvlPast);
        this.finish();

/*        						//передаем длительность прохождения уровня


        */

    }






    private boolean coinsCalc(int helpPrice){


        if (coins - helpPrice >= 0){
            coins -= helpPrice;
            textViewCoins.setText(String.valueOf(coins));
            return true;
        } else {
            Toast toast = Toast.makeText(Lvl1.this, "К сожалению у вас недостаточно монет", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,350);
            toast.show();
            return false;
        }
    }


    private void setViews(){

        imageView = findViewById(R.id.imageViewArtist);
        editText = findViewById(R.id.editTextTipedAnswer);
        textViewAmountLetters = findViewById(R.id.textViewAmoutnLettes);
        textViewCoins = findViewById(R.id.textViewCoins);
        buttonShowAmountLetters = findViewById(R.id.buttonShowAmountLetters);
        buttonShowOneLetter = findViewById(R.id.buttonShowOneLetter);
        buttonSongName = findViewById(R.id.buttonSongName);
        buttonSayAnswer = findViewById(R.id.buttonSayAnswer);
        textSongName = findViewById(R.id.textSongName);
    }


    private void setLvlInformation(){

        Intent newLvl = getIntent();

        lvlID = newLvl.getIntExtra("lvlID", 0);						//ID уровня
        artistPicture = newLvl.getIntExtra("artistPicture", 0);                 //получение изображения артиста
        artistName = newLvl.getStringArrayExtra("artistName");          //Получение массива правильных ответов
        correctAnswer = artistName[0];
        //Правильный ответ
        artistSong = newLvl.getStringExtra("artistSong");				//получение названия песни
        lvlPast = newLvl.getBooleanExtra("lvlPast", false);				//получение информации о факте прохождения песни
        coins = newLvl.getIntExtra("coins", 0);                                    //получение количества монет
        coinsAtStart = newLvl.getIntExtra("coins", 0);                                    //получение количества монет на старте
    }
    private void lvlBuild(){
        textViewCoins.setText(String.valueOf(coins));								//присвоение вьюхе монет
        textSongName.setText(artistSong);							//присвоение вьюхе песни
        imageView.setImageResource(artistPicture);								//присвоение вьюхе картинки артиста

    }

    private void toDoIfLvlPast(){
        buttonShowAmountLetters.setClickable(false);		//блокируем подсказки и кнопку ответа
        buttonShowOneLetter.setClickable(false);
        buttonSongName.setClickable(false);
        buttonSayAnswer.setClickable(false);

        editText.setClickable(false);				//блокируем эдит текст
        editText.setCursorVisible(false);
        editText.setEnabled(false);

        StringBuilder text = new StringBuilder();								// присваиваем и открываем ответ
        for (int i = 0; i < correctAnswer.length(); i++ ){
            text.append(correctAnswer.charAt(i)).append(" ");
        }
        textViewAmountLetters.setText(text.toString().trim().toUpperCase());
        textViewAmountLetters.setVisibility(View.VISIBLE);

        textSongName.setVisibility(View.VISIBLE);				//открываем название песни
    }
}
