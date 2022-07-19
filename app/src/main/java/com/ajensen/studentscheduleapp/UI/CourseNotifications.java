package com.ajensen.studentscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ajensen.studentscheduleapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CourseNotifications extends AppCompatActivity {
    EditText startDate;
    String dateFormat;
    SimpleDateFormat sdf;
    DatePickerDialog.OnDateSetListener startDatePicker;
    final Calendar startDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar endDateCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notifications);

        /*
        // Date formatter;
        dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);

        // Date Picker
        startDate = findViewById(R.id.startDate);
        startDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Date date;
                String info = editDate.getTExt().toString();
                if(info.equals)
                new DatePickerDialog(CourseNotifications.this, startDatePicker, startDateCalendar
                        .get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH), startDateCalendar
                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });

         */



    }


}