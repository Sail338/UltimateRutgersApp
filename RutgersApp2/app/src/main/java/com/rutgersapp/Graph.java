package com.rutgersapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.*;

//Priya's pet file

public class Graph<T>{
    private ArrayList<T> nodes=new ArrayList<T>();
    public Graph(ArrayList<T> nodeList){
        nodes=nodeList;
    }
    public Graph(){
    }
    public void addNode(T node){
        nodes.add(node);
    }
    public T getNode(int i){
        return nodes.get(i);
    }
}
class Stop{
    String name;
    String campus;
    Arc[] arcs;
    public Stop(String n, String c,int numArcs){
        name=n;
        campus=c;
        arcs=new Arc[numArcs];
        //add in all arcs, gotta rip from website to see which buses go where
    }
    public String toString(){
        return "Stop "+name+" located on "+campus+" campus";
    }
}
class Arc{
    private Stop orig;
    private Stop next;
    String name;
    int[] predictions=new int[5];
    boolean active;
    public Arc(Stop origStop, Stop nextStop, String busName){
        orig=origStop;
        next=nextStop;
        name=busName;
        populatePredictions();
    }
    private void populatePredictions(){
        //populate predictions
    }
    public String toString(){
        return name+" bus will go from "+orig+" to "+next;
    }


}
 class Building {
    private String title;
    private  String latitude;
    private  String longitude;
    public  Building(String title,String longitude,String latitude){
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //DONT CHANGE THE TOSTRING, THE AUTO COMPLETE USES THIS
    @Override
    public  String toString(){
        return  this.title;
    }

    //if needed can write another method to output tittle, lat and lng

}
