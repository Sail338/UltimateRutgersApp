package com.rutgersapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.util.Pools;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.*;


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

              final   ProgressBar bar =(ProgressBar ) getActivity().findViewById(R.id.pbHeaderProgress);
              final  ListView lv = (ListView) getActivity().findViewById(R.id.listView);
                lv.setVisibility(View.GONE);
                bar.setVisibility(View.VISIBLE);
                apireq.getRouteConfig(new ResponseValue() {




                    @Override
                    public void onResponse(Object value) {
                        try {
                            JSONObject obj = new JSONObject(value.toString());
                            JSONArray arr = obj.getJSONArray("route");
                            for (int i = 0; i < arr.length(); i++) {
                                if (arr.getJSONObject(i).getString("tag").equals(args.getString("routeid"))) {
                                    JSONArray stoparr = arr.getJSONObject(i).getJSONArray("stop");
                                    Log.d("STOPS", stoparr.toString());
                                    for (int j = 0; j < stoparr.length(); j++) {
                                        stoplist.add(stoparr.getJSONObject(j).getString("tag"));
                                    }

                                    Log.d("STOPLIST", stoplist.toString());
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
                                    try {

                                        final ArrayList<String> predictons = new ArrayList<String>();
                                        JSONObject obj = new JSONObject(value.toString());

                                        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
                                        //Sort the predictions
                                        for (int i = 0; i < obj.getJSONArray("predictions").length(); i++) {
                                            jsonValues.add(obj.getJSONArray("predictions").getJSONObject(i));
                                        }
                                        Collections.sort(jsonValues, new Comparator<JSONObject>() {

                                            private static final String KEY_NAME = "stopTag";

                                            @Override
                                            public int compare(JSONObject a, JSONObject b) {

                                                try {
                                                    return (stoplist.indexOf(a.getString(KEY_NAME)) - stoplist.indexOf(b.getString(KEY_NAME)));
                                                } catch (Exception e) {
                                                    Log.e("FGERR", e.getMessage());

                                                }
                                                return 0;
                                                //if you want to change the sort order, simply use the following:
                                                //return -valA.compareTo(valB);
                                            }
                                        });


                                        for (int i = 0; i < obj.getJSONArray("predictions").length(); i++) {
                                            String add = jsonValues.get(i).getString("stopTitle") + '\n';
                                            Log.d("DIR",obj.getJSONArray("predictions").getJSONObject(i).getJSONObject("direction").toString());

                                            JSONArray big_arr = obj.getJSONArray("predictions").getJSONObject(i).getJSONObject("direction").optJSONArray("prediction");
                                            if(big_arr ==null){
                                                JSONObject ob = obj.getJSONArray("predictions").getJSONObject(i).getJSONObject("direction").optJSONObject("prediction");
                                                String toadd = ob.getString("minutes");
                                                add += toadd + "min";
                                            }


                                         else if(big_arr !=null) {
                                                for (int j = 0; j < big_arr.length(); j++) {
                                                    String toadd = big_arr.getJSONObject(j).getString("minutes");
                                                    if (toadd.equals("0")) {
                                                        toadd = "<1";
                                                    }
                                                    if (j != big_arr.length() - 1) {
                                                        add += toadd + "," + " ";
                                                    } else {
                                                        add += toadd + " " + " min";
                                                    }

                                                }
                                            }
                                            predictons.add(add);
                                        }

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                View view = inflater.inflate(R.layout.activity_active_busses, container, true);
                                                ListView view1;
                                                view1 = (ListView) view.findViewById(R.id.listView);
                                                view1.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, predictons));
                                                lv.setVisibility(View.VISIBLE);
                                                bar.setVisibility(View.GONE);
                                                view1.setClickable(false);
                                                view1.setOnItemClickListener(null);


                                            }
                                        });

                                    } catch (Exception e) {
                                        Log.e("HUGERR", e.getMessage());
                                    }
                                }
                            });

                        } catch (Exception e) {
                            Log.e("BIGERR", e.getMessage());
                        }
                    }
                });

                //make predctions for each stop for the specfic route in stoplist


            } catch (Exception e) {
                Log.e("ERR", e.getMessage());
            }

        return  getView();
    }

}
