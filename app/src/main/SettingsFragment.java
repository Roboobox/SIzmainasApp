package com.avg.roboo.stunduizmainas;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
//import android.util.Log;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by roboo on 19.06.2017.
 */

public class SettingsFragment extends Fragment {
    String result;
    String[] data;
    static String klase;
    List<String> items;
    private int check = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings, container,false);

        check = 0;

        Spinner spinnerKlase = (Spinner)v.findViewById(R.id.spinnerKlase);
        items = new ArrayList<String>();

        getAllKlases();
        sortKlases();



        Switch notificationSwitch = (Switch)v.findViewById(R.id.switchNotifications);

        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("Settings", MODE_PRIVATE);
        notificationSwitch.setChecked(sharedPrefs.getBoolean("notificationsEnabled", true));

        notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notificationSwitch.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Settings", MODE_PRIVATE).edit();
                    editor.putBoolean("notificationsEnabled", true);
                    Log.d("SubscribtionCheck", "Subscribed to " + klase);
                    FirebaseMessaging.getInstance().subscribeToTopic(klase);
                    editor.commit();
                }
                else{
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Settings", MODE_PRIVATE).edit();
                    editor.putBoolean("notificationsEnabled", false);
                    Log.d("SubscribtionCheck", "Unsubscribed from " + klase);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(klase);
                    //Log.d("Firebase", "Unsubscribing from " + klase);
                    editor.commit();
                }
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.custom_spinner, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerKlase.setAdapter(dataAdapter);


        SharedPreferences sharedPref = getActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        int spinnerValue = sharedPref.getInt("userChoiceSpinner",-1);
        if(spinnerValue != -1) {
            // set the selected value of the spinner
            spinnerKlase.setSelection(spinnerValue, false);
        }


        spinnerKlase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("SubscribtionCheck", check + " check");
                if(true) {
                    Log.d("SubscribtionCheck", check + " checkin");
                    //Log.d("Firebase", "Check is " + check);
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    if (sharedPrefs.getBoolean("notificationsEnabled", true)) {
                        Log.d("SubscribtionCheck", "Unsubscribed from " + klase);
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(klase);
                        //Log.d("Firebase", "Unsubscribing from " + klase);
                    }

                    klase = selectedItem;

                    if (sharedPrefs.getBoolean("notificationsEnabled", true)) {
                        Log.d("SubscribtionCheck", "Subscribed to " + klase);
                        FirebaseMessaging.getInstance().subscribeToTopic(klase);
                        //Log.d("Firebase", "Subscribing to " + klase);
                    } else {
                        //Log.d("Firebase", "Can't subscribe - Notifications disabled");
                    }
                    int userChoice = spinnerKlase.getSelectedItemPosition();
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("Settings", 0);
                    SharedPreferences.Editor prefEditor = sharedPref.edit();
                    prefEditor.putInt("userChoiceSpinner", userChoice);
                    prefEditor.putString("userChoiceKlase", selectedItem);
                    prefEditor.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return v;
    }



    public void getAllKlases(){
        result = SplashScreen.resultKlases;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;

            data = new String[ja.length()];

            for(int i=0; i<ja.length(); i++){

                jo = ja.getJSONObject(i);
                if(!(jo.getString("klase").contains(",") || jo.getString("klase").contains("-"))){
                    if(!jo.getString("klase").equals("")) {
                        if(!jo.getString("klase").substring(jo.getString("klase").length()-1).equals(".")){
                            items.add(jo.getString("klase"));
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sortKlases(){
        Comparator<String> cmp = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Float.valueOf(o1).compareTo(Float.valueOf(o2));
            }
        };
        Collections.sort(items, cmp);
    }

}
