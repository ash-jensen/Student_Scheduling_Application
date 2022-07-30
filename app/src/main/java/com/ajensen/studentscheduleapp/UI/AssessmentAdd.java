package com.ajensen.studentscheduleapp.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ajensen.studentscheduleapp.Database.Repository;
import com.ajensen.studentscheduleapp.Entity.Assessment;
import com.ajensen.studentscheduleapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentAdd extends AppCompatActivity {
    Repository repo ;
    EditText editName;
    EditText editType;
    EditText editStartDate;
    EditText editEndDate;
    DatePickerDialog.OnDateSetListener startDatePicker;
    final Calendar startDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar endDateCalendar = Calendar.getInstance();
    int id;
    String name;
    Date startDate;
    Date endDate;
    String startDateString;
    String endDateString;
    int courseId;
    String dateFormat;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);

        // Set names for textEdits
        editName = findViewById(R.id.editName);
        editType = findViewById(R.id.editType);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);
        repo = new Repository(getApplication());

        // Get courseId from courseDetails
        courseId = getIntent().getIntExtra("id", -1);

        // Date pickers and formatter
        dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        startDate = new Date();
        startDateString = sdf.format(startDate);
        editStartDate.setText(startDateString);
        endDate = new Date();
        endDateString = sdf.format(endDate);
        editEndDate.setText(endDateString);

        // Start Date
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = "07/01/22";
                try {
                    startDateCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentAdd.this, startDatePicker, startDateCalendar
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
                if (info.equals("")) info = "07/01/22";
                try {
                    endDateCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentAdd.this, endDatePicker, endDateCalendar
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

        // Course status setup
        Button button = findViewById(R.id.typeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assessmentTypePopUp(view);
            }
        });
    }

    private void assessmentTypePopUp(View view){
        PopupMenu typeMenu = new PopupMenu(this,view);
        typeMenu.inflate(R.menu.menu_assessment_type);
        typeMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.objective:
                        editType.setText("Objective");
                        return true;
                    case R.id.performance:
                        editType.setText("Performance");
                        return true;
                    default:return false;
                }
            }
        });
        typeMenu.show();
    }

    private  void updateLabelStart(){
        editStartDate.setText(sdf.format(startDateCalendar.getTime()));
    }

    private  void updateLabelEnd(){
        editEndDate.setText(sdf.format(endDateCalendar.getTime()));
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_add, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.termList:
                Intent intent = new Intent(AssessmentAdd.this, TermList.class);
                startActivity(intent);
            case R.id.notifyStart:
                // Start notification
                Long triggerStart = startDate.getTime();
                Intent startIntent = new Intent(AssessmentAdd.this, MyReceiver.class);
                startIntent.putExtra("key", name + " starts today.");
                PendingIntent startSender = PendingIntent.getBroadcast(AssessmentAdd.this, MainActivity.numAlert++, startIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerStart, startSender);
                return true;
            case R.id.notifyEnd:
                // End notification
                Long triggerEnd = endDate.getTime();
                Intent endIntent = new Intent(AssessmentAdd.this, MyReceiver.class);
                endIntent.putExtra("key", name + " ends today.");
                PendingIntent endSender = PendingIntent.getBroadcast(AssessmentAdd.this, MainActivity.numAlert++, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerEnd, endSender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveButton(View view) {
        if (startDate.compareTo(endDate) > 0) {
            Toast.makeText(AssessmentAdd.this, "End Date must be after Start Date.",
                    Toast.LENGTH_LONG).show();
        } else {
            if (repo.getAllAssessments().isEmpty())
                id = 1;
            else {
                id = repo.getAllAssessments().get(repo.getAllAssessments().size() - 1).getAssmtId() + 1;
            }
            Assessment assessment = new Assessment(id, editName.getText().toString(), editType.getText().toString(), startDate, endDate, courseId);
            repo.insert(assessment);
            Toast.makeText(AssessmentAdd.this, "Assessment has been saved." +
                    "\n You will now be taken to term list.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AssessmentAdd.this, TermList.class);
            startActivity(intent);
        }
    }
}