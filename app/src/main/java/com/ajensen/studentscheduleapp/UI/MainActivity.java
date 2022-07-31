package com.ajensen.studentscheduleapp.UI;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ajensen.studentscheduleapp.Database.Repository;
import com.ajensen.studentscheduleapp.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enterApp(View view) {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
        Repository repo = new Repository(getApplication());
    }
}