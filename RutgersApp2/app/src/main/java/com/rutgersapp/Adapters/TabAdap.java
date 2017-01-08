package com.rutgersapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import com.rutgersapp.Bus.ActiveBusses;
import com.rutgersapp.Bus.ActiveStops;

/**
 * Created by hari on 1/2/17.
 */
public class TabAdap extends FragmentPagerAdapter {
    public TabAdap(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("POS",Integer.toString(position));
        switch (position){
            case 0:

                return  new ActiveBusses();

            case 1:
                return new ActiveStops();
         //       return new ActiveBusses();
             default:
                 throw new IndexOutOfBoundsException();
        }



    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Busses";
        }
        else if(position ==1){
            return "Routes";
        }
        else{
            return "IDK";
        }
    }
}
