package com.ajensen.studentscheduleapp.UI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajensen.studentscheduleapp.Entity.Assessment;
import com.ajensen.studentscheduleapp.Entity.Course;
import com.ajensen.studentscheduleapp.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private List<Assessment> assessmentList;
    private final Context context;
    private final LayoutInflater inflater;

    // AssessmentView class and constructor
    class AssessmentViewHolder extends RecyclerView.ViewHolder{

        // DELETE
        private static final String TAG = "***ASSESSMENT ADAPTER***";

        private final TextView assessmentItemView;
        // Constructor for the viewHolder
        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentItemTV);
            // Click takes you to courseDetails activity, holds all clicked course details
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = assessmentList.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("id", current.getAssmtId());
                    intent.putExtra("name", current.getAssmtName());
                    intent.putExtra("type", current.getAssmtType());
                    intent.putExtra("startDate", (current.getStartDate()).getTime());
                    intent.putExtra("endDate", (current.getEndDate()).getTime());
                    intent.putExtra("courseId", current.getCourseId());
                    context.startActivity(intent);
                }
            });
        }
    }

    // Constructor for adapter
    public AssessmentAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    // Inflate the list item that you want to show
    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_item_assessment, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    // Binds course to the textView
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(assessmentList != null) {
            Assessment current = assessmentList.get(position);
            String name = current.getAssmtName();
            holder.assessmentItemView.setText(name);
        }
        else {
            holder.assessmentItemView.setText("No assessment name");
        }
    }

    // Fill courseList with courses
    public void setAssessmentsList(List<Assessment> assessments) {
        assessmentList = assessments;
        notifyDataSetChanged();
    }

    // If courseList is empty, return zero, else return size
    @Override
    public int getItemCount() {
        if(assessmentList != null) {
            return assessmentList.size();
        }
        else {
            return 0;
        }
    }
}
