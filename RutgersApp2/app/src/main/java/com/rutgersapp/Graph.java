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
    private int[] predictions=new int[5];
    boolean active;
    public Arc(Stop origStop, Stop nextStop, String busName){
        orig=origStop;
        next=nextStop;
        name=busName;
        updatePredictions();
    }
    private void updatePredictions(){
        //populate predictions
    }
    public int[] getPredictions(){
        //updatePredictions(); uncomment this if you want to update every time you call
        return predictions;
    }
    public String toString(){
        return name+" bus will go from "+orig+" to "+next;
    }
}