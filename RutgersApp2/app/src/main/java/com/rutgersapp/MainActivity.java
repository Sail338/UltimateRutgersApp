package com.rutgersapp;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private ArrayList<String> items = new ArrayList<>();
    private DrawerLayout mDrawerlayout;
    private ListView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Add items to main menue bar
        items.add("Bus");
        mDrawerlayout = (DrawerLayout)findViewById(R.id.drawlaw);
        view = (ListView)findViewById(R.id.left_drawer);
        view.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item,items));
        view.setOnItemClickListener(new DrawerItemClickListener());



    }
    public void selectItem(int position) {
        switch (position) {
            //switch case to switch between Fragments
            case 0:
                ListFragment fragment = new ActiveBusses();
                Bundle args = new Bundle();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.content_frame,fragment).commit();
                break;


        }

    }
    //ListViews onClickListenr for NavBar
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);

//            mDrawerlayout.closeDrawer(view);
        }


		//change

    }

}
