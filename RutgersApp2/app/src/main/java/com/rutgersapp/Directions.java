package com.rutgersapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by hari on 1/5/17.
 */
public class Directions extends Fragment {
    View v;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view =    inflater.inflate(R.layout.directions,container,false);

        v = view;
        return view;
    }
    //make API request to directions and fill in the autocomplete

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //create APIREQUEST
        APIREQ req = new APIREQ();
     final   ArrayList<Building> buildings = new ArrayList<Building>();
        req.getBuildings(new ResponseValue() {
            @Override
            public void onResponse(Object value) {
                try{
                    //array list of building objects

                    JSONObject json = new JSONObject(value.toString());
                    JSONObject object = json.getJSONObject("all");
                    //grab the titiles and locations

                    //Iterate through all keys cause Rutgers is dumb and wond make this a JSON ARRAY WTF
                    Iterator<?> it = object.keys();
                    while (it.hasNext()){
                        String key = (String)it.next();
                        if(object.get(key)  instanceof  JSONObject) {

                            String title = object.getJSONObject(key).getString("title");
                            if(object.getJSONObject(key).optJSONObject("location")==null){
                                Log.d("LOCNULLAT",title);
                                //Hardcoding in some adresses cause Google cant find these
                                //SKELLY FEILD
                                //57 Dudley Rd, New Brunswick, NJ 08901
                                //Central Heating Plant
                                //ADR HERE
                                //if LOCATION IS NULL DO A REVERSELOOKUP
                                continue;
                            }
                            String lat = object.getJSONObject(key).optJSONObject("location").getString("latitude");
                            String lng = object.getJSONObject(key).optJSONObject("location").getString("longitude");
                            Building building = new Building(title,lng,lat);
                            buildings.add(building);
                        }
                    }
                    Log.d("BUILDINGSARR",buildings.toString());
                }catch ( Exception e){
                    Log.e("DIRECTIONJSONERR",e.getMessage());
                }

            }
        });
        ArrayAdapter<Building> s = new ArrayAdapter<Building>(getActivity(),android.R.layout.simple_dropdown_item_1line,buildings);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)v.findViewById(R.id.editText);
        autoCompleteTextView.setAdapter(s);
        AutoCompleteTextView autoCompleteTextView1  = (AutoCompleteTextView)v.findViewById(R.id.editText2);
        autoCompleteTextView1.setAdapter(s);

        super.onActivityCreated(savedInstanceState);
    }
}
