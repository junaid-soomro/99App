package com.example.dark.a99app.User;

/**
 * Created by abd on 30-Apr-18.
 */

public class HistoryModel {

    String name,dept,date_time,status;

    public HistoryModel(String name, String dept, String date_time, String status) {
        this.name = name;
        this.dept = dept;
        this.date_time = date_time;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getStatus() {
        return status;
    }
}
