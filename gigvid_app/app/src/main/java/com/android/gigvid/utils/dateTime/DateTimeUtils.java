package com.android.gigvid.utils.dateTime;

import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ScheduleDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {



    public static ScheduleDateTime getScheduleDateTime(String dateTime){

        ScheduleDateTime scheduleDateTime = new ScheduleDateTime();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXXXX");
            LocalDateTime dateTimeObj = LocalDateTime.parse(dateTime, formatter);
            String month = dateTimeObj.getMonth().name();
            String monthVal = month.substring(0, Math.min(month.length(), 3));
            scheduleDateTime.setMonth(monthVal);

            String date = String.valueOf(dateTimeObj.getDayOfMonth());
            scheduleDateTime.setDate(date);

            String hour = String.valueOf(dateTimeObj.getHour());
            String min = String.valueOf(dateTimeObj.getMinute());

            String time = hour+ ":"+min;

            scheduleDateTime.setTime(time);
        }
        return scheduleDateTime;
    }

    public static ScheduleDateTime getSchedDtTime(String dateTime){
        String[] arr = dateTime.split(" ");
        ScheduleDateTime scheduleDateTime = new ScheduleDateTime();
        scheduleDateTime.setDate(arr[0]);
        scheduleDateTime.setMonth(arr[1]);
        scheduleDateTime.setTime(arr[4]+arr[5]);
        return scheduleDateTime;

    }
}
