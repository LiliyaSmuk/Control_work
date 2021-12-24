package com.example.overwork_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView description_Txt;
    String strRes;
    String rename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        description_Txt = (TextView) findViewById(R.id.mTxt);
        Intent intent = getIntent();
        strRes = intent.getStringExtra("name");
        rename = strRes.replaceAll("\\<.*?\\>", ""); // замена разделителей
        description_Txt.setText(rename.trim()); // удаление пробелов
    }
}