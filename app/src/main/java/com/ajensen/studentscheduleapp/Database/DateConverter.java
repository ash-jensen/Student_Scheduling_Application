package com.ajensen.studentscheduleapp.Database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    // Takes in a long, converts to date for app use
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    // Takes in a date, returns a long for db use
    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null ? null : date.getTime();
    }
}

