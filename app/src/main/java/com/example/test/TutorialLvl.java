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
    Button buttonShowAmountLetters, buttonShowOneLetter, buttonSongName, buttonFriendHelp, buttonAboutHelp;
    Button buttonSayAnswer;
    ImageView firstBlock, secondBlock, thirdBlock;
    int delay = 450;
    ImageView blueRow1,blueRow2,blueRow3, blueRow4, blueRow5, blueRow6, blueRow7 ;
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
        blueRow4 = findViewById(R.id.imageBlueRow4);
        blueRow5 = findViewById(R.id.imageBlueRow5);
        blueRow6 = findViewById(R.id.imageBlueRow6);
        blueRow7 = findViewById(R.id.imageBlueRow7);

        buttonShowAmountLetters = findViewById(R.id.buttonShowAmountLetters);
        buttonShowAmountLetters.setClickable(false);

        buttonShowOneLetter = findViewById(R.id.buttonShowOneLetter);
        buttonShowOneLetter.setClickable(false);

        buttonSongName = findViewById(R.id.buttonSongName);
        buttonSongName.setClickable(false);

        buttonFriendHelp = findViewById(R.id.buttonFriendHelp);
        buttonFriendHelp.setClickable(false);

        buttonAboutHelp = findViewById(R.id.buttonAboutHelp);
        buttonAboutHelp.setClickable(false);

        buttonSayAnswer = findViewById(R.id.buttonSayAnswer);
        buttonAboutHelp.setClickable(false);

        amoutnLettes = findViewById(R.id.amoutnLettes);
        firstBlock = findViewById(R.id.fistBlock);
        secondBlock = findViewById(R.id.secodBlock);
        thirdBlock = findViewById(R.id.thirdBlock);
        textSongName = findViewById(R.id.textSongName);




       LinkedBlockingQueue<Dialog> dialogsToShow = new LinkedBlockingQueue<>();

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
                else if (number == 4){
                    buttonFriendHelp.setClickable(true);
                    buttonFriendHelp.setBackground(TutorialLvl.this.getDrawable(R.mipmap.podskazka_pomoshh_druga));
                    blueRow4.setVisibility(View.VISIBLE);
                }else if (number == 5){
                    buttonAboutHelp.setClickable(true);
                    buttonAboutHelp.setBackground(TutorialLvl.this.getDrawable(R.mipmap.podskazka_podskazok));
                    blueRow5.setVisibility(View.VISIBLE);
                } else if(number == 6){
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

    public void onShowAmountLetters(View view) {

        useHelp(R.string.showLettersAmount,buttonShowAmountLetters );
    }

    public void onShowOneLetter(View view) {
        useHelp(R.string.showOneLetter,buttonShowOneLetter );
    }

    public void onShowSongName(View view) {
        useHelp(R.string.showSongName,buttonSongName );
    }


    public void onFriendHelp(View view) {
        useHelp(R.string.showFriendHelp,buttonFriendHelp );
    }

    public void onHelpAboutHelp(View view) {
        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.information);
        final Button buttonInformation = builder.findViewById(R.id.buttonInformation);
        buttonInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
                blueRow5.setVisibility(View.INVISIBLE);
                blueRow6.setVisibility(View.VISIBLE);
                blueRow7.setVisibility(View.VISIBLE);
                buttonSayAnswer.setBackground(TutorialLvl.this.getDrawable(R.mipmap.vvod_galka));
                showInfo(R.string.commonText, 0);
                showInfo(R.string.lastText, 6);
            }
        });
        builder.show();
    }



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
                            showInfo(R.string.friendHelp, 4);
                        }
                    }, delay);
                }
                else if (buttonPressed.equals(buttonFriendHelp)){
                    builder.cancel();
                    buttonPressed.setClickable(false);
                    blueRow4.setVisibility(View.INVISIBLE);

                    showPublicHelp();
                }


            }
        });
        builder.show();
    }

private void chooseOneLetter(){
    final Dialog builder = new Dialog(this);
    builder.setCanceledOnTouchOutside(false);
    builder.setContentView(R.layout.show_one_letter);
    final Button buttonOneLetter = builder.findViewById(R.id.buttonShowOneLetter);

    buttonOneLetter.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            builder.cancel();
            amoutnLettes.setText("_ _ _ _ _   _ Р _ _ _ _");
            handler.postDelayed(new Runnable() {
                public void run() {
                    showInfo(R.string.thirdHelp, 3);
                }
            }, delay);

        }
    });
    builder.show();
}

private void showPublicHelp(){
    final Dialog builder = new Dialog(this);
    builder.setCanceledOnTouchOutside(false);
    builder.setContentView(R.layout.friends_help);
    final Button friendsHelp = builder.findViewById(R.id.buttonInformOK);
    friendsHelp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            builder.cancel();
            handler.postDelayed(new Runnable() {
                public void run() {
                    showInfo(R.string.helpAboutHelp, 5);
                }
            }, delay);


        }
    });
    builder.show();
}

}

