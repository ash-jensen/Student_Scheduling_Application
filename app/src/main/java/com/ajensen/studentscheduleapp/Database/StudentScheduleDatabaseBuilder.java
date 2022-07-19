package com.ajensen.studentscheduleapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ajensen.studentscheduleapp.DAO.AssessmentDAO;
import com.ajensen.studentscheduleapp.DAO.CourseDAO;
import com.ajensen.studentscheduleapp.DAO.TermDAO;
import com.ajensen.studentscheduleapp.Entity.Assessment;
import com.ajensen.studentscheduleapp.Entity.Course;
import com.ajensen.studentscheduleapp.Entity.Term;

@Database(entities={Term.class, Course.class, Assessment.class}, version = 6, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class StudentScheduleDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile StudentScheduleDatabaseBuilder INSTANCE;

    // Gets an instance of the database
    static StudentScheduleDatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized(StudentScheduleDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    StudentScheduleDatabaseBuilder.class,
                                    "StudentScheduleDatabase")
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
