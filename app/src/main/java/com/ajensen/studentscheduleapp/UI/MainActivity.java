package com.ajensen.studentscheduleapp.UI;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ajensen.studentscheduleapp.Database.Repository;
import com.ajensen.studentscheduleapp.Entity.Assessment;
import com.ajensen.studentscheduleapp.Entity.Course;
import com.ajensen.studentscheduleapp.Entity.Term;
import com.ajensen.studentscheduleapp.R;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enterApp(View view) {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
        Repository repo = new Repository(getApplication());

        // ** DELETE ** //

        // Add Terms
        Term term = new Term(1, "Spring 2022", new Date(), new Date());
        Log.i(TAG, "Term: " + term);
        repo.insert(term);

        term = new Term(2, "Fall 2022", new Date(), new Date());
        repo.insert(term);

        // Add Course
        Course course = new Course(1, "Mobile App Dev", new Date(), new Date(), "Dropped", "Mr. Person",
                "email@email.com", "801-555-5555", 1, "This is the first class! Yay!");
        repo.insert(course);

        course = new Course(2, "Software 1", new Date(), new Date(), "Plan to Take", "Mr. Person",
                "email@email.com", "801-555-5555", 1, "This is the first class! Yay!");
        repo.insert(course);

        // Add Assessment
        Assessment assessment = new Assessment(1, "First Test", "Pop Quiz", new Date(), new Date(), 1);
        repo.insert(assessment);
    }
}