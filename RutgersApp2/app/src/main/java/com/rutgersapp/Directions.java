package com.rutgersapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
        req.getBuildings(new ResponseValue() {
            @Override
            public void onResponse(Object value) {

            }
        });
        ArrayAdapter<String> s = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line);

        super.onActivityCreated(savedInstanceState);
    }
}
