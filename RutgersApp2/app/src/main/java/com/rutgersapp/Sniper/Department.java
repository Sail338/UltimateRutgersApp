package com.rutgersapp.Sniper;

/**
 * Created by hari on 1/14/17.
 */
public class Department {
    public  String courseid;
    public  String coursename;
    public  Department(String courseid, String coursename){
        this.courseid = courseid;
        this.coursename = coursename;
    }

    public String getCourseid() {
        return courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    @Override
    public String toString(){
        return  this.coursename;
    }
}
