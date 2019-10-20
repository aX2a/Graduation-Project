package com.example.aysenur.ahbab_game.biggernumber;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aysenur.ahbab_game.MainActivity;
import com.example.aysenur.ahbab_game.R;
import com.example.aysenur.ahbab_game.model.BiggerNumberScore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BiggerNameActivity extends AppCompatActivity {

    Handler handler = new Handler();
    int time = 0;
    private String userID;

    Timer T=new Timer();

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    endGame(true);
                }
            });
        }
    };

    // game variables
    private static int
            CONSECUTIVE_RIGHT_ANSWERS = 0,
            LEVEL = 1,
            SCORE = 0
                    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigger_name);
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time++;
                    }
                });
            }
        }, 1000, 1000);
        setLayoutsByLevel();
    }

    private void setLayoutsByLevel(){
        // set score board layout
        handleScoreboard();

        // set selection buttons layout
        int buttonCount, maxValue, minValue;
        switch (LEVEL) {
            case 1: buttonCount=3; minValue=0; maxValue=20; break;
            case 2: buttonCount=4; minValue=0; maxValue=40; break;
            case 3: buttonCount=4; minValue=-100; maxValue=100; break;
            case 4: buttonCount=5; minValue=-100; maxValue=100; break;
            case 5: buttonCount=6; minValue=-100; maxValue=100; break;
            case 6: buttonCount=8; minValue=-100; maxValue=100; break;
            default: buttonCount=8; minValue=-100; maxValue=100; break; // assuming level 10
        }
        handleButtons(buttonCount, maxValue, minValue);

    }

    private void handleButtons(int buttonCount, int maxValue, int minValue){
        LinearLayout selectionButtonsLayout = findViewById(R.id.selectionButtonsCenterLayout);
        selectionButtonsLayout.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        for ( Integer value : getDistinctIntegerList(buttonCount, maxValue, minValue) ){
            Button btn = new Button(this);
            btn.setId(value);
            btn.setText(""+value);
            btn.setTextSize(28);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    gameEngine( checkIfBigger(view) );
                }
            });
            selectionButtonsLayout.addView(btn, lp);
        }
    }

    private void gameEngine(boolean success){
        if(success){ // gave right answer
            CONSECUTIVE_RIGHT_ANSWERS++;
            if(CONSECUTIVE_RIGHT_ANSWERS % 5 == 0){ // level up
                CONSECUTIVE_RIGHT_ANSWERS = 0;
                updateStats(true);
            }else {
                updateStats(false);
            }
        }else{ // gave wrong answer
            endGame(false);
        }
    }

    private void updateStats(boolean levelup){
        if(levelup){
            LEVEL ++;
        }
        SCORE +=1;
        setLayoutsByLevel();
    }

    private String scoreId;
    private void endGame(boolean timeout){
        setContentView(R.layout.endgame_activity);
        TextView endgameLevelView = findViewById(R.id.endgameLevelText);
        TextView endgameScoreView = findViewById(R.id.endgameScoreText);
        RelativeLayout relTryAgain = findViewById(R.id.relTryAgain);
        RelativeLayout relExit = findViewById(R.id.relExit);

        endgameLevelView.setText("level "+LEVEL);
        endgameScoreView.setText("score: "+SCORE);
        T.cancel();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        String date_score = dateFormat.format(date);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("biggernumberscore").child(userID);

        if (TextUtils.isEmpty(scoreId)) {
            scoreId = mFirebaseDatabase.push().getKey();
        }

        BiggerNumberScore score = new BiggerNumberScore(SCORE, time, scoreId, date_score);
        mFirebaseDatabase.child(scoreId).setValue(score);


        relTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LEVEL = 1;
                SCORE = 0;
                Intent intent = new Intent(BiggerNameActivity.this, BiggerNameActivity.class);
                startActivity(intent);
            }
        });

        relExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LEVEL = 1;
                SCORE = 0;
                Intent intent = new Intent(BiggerNameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private ArrayList<Integer> getDistinctIntegerList(int count, int maxValue, int minValue){
        Random rand = new Random();
        ArrayList<Integer> selectedValues = new ArrayList<>(count);
        for(int i = 0 ; i < count ;){
            int value = rand.nextInt(maxValue - minValue + 1) + minValue;
            if(!selectedValues.contains(value)){
                selectedValues.add(value);
                i++;
            }
        }
        return selectedValues;
    }

    @SuppressLint("ResourceType")
    private boolean checkIfBigger(View view){
        LinearLayout selectionButtonsLayout = findViewById(R.id.selectionButtonsCenterLayout);
        int count = selectionButtonsLayout.getChildCount();
        int clicked = view.getId();
        int max = clicked;
        for (int i = 0; i < count; i++) {
            if( selectionButtonsLayout.getChildAt(i).getId() > max ){
                max = selectionButtonsLayout.getChildAt(i).getId();
            }
        }
        return ((max == clicked) ? true : false);
    }

    private void handleScoreboard(){
        TextView currentLevelView = findViewById(R.id.currentLevelText);
        TextView currentScoreView = findViewById(R.id.currentScoreText);

        currentLevelView.setText("level "+LEVEL);
        currentScoreView.setText("score: "+SCORE);
    }

    public void onBackPressed() {
        endGame(false);
    }

}
