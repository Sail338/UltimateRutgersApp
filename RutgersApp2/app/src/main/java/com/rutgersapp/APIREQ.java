package com.rutgersapp;

import android.util.Log;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hari on 12/28/16.
 */
public class APIREQ {

        public static final String base = "https://rumobile.rutgers.edu/";
        public  static final String APIVERSION = "2/";
        public static final String activestops = "nbactivestops.txt";
        public static  final String nextbusbase = "http://webservices.nextbus.com/service/publicJSONFeed?a=rutgers&command=";
        public  static final String routeConfig = "routeConfig";
        //BuildString for active Stops and make async Request
        public  void getActiveStops(final ResponseValue value) throws IOException {
            final OkHttpClient client = new OkHttpClient();
            String reponsestr;
             Request request = new Request.Builder().url(base+APIVERSION+activestops).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //throw an Error
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){

                        value.onResponse(response.body().string());

                    }

                }
            });

        }
        //get all routes
        public  void getRouteConfig(final ResponseValue value){
            String url = nextbusbase + routeConfig;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(nextbusbase+routeConfig).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //throw error
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){
                        value.onResponse(response.body().string());
                    }

                }
            });
        }

        public void getPredictionsForStops(ArrayList<String> stopid, String routeId, final ResponseValue value){
            //build URL
            String url = nextbusbase+"predictionsForMultiStops";
            for(String s:stopid){
                url += "&stops="+routeId + "%7Cnull%7C"+s;

            }
            OkHttpClient client = new OkHttpClient();
            Log.d("URL",url);
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //throw error
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){
                        value.onResponse(response.body().string());
                    }

                }
            });
        }


}
