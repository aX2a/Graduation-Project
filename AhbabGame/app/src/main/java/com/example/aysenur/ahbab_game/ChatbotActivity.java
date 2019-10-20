package com.example.aysenur.ahbab_game;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aysenur.ahbab_game.Adapter.CustomAdapter;
import com.example.aysenur.ahbab_game.model.Chatbot;

import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class ChatbotActivity extends AppCompatActivity {

    private static final String SERVER_ADDRESS = "http://192.168.43.137:8000/api/chatbot";

    private ArrayList<Chatbot> chatLists = new ArrayList<>();
    private CustomAdapter customAdapter;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private EditText inputChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        listView = findViewById(R.id.chatListView);
        inputChat = findViewById(R.id.inputChat);
        floatingActionButton = findViewById(R.id.fab);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputChat.getText().length()>=1){
                    String message = inputChat.getText().toString();
                    Chatbot chatbot = new Chatbot(message, 0);
                    chatLists.add(chatbot);
                    inputChat.setText("");

                    try{
                        new sendMessage(message).execute();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    customAdapter = new CustomAdapter(getApplicationContext(), chatLists, 0);
                    listView.setAdapter(customAdapter);


                }else{
                    Toast.makeText(getApplicationContext(),"Gönderilecek mesaj uzunluğu en az 1 karakter olmalıdır!",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void saveState(String s) {

        try {
            PrintStream output = new PrintStream(ChatbotActivity.this.openFileOutput("state.txt", MODE_PRIVATE));
            output.print(s);
            output.close();

        } catch (Exception e) {
            Log.e("file ex", e.getMessage());
        }
    }

    public class sendMessage extends AsyncTask<Void,Void,String> {

        String message;
        public sendMessage(String message) {
            this.message = message;
        }

        protected void onPreExecute(){
            saveState("0");
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            Log.i("Data ", "dataa "+message);
            dataToSend.add(new BasicNameValuePair("message", message));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post =  new HttpPost(SERVER_ADDRESS);

            try{
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                //client.execute(post);
                HttpResponse response = client.execute(post);
                int status = response.getStatusLine().getStatusCode();

                if(status == 200){
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.i("Data ", "doInBackground "+data);
                    Chatbot chatbot = new Chatbot(data, 1);
                    chatLists.add(chatbot);
                    return data;
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(ChatbotActivity.this,"Tanımlanma Tamamlandı!",Toast.LENGTH_SHORT).show();

            saveState("1");

        }

    }

}
