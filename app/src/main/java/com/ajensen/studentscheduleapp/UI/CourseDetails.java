package com.ajensen.studentscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.ajensen.studentscheduleapp.Entity.Assessment;
import com.ajensen.studentscheduleapp.Entity.Course;
import com.ajensen.studentscheduleapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {
    Repository repo ;
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
    List<Assessment> filteredAssessments;

    // DELETE
    private static final String TAG = "***Course Details***";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        // Set names for textEdits
        editName = findViewById(R.id.editName);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);
        editStatus = findViewById(R.id.editStatus);
        editInstructor = findViewById(R.id.editInstructor);
        editNumber = findViewById(R.id.editNumber);
        editEmail = findViewById(R.id.editEmail);
        editNotes = findViewById(R.id.editNotes);

        // Get info about course from courseList
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        startDate = new Date();
        startDate.setTime(getIntent().getLongExtra("startDate", -1));
        endDate = new Date();
        endDate.setTime(getIntent().getLongExtra("endDate", -1));
        status = getIntent().getStringExtra("status");
        instructor = getIntent().getStringExtra("instructor");
        number = getIntent().getStringExtra("number");
        email = getIntent().getStringExtra("email");
        termId = getIntent().getIntExtra("termId", -1);
        notes = getIntent().getStringExtra("notes");

        // Fill textEdits
        editName.setText(name);
        dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);
        startDateString = sdf.format(startDate);
        editStartDate.setText(startDateString);
        endDateString = sdf.format(endDate);
        editEndDate.setText(endDateString);
        editStatus.setText(status);
        editInstructor.setText(instructor);
        editNumber.setText(number);
        editEmail.setText(email);
        editNotes.setText(notes);
        repo = new Repository(getApplication());

        // Date Pickers
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
                new DatePickerDialog(CourseDetails.this, startDatePicker, startDateCalendar
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
                if (info.equals("")) info = "01/01/22";
                try {
                    endDateCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, endDatePicker, endDateCalendar
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

        // Fill assessmentListRV with assessments
        RecyclerView recyclerView = findViewById(R.id.assessmentListRV);
        Repository repo = new Repository(getApplication());
        // List<Assessment> assessments = repo.getAllAssessments();
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        filteredAssessments = new ArrayList<>();
        for(Assessment a:repo.getAllAssessments()) {
            if(a.getAssmtId() == id) {
                filteredAssessments.add(a);
            }
        }
        adapter.setAssessmentsList(filteredAssessments);

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
        getMenuInflater().inflate(R.menu.menu_course_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
// DO YOU WANT TERM LIST HERE?
            case R.id.shareNote:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, notes);
                sendIntent.putExtra(Intent.EXTRA_TITLE, name);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notify:
                // Start notification
                Long triggerStart = startDate.getTime();
                Intent startIntent = new Intent(CourseDetails.this, MyReceiver.class);
                startIntent.putExtra("key", name + " starts today.");
                PendingIntent startSender = PendingIntent.getBroadcast(CourseDetails.this, MainActivity.numAlert++, startIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerStart, startSender);
                // End notification
                Long triggerEnd = endDate.getTime();
                Intent endIntent = new Intent(CourseDetails.this, MyReceiver.class);
                endIntent.putExtra("key", name + " ends today.");
                PendingIntent endSender = PendingIntent.getBroadcast(CourseDetails.this, MainActivity.numAlert++, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerEnd, endSender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveEditsButton(View view) {
        Course course;
        if (id == -1) {
            int newId = repo.getAllCourses().get(repo.getAllCourses().size() -1).getCourseId() + 1;
            course = new Course(newId, editName.getText().toString(), startDate, endDate, editStatus.getText().toString(), instructor, number, email, termId, notes);
            repo.insert(course);
            Toast.makeText(CourseDetails.this, "Course has been saved." +
                    "\n You will now be taken to term list.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CourseDetails.this, TermList.class);
            startActivity(intent);
        }
        else {
            course = new Course(id, editName.getText().toString(), startDate, endDate, editStatus.getText().toString(), instructor, number, email, termId, notes);
            repo.update(course);
            Toast.makeText(CourseDetails.this, "Edits have been saved." +
                    "\n You will now be taken to term list.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CourseDetails.this, TermList.class);
            startActivity(intent);
        }
    }

    public void deleteButton(View view) {
        Course course = new Course(id, editName.getText().toString(), startDate, endDate, status, instructor, number, email, termId, notes);
        // check for assessments
        if (!filteredAssessments.isEmpty()) {
            Toast.makeText(CourseDetails.this, "Must delete associated assessments before deleting course.", Toast.LENGTH_LONG).show();
        }
        else {
            // If term has no associated courses, delete
            repo.delete(course);
            Toast.makeText(CourseDetails.this, "Course has been deleted." +
                    "\n You will now be taken to term list.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CourseDetails.this, TermList.class);
            startActivity(intent);
        }
    }

    // **** FIX ME ****
    public void AddAssessment(View view) {
        // Intent intent = new Intent(CourseDetails.this, AssessmentAdd.class);
        // startActivity(intent);

        Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }


}