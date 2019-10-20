package com.example.aysenur.ahbab_game;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.aysenur.ahbab_game.Fragment.ProfilePageFragment;

public class EmpityPageActivity extends AppCompatActivity {

    FrameLayout frameProfilPage;
    Fragment profilPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empity_page);

        frameProfilPage = findViewById(R.id.frameProfilPage);
        profilPageFragment = new ProfilePageFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameProfilPage, profilPageFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
