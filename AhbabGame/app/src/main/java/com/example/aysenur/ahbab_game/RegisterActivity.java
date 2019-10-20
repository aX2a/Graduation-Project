package com.example.aysenur.ahbab_game;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aysenur.ahbab_game.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtpassword, edtDate, edtPhone, edtRelativeName, edtRelativePhoneNum;
    private Button btn_create;
    public Context ctx;
    private FirebaseAuth mAuth;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        edtName = findViewById(R.id.name);
        edtEmail = findViewById(R.id.email);
        edtpassword = findViewById(R.id.password);
        edtDate = findViewById(R.id.date);
        edtPhone = findViewById(R.id.phone);
        edtRelativeName = findViewById(R.id.relativeName);
        edtRelativePhoneNum = findViewById(R.id.relativePhoneNum);
        btn_create = findViewById(R.id.btn_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtpassword.getText().toString();

                if(email.length() < 6) {
                    Toast.makeText(ctx,"Password must be longer than 6 character", Toast.LENGTH_LONG).show();
                }

                if(isValidEmailAddress(email)) {
                    createUser(email,password);
                }

                else {
                    Toast.makeText(ctx,"You Entered Invalid E-mail Address", Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    public static boolean isValidEmailAddress(String email) {

        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

    private void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("user");

                    String name = edtName.getText().toString();
                    String date = edtDate.getText().toString();
                    String phone = edtPhone.getText().toString();
                    String relativeName  = edtRelativeName.getText().toString();
                    String relativePhone  = edtRelativePhoneNum.getText().toString();

                    user = new User(userID, name, date, phone, relativeName, relativePhone);

                    mFirebaseDatabase.child(userID).setValue(user);

                    Intent intent = new Intent(RegisterActivity.this, LoginPageActivity.class);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(ctx, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
