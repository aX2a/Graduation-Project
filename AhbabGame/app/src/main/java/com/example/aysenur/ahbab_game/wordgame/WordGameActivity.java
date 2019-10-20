package com.example.aysenur.ahbab_game.wordgame;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aysenur.ahbab_game.R;
import com.example.aysenur.ahbab_game.model.WordGameScore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WordGameActivity extends AppCompatActivity {

    final List<User> users = new ArrayList<User>();
    int count = 0, timer = 0, clikedNum = 0;
    private String scoreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_game);
        final List<Integer> randomBtn ;

        String text = readFile();
        //open and read txt file according to choice tag
        final String str [] = text.split("\n");
        randomBtn = getRandomNumber(3, str.length);

        //get number of choice data randomly for adding button
        for(int i=0; i<3; i++){
            users.add(new User(str[randomBtn.get(i)], true));
        }

        final ListView listView = findViewById(R.id.listView);
        CustomAdapter adapter = new CustomAdapter(this, users);
        listView.setAdapter(adapter);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                secondPart(randomBtn);
            }
        }, 5000);
    }

    private void secondPart(final List<Integer> randomBtn) {
        final Timer T=new Timer();
        final List<Integer> randomBtn2 ;

        String text = readFile();
        //open and read txt file according to choice tag
        final String str [] = text.split("\n");
        randomBtn2=getRandomNumber2(5, str.length,randomBtn);

        //get number of choice data randomly for adding button
        for(int i=0;i<5;i++) {
            users.add(new User(str[randomBtn2.get(i)], true));
        }

        Collections.shuffle(users);

        final ListView listView = (ListView) findViewById(R.id.listView);
        final CustomAdapter adapter = new CustomAdapter(this, users);
        listView.setAdapter(adapter);
        /////////////////////////////////////////////////////////////////////////////////////////////////////
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
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clikedNum++;
                String TAG="SEÇİM";
                // Get the selected item text from ListView
                String selectedItem = users.get(position).getUserName();

                for(int i=0;i<3;i++){
                    if(count<2) {
                        if (selectedItem.equals((str[randomBtn.get(i)]))) {
                            listView.getChildAt(position).setVisibility(view.INVISIBLE);
                            count++;
                        }
                    }
                    else if(count==2){
                        if (selectedItem.equals((str[randomBtn.get(i)]))) {
                            // listView.getChildAt(position-1).setVisibility(view.INVISIBLE);
                            T.cancel();
                            String userID;
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date date = new Date();

                            String date_score = dateFormat.format(date);
                            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("wordgamescore").child(userID);

                            if (TextUtils.isEmpty(scoreId)) {
                                scoreId = mFirebaseDatabase.push().getKey();
                            }

                            WordGameScore score = new WordGameScore(timer, clikedNum, date_score);
                            mFirebaseDatabase.child(scoreId).setValue(score);
                            Log.v(TAG, "COMPLETE " +" time "+ timer+ " tıklama sayısı"+ clikedNum);
                            Intent intent= new Intent(WordGameActivity.this, finish.class);
                            intent.putExtra("control","1");
                            startActivity(intent);

                        }
                    }
                    else {
                        T.cancel();
                        String userID;
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();

                        String date_score = dateFormat.format(date);
                        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("wordgamescore").child(userID);

                        if (TextUtils.isEmpty(scoreId)) {
                            scoreId = mFirebaseDatabase.push().getKey();
                        }

                        WordGameScore score = new WordGameScore(timer, clikedNum, date_score);
                        mFirebaseDatabase.child(scoreId).setValue(score);
                        Log.v(TAG, "COMPLETE "+ " time "+ timer+ timer+ " tıklama sayısı"+ clikedNum);
                        Intent intent= new Intent(WordGameActivity.this, finish.class);
                        intent.putExtra("control","1");
                        startActivity(intent);
                    }
                }

            }
        });


    }

    private List<Integer> getRandomNumber(int number, int limit){
        // to create different random number list for choices context
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> last = new ArrayList<Integer>();

        for (int i=0; i<limit; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0; i<number; i++) {
            last.add(list.get(i));
        }
        return last;
    }
    private List<Integer> getRandomNumber2(int number, int limit,List<Integer> randomNum){
        // to create different random number list for choices context
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> last = new ArrayList<Integer>();

        for (int i=1; i<limit; i++) {
            for (int y = 0; y < randomNum.size(); y++) {
                if (i!=randomNum.get(y)) {
                    list.add(new Integer(i));
                    break;
                }
            }
        }
        Collections.shuffle(list);
        for (int i=0; i<number; i++) {
            last.add(list.get(i));
        }
        return last;
    }
    private String readFile (){
        // read file
        String text ="";
        try {
            InputStream is = getResources().openRawResource(R.raw.turkishwords);
            int size = is.available();
            byte [] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Error reading File", Toast.LENGTH_SHORT).show();
        }
        return text;
    }
    public void onBackPressed(){
        Intent intent= new Intent(WordGameActivity.this, finish.class);
        intent.putExtra("control","2");
        startActivity(intent);
    }
}
