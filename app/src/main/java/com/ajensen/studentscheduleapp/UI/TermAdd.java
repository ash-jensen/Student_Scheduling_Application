package com.ajensen.studentscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ajensen.studentscheduleapp.Database.Repository;
import com.ajensen.studentscheduleapp.Entity.Term;
import com.ajensen.studentscheduleapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TermAdd extends AppCompatActivity {
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    DatePickerDialog.OnDateSetListener startDatePicker;
    final Calendar startDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar endDateCalendar = Calendar.getInstance();
    String dateFormat;
    SimpleDateFormat sdf;
    String startDateString;
    Date startDate;
    String endDateString;
    Date endDate;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);

        // Get information from termList about the term
        editName = findViewById(R.id.editName);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);
        repo = new Repository(getApplication());

        // Date pickers and formatter
        dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        startDate = new Date();
        startDateString = sdf.format(startDate);
        editStartDate.setText(startDateString);
        endDate = new Date();
        endDateString = sdf.format(endDate);
        editEndDate.setText(endDateString);

        // Start date
        editStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (startDateString.equals("")) startDateString = "01/01/22";
                try {
                    startDateCalendar.setTime(sdf.parse(startDateString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermAdd.this, startDatePicker, startDateCalendar
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

        // End date
        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (endDateString.equals("")) endDateString = "01/01/22";
                try {
                    endDateCalendar.setTime(sdf.parse(endDateString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermAdd.this, endDatePicker, endDateCalendar
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
    }

    private  void updateLabelStart(){
        editStartDate.setText(sdf.format(startDateCalendar.getTime()));
    }

    private  void updateLabelEnd(){
        editEndDate.setText(sdf.format(endDateCalendar.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_term_add, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.termList:
                Intent intent = new Intent(TermAdd.this, TermList.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveButton(View view) {
        if (startDate.compareTo(endDate) > 0) {
            Toast.makeText(TermAdd.this, "End Date must be after Start Date.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            int id;
            if (repo.getAllTerms().isEmpty())
                id = 1;
            else {
                id = repo.getAllTerms().get(repo.getAllTerms().size() - 1).getTermId() + 1;
            }
            Term term = new Term(id, editName.getText().toString(), startDate, endDate);
            repo.insert(term);
            Toast.makeText(TermAdd.this, "Term has been added." +
                    "\n You will now be taken to term list.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TermAdd.this, TermList.class);
            startActivity(intent);
        }
    }
}