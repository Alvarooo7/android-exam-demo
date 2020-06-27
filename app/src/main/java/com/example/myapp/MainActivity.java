package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btnQuestionOne,btnQuestionTwo,btnQuestionThree;

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
    }

    public void addListener(){
        final Intent goToQuestionOne = new Intent(this,QuestionOneActivity.class);
        final Intent goToQuestionTwo = new Intent(this,QuestionTwoActivity.class);
        final Intent goToQuestionThree = new Intent(this,LocationActivity.class);
        btnQuestionOne.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(goToQuestionOne);
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
                startActivity(goToQuestionThree);
            }
        });
    }


}