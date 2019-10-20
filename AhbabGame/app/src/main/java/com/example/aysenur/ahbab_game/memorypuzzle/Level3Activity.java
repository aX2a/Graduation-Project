package com.example.aysenur.ahbab_game.memorypuzzle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.aysenur.ahbab_game.R;
import com.example.aysenur.ahbab_game.model.Move;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Level3Activity extends AppCompatActivity {

    ImageView iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_31, iv_32, iv_33;

    Integer[] cardsArray = {101, 102, 103, 104, 105, 201, 202, 203, 204, 205};

    int image101, image102, image103, image104, image105,
            image201, image202, image203, image204, image205;

    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;
    int playerPoints = 0, timer = 0;

    Timer T=new Timer();
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3);

        iv_11 = findViewById(R.id.iv_11);
        iv_12 = findViewById(R.id.iv_12);
        iv_13 = findViewById(R.id.iv_13);
        iv_14 = findViewById(R.id.iv_14);
        iv_21 = findViewById(R.id.iv_21);
        iv_22 = findViewById(R.id.iv_22);
        iv_23 = findViewById(R.id.iv_23);
        iv_31 = findViewById(R.id.iv_31);
        iv_32 = findViewById(R.id.iv_32);
        iv_33 = findViewById(R.id.iv_33);

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_31.setTag("7");
        iv_32.setTag("8");
        iv_33.setTag("9");

        frontOfCardsResources();

        Collections.shuffle(Arrays.asList(cardsArray));

        iv_11.setEnabled(false);
        iv_12.setEnabled(false);
        iv_13.setEnabled(false);
        iv_14.setEnabled(false);
        iv_21.setEnabled(false);
        iv_22.setEnabled(false);
        iv_23.setEnabled(false);
        iv_31.setEnabled(false);
        iv_32.setEnabled(false);
        iv_33.setEnabled(false);

        drawCard(iv_11, 0);
        drawCard(iv_12, 1);
        drawCard(iv_13, 2);
        drawCard(iv_14, 3);
        drawCard(iv_21, 4);
        drawCard(iv_22, 5);
        drawCard(iv_23, 6);
        drawCard(iv_31, 7);
        drawCard(iv_32, 8);
        drawCard(iv_33, 9);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_11.setEnabled(true);
                iv_12.setEnabled(true);
                iv_13.setEnabled(true);
                iv_14.setEnabled(true);
                iv_21.setEnabled(true);
                iv_22.setEnabled(true);
                iv_23.setEnabled(true);
                iv_31.setEnabled(true);
                iv_32.setEnabled(true);
                iv_33.setEnabled(true);
                iv_11.setImageResource(R.drawable.ic_back);
                iv_12.setImageResource(R.drawable.ic_back);
                iv_13.setImageResource(R.drawable.ic_back);
                iv_14.setImageResource(R.drawable.ic_back);
                iv_21.setImageResource(R.drawable.ic_back);
                iv_22.setImageResource(R.drawable.ic_back);
                iv_23.setImageResource(R.drawable.ic_back);
                iv_31.setImageResource(R.drawable.ic_back);
                iv_32.setImageResource(R.drawable.ic_back);
                iv_33.setImageResource(R.drawable.ic_back);
            }
        }, 5000);


        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        timer++;
                    }
                });
            }
        }, 1000, 1000);

        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_11, theCard);
            }
        });

        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_12, theCard);
            }
        });

        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_13, theCard);
            }
        });

        iv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_14, theCard);
            }
        });

        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_21, theCard);
            }
        });

        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_22, theCard);
            }
        });

        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_23, theCard);
            }
        });

        iv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_31, theCard);
            }
        });

        iv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_32, theCard);
            }
        });

        iv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_33, theCard);
            }
        });

    }

    private void drawCard(ImageView iv, int card){
        if(cardsArray[card] == 101){
            iv.setImageResource(image101);
        }else if(cardsArray[card] == 102){
            iv.setImageResource(image102);
        }else if(cardsArray[card] == 103){
            iv.setImageResource(image103);
        }else if(cardsArray[card] == 104){
            iv.setImageResource(image104);
        }else if(cardsArray[card] == 105){
            iv.setImageResource(image105);
        }else if(cardsArray[card] == 201){
            iv.setImageResource(image201);
        }else if(cardsArray[card] == 202){
            iv.setImageResource(image202);
        }else if(cardsArray[card] == 203){
            iv.setImageResource(image203);
        }else if(cardsArray[card] == 204){
            iv.setImageResource(image204);
        }else if(cardsArray[card] == 205){
            iv.setImageResource(image205);
        }
    }

    private  void doStuff(ImageView iv, int card){

        if(cardsArray[card] == 101){
            iv.setImageResource(image101);
        }else if(cardsArray[card] == 102){
            iv.setImageResource(image102);
        }else if(cardsArray[card] == 103){
            iv.setImageResource(image103);
        }else if(cardsArray[card] == 104){
            iv.setImageResource(image104);
        }else if(cardsArray[card] == 105){
            iv.setImageResource(image105);
        }else if(cardsArray[card] == 201){
            iv.setImageResource(image201);
        }else if(cardsArray[card] == 202){
            iv.setImageResource(image202);
        }else if(cardsArray[card] == 203){
            iv.setImageResource(image203);
        }else if(cardsArray[card] == 204){
            iv.setImageResource(image204);
        }else if(cardsArray[card] == 205){
            iv.setImageResource(image205);
        }

        if(cardNumber == 1){
            firstCard = cardsArray[card];
            if(firstCard > 200 ){
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;

            iv.setEnabled(false);

        }else if(cardNumber == 2){
            secondCard = cardsArray[card];
            if(secondCard > 200 ){
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);
            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    calculated();
                }
            }, 1000);
        }
    }

    private void calculated(){
        if(firstCard == secondCard){
            if(clickedFirst == 0){
                iv_11.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 1){
                iv_12.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 2){
                iv_13.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 3){
                iv_14.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 4){
                iv_21.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 5){
                iv_22.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 6){
                iv_23.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 7){
                iv_31.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 8){
                iv_32.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 9){
                iv_33.setVisibility(View.INVISIBLE);
            }

            if(clickedSecond == 0){
                iv_11.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 1){
                iv_12.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 2){
                iv_13.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 3){
                iv_14.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 4){
                iv_21.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 5){
                iv_22.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 6){
                iv_23.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 7){
                iv_31.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 8){
                iv_32.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 9){
                iv_33.setVisibility(View.INVISIBLE);
            }

            playerPoints++;

        } else {
            iv_11.setImageResource(R.drawable.ic_back);
            iv_12.setImageResource(R.drawable.ic_back);
            iv_13.setImageResource(R.drawable.ic_back);
            iv_14.setImageResource(R.drawable.ic_back);
            iv_21.setImageResource(R.drawable.ic_back);
            iv_22.setImageResource(R.drawable.ic_back);
            iv_23.setImageResource(R.drawable.ic_back);
            iv_31.setImageResource(R.drawable.ic_back);
            iv_32.setImageResource(R.drawable.ic_back);
            iv_33.setImageResource(R.drawable.ic_back);

            playerPoints++;
        }

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);

        checkEnd();
    }

    private String moveId;
    private void checkEnd(){
        if(iv_11.getVisibility() == View.INVISIBLE &&
                iv_12.getVisibility() == View.INVISIBLE &&
                iv_13.getVisibility() == View.INVISIBLE &&
                iv_14.getVisibility() == View.INVISIBLE &&
                iv_21.getVisibility() == View.INVISIBLE &&
                iv_22.getVisibility() == View.INVISIBLE &&
                iv_23.getVisibility() == View.INVISIBLE &&
                iv_31.getVisibility() == View.INVISIBLE &&
                iv_32.getVisibility() == View.INVISIBLE &&
                iv_33.getVisibility() == View.INVISIBLE){

            T.cancel();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            String date_move = dateFormat.format(date);
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("moves").child(userID);

            if (TextUtils.isEmpty(moveId)) {
                moveId = mFirebaseDatabase.push().getKey();
            }

            Move move = new Move(date_move, "3", playerPoints, timer-5);

            mFirebaseDatabase.child(moveId).setValue(move);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Level3Activity.this);
            alertDialogBuilder
                    .setMessage("Level Completed!\nMove: " + playerPoints)
                    .setCancelable(false)
                    .setPositiveButton("NEXT LEVEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(),Level4Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void frontOfCardsResources(){
        image101 = R.drawable.ic_image108;
        image102 = R.drawable.ic_image109;
        image103 = R.drawable.ic_image1010;
        image104 = R.drawable.ic_image1011;
        image105 = R.drawable.ic_image1012;
        image201 = R.drawable.ic_image208;
        image202 = R.drawable.ic_image209;
        image203 = R.drawable.ic_image2010;
        image204 = R.drawable.ic_image2011;
        image205 = R.drawable.ic_image2012;
    }

}
