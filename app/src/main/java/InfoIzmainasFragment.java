package com.avg.roboo.stunduizmainas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by roboo on 07.08.2017.
 */

public class InfoIzmainasFragment extends Fragment{
    private static CustomAdapter_Info adapter;
    ArrayList<InfoListModel> infoIzmainas;
    ListView listView;
    String result;
    boolean isCurrentDaySelected = true;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info, container,false);


        listView = (ListView) view.findViewById(R.id.listInfo);

        infoIzmainas = new ArrayList<>();

        //infoIzmainas.add(new InfoListModel("Seminārā:", "T. Test", "/azvieto/"));


        adapter = new CustomAdapter_Info(infoIzmainas, getActivity().getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                InfoListModel dataModel = infoIzmainas.get(position);


                //Snackbar.make(view, dataModel.getKlase() + "\n" + dataModel.getInfo() + " API: " + dataModel.getStunda(), Snackbar.LENGTH_LONG)
                //        .setAction("No action", null).show();
                //Toast.makeText(getContext().getApplicationContext(), dataModel.getStunda(), Toast.LENGTH_SHORT).show();
            }
        });

        loadInfoIzCurr();
        loadSlimCurr();
        sortListAlphabetically();


        ImageButton buttonNext = (ImageButton) view.findViewById(R.id.buttonNextInfoDay);
        TextView datumsText = (TextView) view.findViewById(R.id.infoDatums);
        datumsText.setText(SplashScreen.dateCurrentFormat);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCurrentDaySelected){
                    infoIzmainas.clear();
                    loadInfoIzNext();
                    loadSlimNext();
                    datumsText.setText(SplashScreen.dateNextFormat);
                    isCurrentDaySelected = false;
                }
                else{
                    infoIzmainas.clear();
                    loadInfoIzCurr();
                    loadSlimCurr();
                    isCurrentDaySelected = true;
                    datumsText.setText(SplashScreen.dateCurrentFormat);

                }
                sortListAlphabetically();
                adapter.notifyDataSetChanged();
            }
        });


        return view;
    }
    private void loadSlimCurr(){
        result = SplashScreen.resultSlimCurr;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;


            for(int i=0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);

                infoIzmainas.add(new InfoListModel(jo.getString("kur") + ":", jo.getString("skol"), jo.getString("text")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadSlimNext(){
        result = SplashScreen.resultSlimNext;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;


            for(int i=0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);

                infoIzmainas.add(new InfoListModel(jo.getString("kur") + ":", jo.getString("skol"), jo.getString("text")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadInfoIzCurr(){
        result = SplashScreen.resultInfoIzCurr;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;


            for(int i=0; i<ja.length(); i++) {
                jo = ja.getJSONObject(i);

                infoIzmainas.add(new InfoListModel("Informācija:", jo.getString("info"), ""));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void loadInfoIzNext(){
        result = SplashScreen.resultInfoIzNext;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;


            for(int i=0; i<ja.length(); i++) {
                jo = ja.getJSONObject(i);

                infoIzmainas.add(new InfoListModel("Informācija:", jo.getString("info"), ""));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void sortListAlphabetically(){
        Collections.sort(infoIzmainas, new Comparator<InfoListModel>() {
            @Override
            public int compare(InfoListModel slim1, InfoListModel slim2) {
                return slim1.getKurinfo().compareToIgnoreCase(slim2.getKurinfo());
            }
        });
    }
}
