package com.rutgersapp;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        APIREQ apireq = new APIREQ();
        try {
            String resp;
            apireq.getActiveStops(new ResponseValue() {
                @Override
                public void onResponse(Object value) {
                    Log.d("RETURND", value.toString());
                }
            });

        } catch (IOException e){
            Log.e("ERR","BIG ERR");
        }
    }

}
