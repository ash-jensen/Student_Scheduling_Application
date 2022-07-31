package com.ajensen.studentscheduleapp.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "courses", foreignKeys = @ForeignKey(entity = Term.class,
                parentColumns = "termId", childColumns = "termId"),
                indices = {@Index(value = {"termId"})})
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String courseName;
    private Date startDate;
    private Date endDate;
    private String status;
    private String instructor;
    private String number;
    private String email;
    private String notes;
    private int termId;

    public Course(int courseId, String courseName, Date startDate, Date endDate,
                  String status, String instructor, String number, String email,
                  int termId, String notes) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructor = instructor;
        this.email = email;
        this.number = number;
        this.termId = termId;
        this.notes = notes;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String phoneNumber) {
        this.number = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", instructor='" + instructor + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + number + '\'' +
                ", termId=" + termId +
                ", note='" + notes + '\'' +
                '}';
    }
}
