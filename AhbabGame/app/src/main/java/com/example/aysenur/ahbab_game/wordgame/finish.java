package com.example.aysenur.ahbab_game.wordgame;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aysenur.ahbab_game.MainActivity;
import com.example.aysenur.ahbab_game.R;

import java.text.BreakIterator;
import java.util.List;

public class finish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        TextView textView=findViewById(R.id.txt);

        Bundle extras = getIntent().getExtras();
        String value = extras.getString("control");
        if(value.equals("1")){
            textView.setText("BÖLÜMÜ BİTİRDİNİZ");
        }
        if(value.equals("2")){
            textView.setText("GAME OVER");
        }
        Button btnTryAgain=findViewById(R.id.tekrar);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(finish.this, WordGameActivity.class);
                startActivity(intent);
            }
        });

        Button btnGameOver=findViewById(R.id.cikis);
        btnGameOver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(finish.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void onBackPressed(){

            ActivityCompat.finishAffinity(finish.this);
            android.os.Process.killProcess(0);

    }

}
