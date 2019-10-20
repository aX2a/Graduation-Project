package com.example.aysenur.ahbab_game;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.aysenur.ahbab_game.Fragment.ProfilePageFragment;
import com.example.aysenur.ahbab_game.Schedule.ScheduleActivity;
import com.example.aysenur.ahbab_game.biggernumber.BiggerNameActivity;
import com.example.aysenur.ahbab_game.memorypuzzle.Level1Activity;
import com.example.aysenur.ahbab_game.wordgame.WordGameActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relMemoryPuzzle, relBiggerNumber, relWordGame;
    ImageView btnLogout, btnChatbot, btnProfile, btnCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relMemoryPuzzle = findViewById(R.id.relMemoryPuzzle);
        relBiggerNumber = findViewById(R.id.relBiggerNumber);
        relWordGame = findViewById(R.id.relWordGame);
        btnLogout = findViewById(R.id.btnLogout);
        btnChatbot = findViewById(R.id.btnChatbot);
        btnProfile = findViewById(R.id.btnProfile);
        btnCalendar = findViewById(R.id.btnCalendar);

        relMemoryPuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Level1Activity.class);
                startActivity(intent);
            }
        });

        relWordGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WordGameActivity.class);
                startActivity(intent);
            }
        });

        relBiggerNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BiggerNameActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginPageActivity.class);
                startActivity(intent);
            }
        });

        btnChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatbotActivity.class);
                startActivity(intent);
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmpityPageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        ActivityCompat.finishAffinity(MainActivity.this);
        android.os.Process.killProcess(0);
    }
}
