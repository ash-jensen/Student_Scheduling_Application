package com.ajensen.studentscheduleapp.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajensen.studentscheduleapp.Entity.Course;
import com.ajensen.studentscheduleapp.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> courseList;
    private final Context context;
    private final LayoutInflater inflater;

    // CourseView class and constructor
    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;

        // Constructor for the viewHolder
        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseItemTV);

            // Click takes you to courseDetails activity, holds all clicked course details
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = courseList.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("id", current.getCourseId());
                    intent.putExtra("name", current.getCourseName());
                    intent.putExtra("startDate", (current.getStartDate()).getTime());
                    intent.putExtra("endDate", (current.getEndDate()).getTime());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructor", current.getInstructor());
                    intent.putExtra("number", current.getNumber());
                    intent.putExtra("email", current.getEmail());
                    intent.putExtra("termId", current.getTermId());
                    intent.putExtra("notes", current.getNotes());
                    context.startActivity(intent);
                }
            });
        }
    }

    // Constructor for adapter
    public CourseAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    // Inflate the list item that you want to show
    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_item_course, parent, false);
        return new CourseAdapter.CourseViewHolder(itemView);
    }

    // Binds course to the textView
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(courseList != null) {
            Course current = courseList.get(position);
            String name = current.getCourseName();
            holder.courseItemView.setText(name);
        }
        else {
            holder.courseItemView.setText("No course name");
        }
    }

    // Fill courseList with courses
    public void setCourseList(List<Course> courses) {
        courseList = courses;
        notifyDataSetChanged();
    }

    // If courseList is empty, return zero, else return size
    @Override
    public int getItemCount() {
        if(courseList != null) {
            return courseList.size();
        }
        else {
            return 0;
        }
    }
}
