package com.rutgersapp.Sniper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rutgersapp.R;

/**
 * Class Picker for picking classes and making the request
 */

public class SniperPicker extends Fragment {
    View v;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view =    inflater.inflate(R.layout.class_picker,container,false);

        v = view;
        return view;
    }



}
