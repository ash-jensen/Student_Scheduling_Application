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

public class AssessmentDetails extends AppCompatActivity {
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
    String type;
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
        setContentView(R.layout.activity_assessment_details);

        // Set names for textEdits
        editName = findViewById(R.id.editName);
        editType = findViewById(R.id.editType);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);

        // Get info about assessment from assessmentList
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
        startDate = new Date();
        startDate.setTime(getIntent().getLongExtra("startDate", -1));
        endDate = new Date();
        endDate.setTime(getIntent().getLongExtra("endDate", -1));
        courseId = getIntent().getIntExtra("courseId", -1);

        // Fill textEdits
        editName.setText(name);
        editType.setText(type);
        dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        startDateString = sdf.format(startDate);
        editStartDate.setText(startDateString);
        endDateString = sdf.format(endDate);
        editEndDate.setText(endDateString);
        repo = new Repository(getApplication());

        // Date Pickers
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = editStartDate.getText().toString();
                if (info.equals("")) info = "07/01/22";
                try {
                    startDateCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, startDatePicker, startDateCalendar
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
                new DatePickerDialog(AssessmentDetails.this, endDatePicker, endDateCalendar
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
        getMenuInflater().inflate(R.menu.menu_assessment_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.termList:
                Intent intent = new Intent(AssessmentDetails.this, TermList.class);
                startActivity(intent);
            case R.id.notifyStart:
                // Start notification
                Long triggerStart = startDate.getTime();
                Intent startIntent = new Intent(AssessmentDetails.this, MyReceiver.class);
                startIntent.putExtra("key", name + " starts today.");
                PendingIntent startSender = PendingIntent.getBroadcast(AssessmentDetails.this, MainActivity.numAlert++, startIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerStart, startSender);
                return true;
            case R.id.notifyEnd:
                // End notification
                Long triggerEnd = endDate.getTime();
                Intent endIntent = new Intent(AssessmentDetails.this, MyReceiver.class);
                endIntent.putExtra("key", name + " ends today.");
                PendingIntent endSender = PendingIntent.getBroadcast(AssessmentDetails.this, MainActivity.numAlert++, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerEnd, endSender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveEditsButton(View view) {
        if (startDate.compareTo(endDate) > 0) {
            Toast.makeText(AssessmentDetails.this, "End Date must be after Start Date.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Assessment assessment;
            if (id == -1) {
                int newId = repo.getAllAssessments().get(repo.getAllAssessments().size() - 1).getAssmtId() + 1;
                assessment = new Assessment(newId, editName.getText().toString(), editType.getText().toString(), startDate, endDate, courseId);
                repo.insert(assessment);
                Toast.makeText(AssessmentDetails.this, "Assessment has been saved." +
                        "\n You will now be taken to term list.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AssessmentDetails.this, TermList.class);
                startActivity(intent);
            } else {
                assessment = new Assessment(id, editName.getText().toString(), editType.getText().toString(), startDate, endDate, courseId);
                repo.update(assessment);
                Toast.makeText(AssessmentDetails.this, "Edits have been saved." +
                        "\n You will now be taken to term list.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AssessmentDetails.this, TermList.class);
                startActivity(intent);
            }
        }
    }

    public void deleteButton(View view) {
        Assessment assessment = new Assessment(id, editName.getText().toString(), editType.getText().toString(), startDate, endDate,  courseId);
        repo.delete(assessment);
        Toast.makeText(AssessmentDetails.this, "Assessment has been deleted." +
                "\n You will now be taken to term list.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AssessmentDetails.this, TermList.class);
        startActivity(intent);

    }
}
