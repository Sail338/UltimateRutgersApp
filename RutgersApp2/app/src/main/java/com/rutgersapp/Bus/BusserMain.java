package com.rutgersapp.Bus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rutgersapp.R;
import com.rutgersapp.Adapters.TabAdap;

/**
 * Created by hari on 1/3/17.
 */
public class BusserMain extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view =  inflater.inflate(R.layout.pager,container,false);
        TabAdap adap = new TabAdap(getChildFragmentManager());
        final ViewPager pager = (ViewPager)view.findViewById(R.id.viewPager);
        pager.setAdapter(adap);
        final TabLayout layout = (TabLayout)view.findViewById(R.id.tabs);
        //ayout.addTab(layout.newTab(),0);
        layout.post(new Runnable() {
            @Override
            public void run() {
                layout.setupWithViewPager(pager);
            }
        });

        layout.setTabMode(TabLayout.MODE_FIXED);

       //pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(layout));


        return view;
    }


}
