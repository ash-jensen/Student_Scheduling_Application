package com.ajensen.studentscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ajensen.studentscheduleapp.Database.Repository;
import com.ajensen.studentscheduleapp.Entity.Term;
import com.ajensen.studentscheduleapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TermEdit extends AppCompatActivity {
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    DatePickerDialog.OnDateSetListener startDatePicker;
    final Calendar startDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar endDateCalendar = Calendar.getInstance();
    String dateFormat;
    SimpleDateFormat sdf;
    int id;
    String name;
    String startDateString;
    Date startDate;
    String endDateString;
    Date endDate;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);
        // Get information from termList about the term
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
        repo = new Repository(getApplication());

        // Date pickers and formatter
        dateFormat = "MM/dd/yy"; //In which you need put here
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        startDateString = sdf.format(startDate);
        editStartDate.setText(startDateString);
        endDateString = sdf.format(endDate);
        editEndDate.setText(endDateString);
        // Start date
        editStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Date date;
                String info = editStartDate.getText().toString();
                if (info.equals("")) info = "01/01/22";
                try {
                    startDateCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermEdit.this, startDatePicker, startDateCalendar
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
                // Date date;
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = "01/01/22";
                try {
                    endDateCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermEdit.this, endDatePicker, endDateCalendar
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
        getMenuInflater().inflate(R.menu.menu_term_edit, menu);
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

    public void saveButton(View view) {
        Term term;
        if (id == -1) {
            int newId = repo.getAllTerms().get(repo.getAllTerms().size() -1).getTermId() + 1;
            // Date picker!
            term = new Term(newId, editName.getText().toString(), startDate, endDate);
            repo.insert(term);
        }
        else {
            term = new Term(id, editName.getText().toString(), startDate, endDate);
            repo.update(term);
        }
    }

}