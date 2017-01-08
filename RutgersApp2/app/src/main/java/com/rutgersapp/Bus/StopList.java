package com.rutgersapp.Bus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.rutgersapp.API.APIREQ;
import com.rutgersapp.R;
import com.rutgersapp.ResponseValue;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 1/3/17.
 */
public class StopList extends Fragment {
    View view;

    @Override

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Created", "CREATE");
        //create ViewPager


        View v = inflater.inflate(R.layout.buslist, container, false);
        view = v;
//        view.findViewById(R.id.tabs).setVisibility(View.GONE);


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final APIREQ apireq = new APIREQ();
        try {
            final Bundle args = getArguments();
            final String route = args.getString("stopid");
            //String stopid = args.getString("stopid");
            //final ArrayList<String> stoplist = new ArrayList<>();

            final ProgressBar bar = (ProgressBar) getActivity().findViewById(R.id.pbHeaderProgress);
            final ListView lv = (ListView) view.findViewById(R.id.ahahaha);

            lv.setVisibility(View.GONE);
            bar.setVisibility(View.VISIBLE);
            //make request
            try {
                apireq.getAllBusesThroughAStop(route, new ResponseValue() {
                    @Override
                    public void onResponse(Object value) {
                        Log.d("RETJSON", value.toString());
                        try {
                            JSONObject o = new JSONObject(value.toString());
                            //grab the prediction arr
                            final ArrayList<String> predictons = new ArrayList<String>();
                            JSONObject obj = new JSONObject(value.toString());

                            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
                            //Sort the predictions

                            if(obj.optJSONArray("predictions")==null){
                                jsonValues.add(obj.getJSONObject("predictions"));
                            }
                            else {
                                for (int i = 0; i < obj.optJSONArray("predictions").length(); i++) {
                                    jsonValues.add(obj.getJSONArray("predictions").getJSONObject(i));
                                }
                            }
                            for (int i = 0; i < obj.getJSONArray("predictions").length(); i++) {
                                String add = jsonValues.get(i).getString("routeTitle") + '\n';
                                if( jsonValues.get(i).optJSONObject("direction")==null){
                                    continue;
                                }
                                JSONArray big_arr = jsonValues.get(i).optJSONObject("direction").optJSONArray("prediction");
                                //For some dumb reason if only one thing then the prediction array doesnt exist,
                                if(big_arr ==null){
                                    JSONObject ob = jsonValues.get(i).getJSONObject("direction").optJSONObject("prediction");
                                    String toadd = ob.getString("minutes");
                                    add += toadd + "min";
                                }
                                //Put the Added String in The Adapter List
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
                                    // View view = inflater.inflate(R.layout.activity_active_busses, container, false);
                                    ListView view1;
                                    view1 = (ListView) view.findViewById(R.id.ahahaha);
                                    view1.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, predictons));
                                    view1.setVisibility(View.VISIBLE);
                                    bar.setVisibility(View.GONE);
                                    //     view1.setClickable(false);
                                    //       view1.setOnItemClickListener(null);


                                }
                            });


                        } catch (Exception e) {
                                Log.e("JSONERR",e.getMessage());
                        }

                    }
                });

            } catch (Exception e) {
                Log.e("Erronrquest",e.getMessage());
            }
            } catch (Exception e){

                    Log.e("Erronrquest",e.getMessage());

        }
        }}