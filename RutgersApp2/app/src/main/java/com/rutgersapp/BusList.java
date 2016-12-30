package com.rutgersapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;


import java.util.ArrayList;
import java.util.logging.XMLFormatter;

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
            final Bundle args = getArguments();
           final String route = args.getString("routeid");
            //String stopid = args.getString("stopid");
            final ArrayList<String> stoplist = new ArrayList<>();
            stoplist.add("gibbons");
            apireq.getRouteConfig(new ResponseValue() {
                @Override
                public void onResponse(Object value) {
                    try {
                        JSONObject obj = new JSONObject(value.toString());
                        JSONArray arr = obj.getJSONArray("route");
                        for(int i = 0;i<arr.length();i++){
                            if(arr.getJSONObject(i).getString("tag").equals(args.getString("routeid"))){
                              JSONArray stoparr =  arr.getJSONObject(i).getJSONArray("stop");
                              Log.d("STOPS",stoparr.toString());
                              for(int j =0;j<stoparr.length();j++){
                                  stoplist.add(stoparr.getJSONObject(j).getString("tag"));
                              }
                              //  Log.d("obj",arr.getJSONObject(i).getString("stop"));

                            }

                        }


                        //parse all the stoplists according to the route then populate stoplist
                        //populate stoplist
                        //add gibbons for testing for now
                        apireq.getPredictionsForStops(stoplist, route, new ResponseValue() {
                            @Override
                            public void onResponse(Object value) {
                                final String val = value.toString();

                                Log.d("STOPS", val.toString());
                                ArrayList<String> predictons;
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                });

                            }
                        });

                    }catch ( Exception e){
                        Log.e("BIGERR",e.getMessage());
                    }
                }
            });

            //make predctions for each stop for the specfic route in stoplist



        } catch (Exception e){
            Log.e("ERR",e.getMessage());
        }
        return  getView();
    }

}
