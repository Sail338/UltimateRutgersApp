package com.rutgersapp;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.rutgersapp.Adapters.TabAdap;
import com.rutgersapp.Bus.BusserMain;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items = new ArrayList<>();
    private DrawerLayout mDrawerlayout;
    private ListView view;
    private TabAdap adap;
    private TabLayout tabLayout;
    private ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Add items to main menue bar
        items.add("Bus");
        items.add("Directions");
        mDrawerlayout = (DrawerLayout)findViewById(R.id.drawlaw);
        view = (ListView)findViewById(R.id.left_drawer);
        view.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item,items));
        view.setOnItemClickListener(new DrawerItemClickListener());
        //pager = (ViewPager)findViewById(R.id.view_pager);
       // pager.setVisibility(View.GONE);



    }
    public void selectItem(int position) {
        switch (position) {
            //switch case to switch between Fragments
            case 0:
               Fragment fragment = new BusserMain();
                Bundle args = new Bundle();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.content_frame,fragment).addToBackStack(null).commit();
                break;
            case 1:
                Fragment frag = new Directions();
                Bundle arg = new Bundle();
                FragmentManager manag = getSupportFragmentManager();
                manag.beginTransaction().replace(R.id.content_frame,frag).addToBackStack(null).commit();
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
