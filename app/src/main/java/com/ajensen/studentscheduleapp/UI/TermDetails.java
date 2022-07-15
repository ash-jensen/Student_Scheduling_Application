package com.ajensen.studentscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ajensen.studentscheduleapp.Database.Repository;
import com.ajensen.studentscheduleapp.Entity.Course;
import com.ajensen.studentscheduleapp.Entity.Term;
import com.ajensen.studentscheduleapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {
    Repository repo ;
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    int id;
    String name;
    Date startDate;
    Date endDate;
    String startDateString;
    String endDateString;
    String dateFormat;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        // Get info about selected Term from TermList
        editName = findViewById(R.id.editName);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        startDate = new Date();
        startDate.setTime(getIntent().getLongExtra("startDate", -1));
        endDate = new Date();
        endDate.setTime(getIntent().getLongExtra("endDate", -1));
        editName.setText(name);
        // Date picker and formatter
        dateFormat = "MM/dd/yy"; //In which you need put here
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        startDateString = sdf.format(startDate);
        editStartDate.setText(startDateString);
        endDateString = sdf.format(endDate);
        editEndDate.setText(endDateString);
        repo = new Repository(getApplication());

        // Fill courseListRV with courses
        RecyclerView recyclerView = findViewById(R.id.courseListRV);
        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getAllCourses();
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourseList(courses);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_term_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void editButton(View view) {
        Intent intent = new Intent(TermDetails.this, TermEdit.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        // change to use date picker, here and in term details
        intent.putExtra("startDate", startDate.getTime());
        intent.putExtra("endDate", endDate.getTime());
        startActivity(intent);
    }

    public void AddCourse(View view) {
    }
}