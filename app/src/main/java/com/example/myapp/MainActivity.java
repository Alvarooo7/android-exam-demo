package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button btnQuestionOne,btnQuestionTwo,btnQuestionThree;
    TextView edtTittle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addListener();
    }

    public void initView(){
        btnQuestionOne = findViewById(R.id.btnQuestionOne);
        btnQuestionTwo = findViewById(R.id.btnQuestionTwo);
        btnQuestionThree = findViewById(R.id.btnQuestionThree);
        edtTittle = findViewById(R.id.txtTittleUser);

        SharedPreferences prfs = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        String username = prfs.getString("Authentication_Name", "");
        edtTittle.setText("Bienvenido "+username.toUpperCase()+"!");
    }

    public void addListener(){
        final Intent goToQuestionOne = new Intent(this,QuestionOneActivity.class);
        final Intent goToLogin = new Intent(this,LoginActivity.class);
        final Intent goToQuestionTwo = new Intent(this,QuestionTwoActivity.class);
        final Intent goToQuestionThree = new Intent(this,LocationActivity.class);
        btnQuestionOne.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(goToQuestionThree);
            }
        });

        btnQuestionTwo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(goToQuestionTwo);
            }
        });

        btnQuestionThree.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                settings.edit().remove("Authentication_Name").commit();
                startActivity(goToLogin);
                finish();
            }
        });
    }


}