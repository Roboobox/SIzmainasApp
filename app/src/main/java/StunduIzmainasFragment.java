package com.avg.roboo.stunduizmainas;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

/**
 * Created by roboo on 18.06.2017.
 */

public class StunduIzmainasFragment extends Fragment{
    private static CustomAdapter adapter;
    //private static CustomAdapter adapter2;
    Boolean noChanges = false;
    InputStream inputstr;
    String line;
    String result;
    String resultD;
    String dateCurrent;
    String dateNext;
    String dateCurrentFormat;
    String dateNextFormat;


    String[] data;
    private boolean todaySelected = true;
    ArrayList<ListModel> stunduIzmainasSdn;
    //ArrayList<ListModel> stunduIzmainasRit;
    TextView stunduIzmainasTitle;
    ImageView imageIzmainunav;
    private ImageButton nextDay;
    ArrayList<ListModel> klasesIzmainas = new ArrayList<ListModel>();
    ListView listView;
    //ListView listView2;
    TextView tv;
    View v;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stundu_izmainas, container,false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RelativeLayout layoutRelative = (RelativeLayout) view.findViewById(R.id.relativeLayStundas);

        imageIzmainunav = (ImageView)view.findViewById(R.id.imageIzmainuNav);

        listView = (ListView) view.findViewById(R.id.stunduListSdn);
        //listView2 = (ListView) view.findViewById(R.id.stunduListRit);
        listView.setSelector(R.drawable.list_selector);
        stunduIzmainasSdn = new ArrayList<>();
       // stunduIzmainasRit = new ArrayList<>();

        //stunduIzmainasSdn.add(new ListModel("5", "10.3", "Latvijas un pasaules vēsture", "137 Kabinets"));
        //stunduIzmainasSdn.add(new ListModel("2", "12.4", "Sports", "Nenotiek"));
        //stunduIzmainasSdn.add(new ListModel("1", "9.1", "Fizika", "136 Kabinets"));
        //stunduIzmainasRit.add(new ListModel("1", "9.1", "Fizika", "136 Kabinets"));
        getDateInfo();
        getData();


        sortList();


        //stunduIzmainasRit.add(new ListModel("6", "7.2", "Latv. val.", "Papildus"));

        adapter = new CustomAdapter(stunduIzmainasSdn, getActivity().getApplicationContext());
        //adapter2 = new CustomAdapter(stunduIzmainasRit, getActivity().getApplicationContext());

        listView.setAdapter(adapter);
        //listView2.setAdapter(adapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListModel dataModel = stunduIzmainasSdn.get(position);

                //Snackbar.make(view, dataModel.getKlase() + "\n" + dataModel.getInfo() + " API: " + dataModel.getStunda(), Snackbar.LENGTH_LONG)
                //        .setAction("No action", null).show();
//                Snackbar snack  = Snackbar.make(layoutRelative, "Info: " + dataModel.getInfo(), Snackbar.LENGTH_LONG);
//                View viewSnack = snack.getView();
//                TextView tv = (TextView)viewSnack.findViewById(android.support.design.R.id.snackbar_text);
//                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorOrange));
//                snack.setAction("AIZVĒRT", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        })
//                       .show();
                //Toast.makeText(getContext().getApplicationContext(), dataModel.getInfo(), Toast.LENGTH_SHORT).show();
            }
        });

