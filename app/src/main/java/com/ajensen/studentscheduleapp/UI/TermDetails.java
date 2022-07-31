package com.ajensen.studentscheduleapp.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajensen.studentscheduleapp.Database.Repository;
import com.ajensen.studentscheduleapp.Entity.Course;
import com.ajensen.studentscheduleapp.Entity.Term;
import com.ajensen.studentscheduleapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {
    Repository repo ;
    EditText editName;
    EditText editStartDate;
    DatePickerDialog.OnDateSetListener startDatePicker;
    final Calendar startDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar endDateCalendar = Calendar.getInstance();
    EditText editEndDate;
    int id;
    String name;
    Date startDate;
    Date endDate;
    String startDateString;
    String endDateString;
    String dateFormat;
    SimpleDateFormat sdf;
    List<Course> filteredCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        // Set names for textEdits
        editName = findViewById(R.id.editName);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);

        // Get info about selected Term from TermList
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        startDate = new Date();
        startDate.setTime(getIntent().getLongExtra("startDate", -1));
        endDate = new Date();
        endDate.setTime(getIntent().getLongExtra("endDate", -1));
        editName.setText(name);

        // Fill textEdits
        dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        startDateString = sdf.format(startDate);
        editStartDate.setText(startDateString);
        endDateString = sdf.format(endDate);
        editEndDate.setText(endDateString);
        repo = new Repository(getApplication());

        // Start Date
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = "01/01/22";
                try {
                    startDateCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, startDatePicker, startDateCalendar
                        .get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH),
                        startDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDatePicker = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                startDateCalendar.set(Calendar.YEAR,year);
                startDateCalendar.set(Calendar.MONTH,monthOfYear);
                startDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startDate = startDateCalendar.getTime();
                updateLabelStart();
            }
        };

        // End Date
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = "01/01/22";
                try {
                    endDateCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, endDatePicker, endDateCalendar
                        .get(Calendar.YEAR), endDateCalendar.get(Calendar.MONTH),
                        endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDatePicker = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                endDateCalendar.set(Calendar.YEAR,year);
                endDateCalendar.set(Calendar.MONTH,monthOfYear);
                endDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endDate = endDateCalendar.getTime();
                updateLabelEnd();
            }
        };

        // Fill courseListRV with courses
        RecyclerView recyclerView = findViewById(R.id.courseListRV);
        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getAllCourses();
        filteredCourses = new ArrayList<>();
        for(Course c:repo.getAllCourses()) {
            if(c.getTermId() == id) {
                filteredCourses.add(c);
            }
        }
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourseList(filteredCourses);
    }

    private  void updateLabelStart(){
        editStartDate.setText(sdf.format(startDateCalendar.getTime()));
    }

    private  void updateLabelEnd(){
        editEndDate.setText(sdf.format(endDateCalendar.getTime()));
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.termList:
                Intent intent = new Intent(TermDetails.this, TermList.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveEditsButton(View view) {
        if (startDate.compareTo(endDate) > 0) {
            Toast.makeText(TermDetails.this, "End Date must be after Start Date.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Term term;
            if (id == -1) {
                int newId = repo.getAllTerms().get(repo.getAllTerms().size() - 1).getTermId() + 1;
                term = new Term(newId, editName.getText().toString(), startDate, endDate);
                repo.insert(term);
                Toast.makeText(TermDetails.this, "Term has been added." +
                        "\n You will now be taken to term list.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(TermDetails.this, TermList.class);
                startActivity(intent);
            } else {
                term = new Term(id, editName.getText().toString(), startDate, endDate);
                Toast.makeText(TermDetails.this, "Term has been updated." +
                        "\n You will now be taken to term list.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(TermDetails.this, TermList.class);
                startActivity(intent);
                repo.update(term);
            }
        }
    }

    public void deleteButton(View view) {
        Term term = new Term(id, name, startDate, endDate);
        // check for course
        if (!filteredCourses.isEmpty()) {
            Toast.makeText(TermDetails.this, "Must delete associated courses before deleting term.", Toast.LENGTH_LONG).show();
        }
        else {
            // If term has no associated courses, delete
            repo.delete(term);
            Toast.makeText(TermDetails.this, "Term has been deleted. " +
                    "\n You will now be taken to term list.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TermDetails.this, TermList.class);
            startActivity(intent);
        }
    }

    public void AddCourse(View view) {
        Intent intent = new Intent(TermDetails.this, CourseAdd.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}