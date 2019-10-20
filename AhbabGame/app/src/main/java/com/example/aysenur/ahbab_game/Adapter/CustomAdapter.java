package com.example.aysenur.ahbab_game.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aysenur.ahbab_game.R;
import com.example.aysenur.ahbab_game.model.Chatbot;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends ArrayAdapter<Chatbot> {

    private  int type;

    public CustomAdapter(Context context, ArrayList<Chatbot> chatList, int type) {
        super(context, 0, chatList);
        this.type = type;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Chatbot chatbot = getItem(position);
        Log.i("Deneme: ", ""+chatbot.getMessage());
        if (chatbot.getType() == 0){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.right_item_layout, parent, false);

            TextView txtUser = convertView.findViewById(R.id.txtUserRight);
            TextView txtMessage = convertView.findViewById(R.id.txtMessageRight);
            TextView txtTime = convertView.findViewById(R.id.txtTimeRight);

            txtUser.setText("You");
            txtMessage.setText(chatbot.getMessage());
            txtTime.setText(getTime());

        }else{

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.left_item_layout, parent, false);

            TextView txtUser = convertView.findViewById(R.id.txtUserLeft);
            TextView txtMessage = convertView.findViewById(R.id.txtMessageLeft);
            TextView txtTime = convertView.findViewById(R.id.txtTimeLeft);

            txtUser.setText("Chatbot");
            txtMessage.setText(chatbot.getMessage());
            txtTime.setText(getTime());

        }

        return convertView;
    }

    private String getTime(){

        long msTime = System.currentTimeMillis();
        Date curDateTime = new Date(msTime);
        SimpleDateFormat formatter = new SimpleDateFormat("dd'/'MM'/'y hh:mm");
        String dateTime = formatter.format(curDateTime);

        return dateTime;
    }
}
