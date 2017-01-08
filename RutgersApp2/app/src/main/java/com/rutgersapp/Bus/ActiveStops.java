package com.rutgersapp.Bus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.rutgersapp.API.APIREQ;
import com.rutgersapp.R;
import com.rutgersapp.ResponseValue;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hari on 1/3/17.
 */
public class ActiveStops extends ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //Fragment that returns a ListView
    HashMap<String,String> stoptoid = new HashMap<>();
    @Override
    //inflate and then return, use the same layout is active busses cause it doesnt rlly matter m8
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view =    inflater.inflate(R.layout.activity_active_busses,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final APIREQ apireq = new APIREQ();
        //make request
        try {
            apireq.getActiveStops(new ResponseValue() {
                @Override
                public void onResponse(Object value) {
                    //make a list of STOPS
                    final String x = value.toString();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<String> stops = new ArrayList<String>();
                            try {
                                JSONObject obj = new JSONObject(x);
                                JSONArray arr = obj.getJSONArray("stops");
                                for(int i = 0;i<arr.length();i++){
                                    stops.add(arr.getJSONObject(i).getString("title"));
                                }
                                setListAdapter(new ArrayAdapter<String >(getActivity(),android.R.layout.simple_list_item_1,stops));
                                //use hashmap of stops with its corressponding id
                                //first put everythin in the hashmap with null

                                for(String s:stops){
                                    stoptoid.put(s,null);
                                }

                                //make request to routeconfig again to get values
                             ;
                            }catch (Exception e){
                                    Log.e("ERRINACTIVESTOPS",e.getMessage());
                            }
                        }
                    });

                    apireq.getRouteConfig(new ResponseValue() {
                        @Override
                        public void onResponse(Object value) {
                            //iterate through keys and plop id when needed
                            //TODO cache this somehere
                            try{
                                JSONObject obj = new JSONObject(value.toString());
                                JSONArray routes = obj.getJSONArray("route");
                                for(int i =0;i<routes.length();i++){
                                    JSONArray stops = routes.getJSONObject(i).getJSONArray("stop");
                                    for(int j =0;j<stops.length();j++){
                                        stoptoid.put(stops.getJSONObject(j).getString("title"),stops.getJSONObject(j).getString("tag"));
                                    }

                                }
                                //setup onClickListener

                                getListView().setOnItemClickListener(new ItemClick_2());
                            }

                            catch (Exception e){
                                Log.e("ISSUEPARSING",e.getMessage());
                            }
                        }
                    });

                }
            });
        }catch(Exception e){
                Log.e("ISSUE withsomething",e.getMessage());
            }
    }

    public void selectItem(String position) {
        StopList list    = new StopList();
        Bundle args = new Bundle();
        args.putString("stopid",position);
        Log.d("IDIS",position);
      FragmentManager manager = getActivity().getSupportFragmentManager();
        list.setArguments(args);


        manager.beginTransaction().replace(R.id.content_frame
                ,list).addToBackStack(null).commit();




    }
    public class ItemClick_2 implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            selectItem(stoptoid.get(parent.getItemAtPosition(position).toString()));

        }


    }
}

