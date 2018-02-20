package com.avg.roboo.stunduizmainas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by roboo on 04.07.2017.
 */

public class KlasesIzmainasFragment extends Fragment {
    public static String daySelected = "Pirmdiena";
    ArrayList<Fragment> list;
    public ImageButton buttonRight;
    public ImageButton buttonLeft;

    public Button buttonAdd;

    public TextView text;
    KlasesIzmainasPirmdienaFragment pirmdienaFragment = new KlasesIzmainasPirmdienaFragment();
    KlasesIzmainasOtrdienaFragment otrdienaFragment = new KlasesIzmainasOtrdienaFragment();
    KlasesIzmainasTresdienaFragment tresdienaFragment = new KlasesIzmainasTresdienaFragment();
    KlasesIzmainasCeturtdienaFragment ceturtdienaFragment = new KlasesIzmainasCeturtdienaFragment();
    KlasesIzmainasPiektdienaFragment piektdienaFragment = new KlasesIzmainasPiektdienaFragment();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.klases_izmainas, container,false);
        Log.d("FragmentSwitcher", "day fragment switcher");
        //KlasesIzmainasPirmdienaFragment f = new KlasesIzmainasPirmdienaFragment();
        getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, pirmdienaFragment).commit();
        text = (TextView)v.findViewById(R.id.textDiena);

        buttonLeft = (ImageButton)v.findViewById(R.id.buttonSwitchLeft);
        buttonLeft.setClickable(true);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextDayFragmentLeft();
                text.setText(daySelected);
            }
        });

        buttonRight = (ImageButton)v.findViewById(R.id.buttonSwitchRight);
        buttonRight.setClickable(true);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextDayFragmentRight();
                text.setText(daySelected);
            }
        });
        Log.d("FragmentSwitcher", "curr day call " + SplashScreen.nameOfDayCurrent);
        switch (SplashScreen.nameOfDayCurrent) {
            case "Mon":
                daySelected = "Pirmdiena";
                getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, pirmdienaFragment).commit();
                break;
            case "Tue":
                daySelected = "Otrdiena";
                getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, otrdienaFragment).commit();
                break;
            case "Wed":
                daySelected = "Trešdiena";
                getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, tresdienaFragment).commit();
                break;
            case "Thu":
                daySelected = "Ceturtdiena";
                getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, ceturtdienaFragment).commit();
                break;
            case "Fri":
                daySelected = "Piektdiena";
                getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, piektdienaFragment).commit();
                break;
        }

        text.setText(daySelected);
        Log.d("FragmentSwitcher", "day " + daySelected);
        return v;
    }


    public void nextDayFragmentRight(){
        switch (daySelected) {
            case "Pirmdiena":
                KlasesIzmainasPirmdienaFragment.saveList();
                Log.d("SwitchSave", "switch pirmdiena");
                daySelected = "Otrdiena";
                getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, otrdienaFragment).commit();
                break;
            case "Otrdiena":
                KlasesIzmainasOtrdienaFragment.saveList();
                Log.d("SwitchSave", "switch otrdiena");
                daySelected = "Trešdiena";
                getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, tresdienaFragment).commit();
                break;
            case "Trešdiena":
                KlasesIzmainasTresdienaFragment.saveList();
                Log.d("SwitchSave", "switch tresdiena");
                daySelected = "Ceturtdiena";
                getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, ceturtdienaFragment).commit();
                break;
            case "Ceturtdiena":
                KlasesIzmainasCeturtdienaFragment.saveList();
                Log.d("SwitchSave", "switch cetrutdiena");
                daySelected = "Piektdiena";
                getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, piektdienaFragment).commit();
                break;
            case "Piektdiena":
                KlasesIzmainasPiektdienaFragment.saveList();
                Log.d("SwitchSave", "switch piektdiena");
                daySelected = "Pirmdiena";
                getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, pirmdienaFragment).commit();
                break;
        }
    }
    public void nextDayFragmentLeft(){
        if(daySelected.equals("Pirmdiena")){
            daySelected = "Piektdiena";
            getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, piektdienaFragment).commit();

        }
        else if(daySelected.equals("Otrdiena")){
            daySelected = "Pirmdiena";
            getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, pirmdienaFragment).commit();

        }
        else if(daySelected.equals("Trešdiena")){
            daySelected = "Otrdiena";
            getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, otrdienaFragment).commit();

        }
        else if(daySelected.equals("Ceturtdiena")){
            daySelected = "Trešdiena";
            getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, tresdienaFragment).commit();
        }
        else if(daySelected.equals("Piektdiena")){
            daySelected = "Ceturtdiena";
            getFragmentManager().beginTransaction().replace(R.id.klasesIzmainasFrame, ceturtdienaFragment).commit();

        }
    }
}

