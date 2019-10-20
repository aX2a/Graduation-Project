package com.example.aysenur.ahbab_game.Schedule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.aysenur.ahbab_game.Adapter.ScheduleAdapter;
import com.example.aysenur.ahbab_game.R;
import com.example.aysenur.ahbab_game.model.Schedule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    RelativeLayout relAddNote;
    RecyclerView rvNotesList;
    RecyclerView.LayoutManager mLayoutManager;

    ScheduleAdapter scheduleAdapter;
    List<Schedule> scheduleList = new ArrayList<>();

    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        relAddNote = findViewById(R.id.relAddNote);
        rvNotesList = findViewById(R.id.rvNotesList);

        relAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, AddNote.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseReference = mFirebaseInstance.getReference("schedule").child(userID);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                scheduleList.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Schedule s = postSnapshot.getValue(Schedule.class);
                    scheduleList.add(s);
                }

                mLayoutManager = new LinearLayoutManager(ScheduleActivity.this, LinearLayoutManager.VERTICAL, false);
                rvNotesList.setLayoutManager(mLayoutManager);

                scheduleAdapter = new ScheduleAdapter(scheduleList, ScheduleActivity.this);
                rvNotesList.setAdapter(scheduleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
