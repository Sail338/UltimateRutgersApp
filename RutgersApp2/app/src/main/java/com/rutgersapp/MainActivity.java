package com.rutgersapp;

import android.content.Intent;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items = new ArrayList<>();
    private DrawerLayout mDrawerlayout;
    private ListView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items.add("Bus");
        mDrawerlayout = (DrawerLayout)findViewById(R.id.drawlaw);
        view = (ListView)findViewById(R.id.left_drawer);
        view.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item,items));
        view.setOnItemClickListener(new DrawerItemClickListener());


    }
    public void selectItem(int position) {
        Intent intent = null;
        switch(position) {
            case 0:
                intent = new Intent(this, ActiveBusses.class);
                break;
            default :
                intent = new Intent(this, MainActivity.class); // Activity_0 as default
                break;
        }

        startActivity(intent);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
//            mDrawerlayout.closeDrawer(view);
        }


		//change

    }

}
