package com.ajensen.studentscheduleapp.Database;


import android.app.Application;

import com.ajensen.studentscheduleapp.DAO.AssessmentDAO;
import com.ajensen.studentscheduleapp.DAO.CourseDAO;
import com.ajensen.studentscheduleapp.DAO.TermDAO;
import com.ajensen.studentscheduleapp.Entity.Assessment;
import com.ajensen.studentscheduleapp.Entity.Course;
import com.ajensen.studentscheduleapp.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO termDAO;
    private CourseDAO courseDAO;
    private AssessmentDAO assessmentDAO;
    private List<Term> allTerms;
    private List<Course> allCourses;
    private List<Assessment> allAssessments;

    // Create threads
    private static final int NUMBER_OF_THREADS = 6;
    static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Repository constructor
    public Repository(Application application) {
        StudentScheduleDatabaseBuilder db = StudentScheduleDatabaseBuilder.getDatabase(application);
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();
    }

    // Term actions
    public void insert(Term term) {
        databaseExecutor.execute(()-> {
            termDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(()->{
            termDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term){
        databaseExecutor.execute(()->{
            termDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Term>getAllTerms(){
        databaseExecutor.execute(()->{
            allTerms = termDAO.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allTerms;
    }

    // Course actions
    public List<Course>getAllCourses(){
        databaseExecutor.execute(()->{
            allCourses = courseDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allCourses;
    }

    public void insert(Course course){
        databaseExecutor.execute(()->{
            courseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course){
        databaseExecutor.execute(()->{
            courseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course){
        databaseExecutor.execute(()->{
            courseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Assessment actions
    public List<Assessment>getAllAssessments(){
        databaseExecutor.execute(()->{
            allAssessments = assessmentDAO.getAllAssessments();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allAssessments;
    }

    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            assessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            assessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            assessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
