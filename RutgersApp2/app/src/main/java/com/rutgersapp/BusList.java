package com.rutgersapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;



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
            final Bundle args = getArguments();
           final String route = args.getString("routeid");
            //String stopid = args.getString("stopid");
            final ArrayList<String> stoplist = new ArrayList<>();

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
                                try {
                                    Log.d("STOPS", val.toString());
                                    final ArrayList<String> predictons = new ArrayList<String>();
                                    JSONObject obj = new JSONObject(value.toString());
                                    Log.d("SOMESTUFF",obj.getJSONArray("predictions").toString());
                                    for(int i=0;i<obj.getJSONArray("predictions").length();i++) {
                                        String add = obj.getJSONArray("predictions").getJSONObject(i).getString("stopTitle") +'\n';
                                        JSONArray big_arr =obj.getJSONArray("predictions").getJSONObject(i).getJSONObject("direction").getJSONArray("prediction");
                                        for (int j = 0; j < big_arr.length(); j++) {
                                            String toadd = big_arr.getJSONObject(j).getString("minutes");
                                            if(toadd.equals("0")){
                                                toadd = "<1";
                                            }
                                            if(j!=big_arr.length()-1) {
                                                add += toadd + " " + ",";
                                            }
                                            else{
                                                add += toadd + " " +" min";
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
                                            view1.setClickable(false);
                                            view1.setOnItemClickListener(null);


                                        }
                                    });

                                }catch (Exception e){
                                    Log.e("HUGERR",e.getMessage());
                                }
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