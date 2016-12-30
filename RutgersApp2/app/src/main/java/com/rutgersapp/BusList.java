package com.rutgersapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by hari on 12/30/16.
 */
public class BusList extends ListFragment {
    @Override
    public View  onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Created","CREATE");
        final APIREQ apireq = new APIREQ();
        try {
            Bundle args = getArguments();
           final String route = args.getString("routeid");
            //String stopid = args.getString("stopid");
            final ArrayList<String> stoplist = new ArrayList<>();
            stoplist.add("gibbons");
            apireq.getRouteConfig(new ResponseValue() {
                @Override
                public void onResponse(Object value) {
                    //parse all the stoplists according to the route then populate stoplist
                    //populate stoplist
                    //add gibbons for testing for now
                    apireq.getPredictionsForStops(stoplist, route, new ResponseValue() {
                        @Override
                        public void onResponse(Object value) {
                            final String val = value.toString();
                            //parse returned XML populate a new listview
                            Log.d("STOPS",val.toString());
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        }
                    });

                }
            });
            //make predctions for each stop for the specfic route in stoplist



        } catch (Exception e){
            Log.e("ERR",e.getMessage());
        }
        return  getView();
    }

}
