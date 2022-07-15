package com.ajensen.studentscheduleapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ajensen.studentscheduleapp.Database.Repository;
import com.ajensen.studentscheduleapp.Entity.Term;
import com.ajensen.studentscheduleapp.R;

import java.util.Date;

public class TermEdit extends AppCompatActivity {
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    int id;
    String name;
    String startDate;
    String endDate;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);
        editName = findViewById(R.id.editName);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        editName.setText(name);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
        repo = new Repository(getApplication());
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
            term = new Term(newId, editName.getText().toString(), new Date(), new Date());
            repo.insert(term);
        }
        else {
            term = new Term(id, editName.getText().toString(), new Date(), new Date());
            repo.update(term);
        }
    }
}