package com.rutgersapp;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.ActionMenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActiveBusses extends ListFragment {
    //Fragment that returns a ListView
    HashMap<String,String >  nametoRoute = new HashMap<>();
    @Override
    public View  onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ideally load from a cache, but lets to that later
        final APIREQ apireq = new APIREQ();
        try{
            apireq.getActiveStops(new ResponseValue() {
                @Override
                public void onResponse(Object value) {
                        final String x = value.toString();

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {


                                try {

                                    //TODO after clicking on item display all stops
                                    //TODO add predictions
                                    JSONObject obj = new JSONObject(x);
                                    JSONArray arr = obj.getJSONArray("routes");
                                    ArrayList<String> li = new ArrayList<String>();
                                    int length = arr.length();
                                    for (int i = 0; i < length; i++) {
                                        JSONObject o = arr.getJSONObject(i);
                                        if (o.has("title")) {
                                            nametoRoute.put(o.getString("title"),o.getString("tag"));
                                            //make reuqest to gibbons
                                            //on clcik open a new Fragment
                                            li.add(o.getString("title"));

                                        }
                                    }
                                    Log.d("LENGtH", li.get(0));

                                    View view = inflater.inflate(R.layout.activity_active_busses,container,true);
                                    ListView list;
                                    list = (ListView)view.findViewById(R.id.listView);
                                    list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, li));
                                    //CREATE onclcik listener
                                    list.setOnItemClickListener(new ItemClick());


                                } catch (Exception e){
                                    Log.e("ERR",e.getMessage());
                                }



                                    }
                                });

                }
            });

        } catch ( Exception e){

        }
        return getView();
    }
    public void selectItem(String position) {
        BusList list = new BusList();
        Bundle args = new Bundle();
        args.putString("routeid",position);
        FragmentManager manager = getFragmentManager();
        list.setArguments(args);
        manager.beginTransaction().replace(R.id.content_frame,list).commit();


    }
    public class ItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(nametoRoute.get(parent.getItemAtPosition(position).toString()));


//            mDrawerlayout.closeDrawer(view);
        }


    }
}
