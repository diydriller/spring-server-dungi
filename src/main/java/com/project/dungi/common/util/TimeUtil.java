package com.project.dungi.common.util;

import java.time.LocalDateTime;

public class TimeUtil {

    public enum DAY {
        MON, TUE, WED, THU, FRI , SAT, SUN
    };

    public static String localDateTimeToTimeStr(LocalDateTime time){
        return String.join("/",
                String.valueOf(time.getMonthValue()),
                String.valueOf(time.getDayOfMonth()),
                String.valueOf(time.getHour()),
                String.valueOf(time.getMinute())
        );
    }

    public static LocalDateTime timeStrToLocalDateTime(String time){
        String[] timeArr = time.split("/");
        int year = Integer.parseInt(timeArr[0]);
        int month = Integer.parseInt(timeArr[1]);
        int day = Integer.parseInt(timeArr[2]);
        int hour = Integer.parseInt(timeArr[3]);
        int minutes = Integer.parseInt(timeArr[4]);
        return LocalDateTime.of(year, month, day, hour, minutes);
    }

    public static LocalDateTime timeStrToTodayLocalDateTime(String time){
        String[] timeArr = time.split("/");
        int hour = Integer.parseInt(timeArr[0]);
        int minutes = Integer.parseInt(timeArr[1]);
        return LocalDateTime.of(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue(),
                LocalDateTime.now().getDayOfMonth(),
                hour,
                minutes
        );
    }
}