//        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                ListModel dataModel = stunduIzmainasRit.get(position);
//
//                //Snackbar.make(view, dataModel.getKlase() + "\n" + dataModel.getInfo() + " API: " + dataModel.getStunda(), Snackbar.LENGTH_LONG)
//                //        .setAction("No action", null).show();
//                Toast.makeText(getContext().getApplicationContext(), dataModel.getInfo(), Toast.LENGTH_SHORT).show();
//            }
//        });


        for(int i = 0; i<listView.getCount(); i++){
            v = listView.getAdapter().getView(i, null, null);
            tv = (TextView) v.findViewById(R.id.textKlase);
            tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
            tv.setBackgroundColor(Color.RED);
            int color = Color.argb( 200, 255, 64, 64 );
            tv.setBackgroundColor( color );
        }

        stunduIzmainasTitle = (TextView)view.findViewById(R.id.stunduIzmTitle);

        stunduIzmainasTitle.setText(dateCurrentFormat);

        nextDay = (ImageButton)view.findViewById(R.id.buttonNextDayChanges);
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(todaySelected) {
                    stunduIzmainasTitle.setText(dateNextFormat);
                    todaySelected = false;
                }
                else{
                    todaySelected = true;
                    stunduIzmainasTitle.setText(dateCurrentFormat);
                }
                listView.setVisibility(View.VISIBLE);
                imageIzmainunav.setVisibility(View.GONE);
                stunduIzmainasSdn.clear();
                klasesIzmainas.clear();
                getData();
                sortList();
                adapter.notifyDataSetChanged();
                listView.setSelectionAfterHeaderView();
                //listView.setVisibility(View.GONE);
            }
        });


        return view;
    }







    public void getData(){
        result = SplashScreen.result;

        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;

            data = new String[ja.length()];

            for(int i=0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);
                data[i] = jo.getString("prieksmets");
                if(todaySelected) {
                    getIzmainas(SplashScreen.dateCurrent, jo);
                }
                else{
                    getIzmainas(SplashScreen.dateNext, jo);
                }
            }
            if(todaySelected && stunduIzmainasSdn.isEmpty()) {
                listView.setVisibility(View.INVISIBLE);
                imageIzmainunav.setVisibility(View.VISIBLE);
            }
            if(!todaySelected && stunduIzmainasSdn.isEmpty()){
                listView.setVisibility(View.INVISIBLE);
                imageIzmainunav.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getDateInfo(){
        dateCurrent = SplashScreen.dateCurrent;
        dateNext = SplashScreen.dateNext;
        dateCurrentFormat = SplashScreen.dateCurrentFormat;
        dateNextFormat = SplashScreen.dateNextFormat;

    }

    private void sortList(){
        Collections.sort(stunduIzmainasSdn, new Comparator<ListModel>() {
            @Override
            public int compare(ListModel change, ListModel change1)
            {
                return Integer.valueOf(change.getStundasNum()).compareTo(Integer.valueOf(change1.getStundasNum()));
            }
        });

        for(int i=0; i<stunduIzmainasSdn.size(); i++){
            if(stunduIzmainasSdn.get(i).getKlase().equals(SettingsFragment.klase)){
                klasesIzmainas.add(stunduIzmainasSdn.get(i));
                //stunduIzmainasSdn.remove(i);
            }
        }
        if(!klasesIzmainas.isEmpty()) {
            Collections.sort(klasesIzmainas, new Comparator<ListModel>() {
                @Override
                public int compare(ListModel change, ListModel change1) {
                    return Integer.valueOf(change.getStundasNum()).compareTo(Integer.valueOf(change1.getStundasNum()));
                }
            });

            stunduIzmainasSdn.removeAll(klasesIzmainas);

            for (int i = 0; i < klasesIzmainas.size(); i++) {
                stunduIzmainasSdn.add(i, klasesIzmainas.get(i));
            }
        }
    }

    public void getIzmainas(String date, JSONObject jo) throws JSONException{
        if (jo.getString("datums").equals(date)) {
            if (!(jo.getString("prieksmets").equals("") && jo.getString("text").equals(""))) {
//                if(jo.get("prieksmets").equals("Fakultatīvas nodarbības vēsturē")){
//                    stunduIzmainasSdn.add(new ListModel(jo.getString("stunda"), jo.getString("klase"), "Fakult. nodarb. vēsturē", jo.getString("text")));
//                }
//                else {
                    if(jo.getString("klase").contains(",")){
                        int dotCounter = 0;
                        String text = jo.getString("klase");
                        for (int i = 0; i<text.length(); i++){
                            if( text.charAt(i) == '.'){
                                dotCounter++;
                            }
                        }
                        if(dotCounter == 1) {
                            String klase = "";
                            ArrayList<String> klases = new ArrayList<String>();
                            text = text.replaceAll("\\s+", "");
                            String[] items = text.split("[.,]");


                            for (int o = 0; o < items.length; o++) {
                                if (o == 0) {
                                    klase = items[o];
                                } else {
                                    klases.add(klase + "." + items[o]);
                                }
                            }


                            for (int p = 0; p < klases.size(); p++) {
                                stunduIzmainasSdn.add(new ListModel(jo.getString("stunda"), klases.get(p), jo.getString("prieksmets"), jo.getString("text")));
                            }
                        }

                    }
                    else{
                        stunduIzmainasSdn.add(new ListModel(jo.getString("stunda"), jo.getString("klase"), jo.getString("prieksmets"), jo.getString("text")));
                    }


//                }
            }
        }
    }

}
