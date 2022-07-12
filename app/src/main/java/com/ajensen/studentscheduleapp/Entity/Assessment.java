package com.ajensen.studentscheduleapp.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "assessments",
        foreignKeys = @ForeignKey(entity = Course.class,
                parentColumns = "courseId",
                childColumns = "courseId"))
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assmtId;
    private String assmtName;
    private String assmtType;
    private Date startDate;
    private Date endDate;
    private int courseId;

    public Assessment(int assmtId, String assmtName, String assmtType, Date startDate, Date endDate, int courseId) {
        this.assmtId = assmtId;
        this.assmtName = assmtName;
        this.assmtType = assmtType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseId = courseId;
    }

    public int getAssmtId() {
        return assmtId;
    }

    public void setAssmtId(int assmtId) {
        this.assmtId = assmtId;
    }

    public String getAssmtName() {
        return assmtName;
    }

    public void setAssmtName(String assmtName) {
        this.assmtName = assmtName;
    }

    public String getAssmtType() {
        return assmtType;
    }

    public void setAssmtType(String assmtType) {
        this.assmtType = assmtType;
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "assmtId=" + assmtId +
                ", assmtName='" + assmtName + '\'' +
                ", assmtType='" + assmtType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", courseId=" + courseId +
                '}';
    }
}
