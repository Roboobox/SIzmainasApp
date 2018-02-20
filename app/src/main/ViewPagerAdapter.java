package com.avg.roboo.stunduizmainas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roboo on 09.12.2017.
 */


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                HomeFragment hf = new HomeFragment();
                return hf;


            case 2:
                StunduIzmainasFragment stf = new StunduIzmainasFragment();
                return stf;


            case 1:
                InfoActivity inf = new InfoActivity();
                return inf;


            case 3:
                KlasesIzmainasFragment kf = new KlasesIzmainasFragment();
                return kf;

            case 4:
                SettingsFragment sf = new SettingsFragment();
                return sf;


            default:
                return null;

        }
        //return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    public void clearFragments(){
        mFragmentList.clear();
    }

}