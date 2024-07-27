package com.example.myfinalproject;

public class Day {
    private String day;
    private String time;

    public Day(String day, String time) {
        this.day = day;
        this.time = time;
    }

    public Day(String day) {
        this.day = day;
        this.time="";
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean equals(Day obj) {
        return this.day.equals(obj.getDay());
    }
}
