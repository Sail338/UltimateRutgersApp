package com.rutgersapp;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by hari on 12/28/16.
 */
public class APIREQ {

        public static final String base = "https://rumobile.rutgers.edu/";
        public  static final String APIVERSION = "2/";
        public static final String activestops = "nbactivestops.txt";
        //BuildString for active Stops and make async Request
        public  void getActiveStops(final ResponseValue value) throws IOException {
            OkHttpClient client = new OkHttpClient();
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



}
