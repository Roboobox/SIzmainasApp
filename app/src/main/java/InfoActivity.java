package com.avg.roboo.stunduizmainas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by roboo on 07.08.2017.
 */

public class InfoActivity extends Fragment {

    private boolean isInfoNewsSelected = true;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.izmainas_info, container,false);

//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.changesItem:
//                                StunduIzmainasFragment stf = new StunduIzmainasFragment();
//                                getSupportFragmentManager().beginTransaction().replace(R.id.frame, stf).commit();
//                                break;
//
//                            case R.id.settingsItem:
//                                SettingsFragment sf = new SettingsFragment();
//                                getSupportFragmentManager().beginTransaction().replace(R.id.frame, sf).commit();
//                                break;
//
//                            case R.id.placeItem:
//                                KlasesIzmainasFragment kf = new KlasesIzmainasFragment();
//                                getSupportFragmentManager().beginTransaction().replace(R.id.frame, kf).commit();
//                                break;
//
//                            case R.id.homeItem:
//                                HomeFragment hf = new HomeFragment();
//                                getSupportFragmentManager().beginTransaction().replace(R.id.frame, hf).commit();
//                                break;
//                        }
//                        return true;
//                    }
//                });

        ImageButton button = (ImageButton)view.findViewById(R.id.imageButtonNextInfo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInfoNewsSelected){
                    isInfoNewsSelected = false;
                    InfoIzmainasFragment hf = new InfoIzmainasFragment();
                    getFragmentManager().beginTransaction().replace(R.id.frameInfoChanges, hf).commit();
                }
                else{
                    isInfoNewsSelected = true;
                    InfoNewsFragment hf = new InfoNewsFragment();
                    getFragmentManager().beginTransaction().replace(R.id.frameInfoChanges, hf).commit();
                }
            }
        });

        //InfoIzmainasFragment hf = new InfoIzmainasFragment();
        //getFragmentManager().beginTransaction().replace(R.id.frameInfoChanges, hf).commit();

        InfoNewsFragment hf = new InfoNewsFragment();
        getFragmentManager().beginTransaction().replace(R.id.frameInfoChanges, hf).commit();

        return view;
    }
}
