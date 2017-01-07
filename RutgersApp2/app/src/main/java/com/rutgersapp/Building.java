package com.rutgersapp;

/**
 * Created by hari on 1/7/17.
 */
public class Building {
    private String title;
    private  String latitude;
    private  String longitude;
    public  Building(String title,String longitude,String latitude){
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Override
    public  String toString(){
        return  this.title;
    }

}
