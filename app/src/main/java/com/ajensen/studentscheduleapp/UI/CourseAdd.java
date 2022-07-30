package com.ajensen.studentscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;

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

import com.ajensen.studentscheduleapp.Database.Repository;
import com.ajensen.studentscheduleapp.Entity.Course;
import com.ajensen.studentscheduleapp.Entity.Term;
import com.ajensen.studentscheduleapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CourseAdd extends AppCompatActivity {
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    EditText editStatus;
    EditText editInstructor;
    EditText editNumber;
    EditText editEmail;
    EditText editNotes;
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
    String status;
    String instructor;
    String number;
    String email;
    int termId;
    String notes;
    String dateFormat;
    SimpleDateFormat sdf;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);

        // Set names for textEdits
        editName = findViewById(R.id.editName);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);
        editStatus = findViewById(R.id.editStatus);
        editInstructor = findViewById(R.id.editInstructor);
        editNumber = findViewById(R.id.editNumber);
        editEmail = findViewById(R.id.editEmail);
        editNotes = findViewById(R.id.editNotes);
        repo = new Repository(getApplication());

        // Get termId from termList
        termId = getIntent().getIntExtra("id", -1);

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
                if (startDateString.equals("")) startDateString = "07/01/22";
                try {
                    startDateCalendar.setTime(sdf.parse(startDateString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseAdd.this, startDatePicker, startDateCalendar
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
                if (endDateString.equals("")) endDateString = "07/01/22";
                try {
                    endDateCalendar.setTime(sdf.parse(endDateString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseAdd.this, endDatePicker, endDateCalendar
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
        Button button=findViewById(R.id.statusButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusPopUp(view);
            }
        });
    }

    private void statusPopUp(View view){
        PopupMenu statusMenu = new PopupMenu(this,view);
        statusMenu.inflate(R.menu.menu_course_status);
        statusMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.inProgress:
                        editStatus.setText("In Progress");
                        return true;
                    case R.id.completed:
                        editStatus.setText("Completed");
                        return true;
                    case R.id.dropped:
                        editStatus.setText("Dropped");
                        return true;
                    case R.id.planToTake:
                        editStatus.setText("Plan to Take");
                        return true;
                    default:return false;
                }
            }
        });
        statusMenu.show();
    }

    private  void updateLabelStart(){
        editStartDate.setText(sdf.format(startDateCalendar.getTime()));
    }

    private  void updateLabelEnd(){
        editEndDate.setText(sdf.format(endDateCalendar.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_add, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.termList:
                Intent intent = new Intent(CourseAdd.this, TermList.class);
                startActivity(intent);
            case R.id.shareNote:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, notes);
                sendIntent.putExtra(Intent.EXTRA_TITLE, name);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifyStart:
                // Start notification
                Long triggerStart = startDate.getTime();
                Intent startIntent = new Intent(CourseAdd.this, MyReceiver.class);
                startIntent.putExtra("key", name + " starts today.");
                PendingIntent startSender = PendingIntent.getBroadcast(CourseAdd.this, MainActivity.numAlert++, startIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerStart, startSender);
                return true;
            case R.id.notifyEnd:
                // End notification
                Long triggerEnd = endDate.getTime();
                Intent endIntent = new Intent(CourseAdd.this, MyReceiver.class);
                endIntent.putExtra("key", name + " ends today.");
                PendingIntent endSender = PendingIntent.getBroadcast(CourseAdd.this, MainActivity.numAlert++, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerEnd, endSender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveButton(View view) {
        if (startDate.compareTo(endDate) > 0) {
            Toast.makeText(CourseAdd.this, "End Date must be after Start Date.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            if (repo.getAllCourses().isEmpty())
                id = 1;
            else {
                id = repo.getAllCourses().get(repo.getAllCourses().size() - 1).getCourseId() + 1;
            }
            Course course = new Course(id, editName.getText().toString(), startDate, endDate, editStatus.getText().toString(), editInstructor.getText().toString(), editNumber.getText().toString(), editEmail.getText().toString(), termId, editNotes.getText().toString());
            repo.insert(course);
            Toast.makeText(CourseAdd.this, "Course has been saved." +
                    "\n You will now be taken to term list.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CourseAdd.this, TermList.class);
            startActivity(intent);
        }
    }

}