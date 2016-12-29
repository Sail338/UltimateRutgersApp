package com.rutgersapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActiveBusses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_busses);
        //ideally load from a cache, but lets to that later
        APIREQ apireq = new APIREQ();
        try{
            apireq.getActiveStops(new ResponseValue() {
                @Override
                public void onResponse(Object value) {
                        final String x = value.toString();
                        runOnUiThread(new Runnable() {
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
                                            li.add(o.getString("title"));
                                        }
                                    }
                                    Log.d("LENGtH", li.get(0));

                                    ListView view = (ListView) findViewById(R.id.listView);
                                    view.setAdapter(new ArrayAdapter<String>(ActiveBusses.this, android.R.layout.simple_list_item_1, li));


                                } catch (Exception e){
                                    Log.e("ERR",e.getMessage());
                                }
                            }
                        });



                }
            });

        } catch ( Exception e){

        }
    }
}
