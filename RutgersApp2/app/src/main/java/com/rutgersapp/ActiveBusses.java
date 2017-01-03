package com.rutgersapp;


import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //Fragment that returns a ListView
    HashMap<String,String>  nametoRoute = new HashMap<>();
    @Override
    public View  onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
         View view =    inflater.inflate(R.layout.activity_active_busses,container,false);
        return view;
    }

  @Override

  public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      //create tabs and shinnegins'




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

                              // View view = inflater.inflate(R.layout.activity_active_busses,container,true);
                              //ListView list;
                              //list = (ListView)view.findViewById(R.id.list);
                              setListAdapter(new ArrayAdapter<String >(getActivity(),android.R.layout.simple_list_item_1,li));
                              // getListView().setAdapter(new ArrayAdapter<String >(this,android.R.layout.simple_list_item_1,li));
                              //list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, li));
                              //list.setNestedScrollingEnabled(true);
                              getListView().setOnItemClickListener(new ItemClick());
                              //CREATE onclcik listener
                              //list.setOnItemClickListener(new ItemClick());


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


    public void selectItem(String position) {
        BusList list = new BusList();
        Bundle args = new Bundle();
        args.putString("routeid",position);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        list.setArguments(args);


        manager.beginTransaction().replace(R.id.content_frame
                ,list).addToBackStack(null).commit();




    }
    public class ItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectItem(nametoRoute.get(parent.getItemAtPosition(position).toString()));

        }


    }
}
