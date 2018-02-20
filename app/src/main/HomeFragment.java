package com.avg.roboo.stunduizmainas;

import android.app.AlarmManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by roboo on 30.07.2017.
 */

public class HomeFragment extends Fragment {
    List<String> items;
    private static boolean isKlaseChoosen = false;
    SharedPreferences prefs = null;
    private static CustomAdapter_Klase adapter;
    private CustomAdapter_Info adapterInfo;
    ArrayList<InfoListModel> infoIzmainas;
    ArrayList<KlaseListModel> stunduIzmainasKlase;
    ListView listView;
    ListView listViewInfo;
    ImageView imageNavIzmainas;
    TextView textInfoHome;
    TextView textInfoHomeTitle;
    RelativeLayout infoHomeWindow;
    private String result;
    private String klase;
    public AlarmManager alarmManager;
    public ScrollView scrollView;
    private boolean noChanges = false;
    private ArrayList<String> vidusskola = new ArrayList<>();
    private ArrayList<String> pamatskola = new ArrayList<>();
    private ArrayList<String> skolotaji = new ArrayList<>();
    private ArrayList<KlaseListModel> izmainasCurrentDay = new ArrayList<>();
    private String vardaDiena;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_new, container, false);

        scrollView = (ScrollView)v.findViewById(R.id.ScrollView01);
        imageNavIzmainas = (ImageView)v.findViewById(R.id.imageKlasesIzmainasNav);
        textInfoHome = (TextView)v.findViewById(R.id.infoTodayHome);
        textInfoHomeTitle = (TextView)v.findViewById(R.id.textInfoHomeTitle);
        infoHomeWindow = (RelativeLayout)v.findViewById(R.id.relativeLayout8);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams ( RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT );



        prefs = getActivity().getSharedPreferences("Settings", MODE_PRIVATE);

        if (prefs.getBoolean("firstrun", true)) {

            // Do first run stuff here then set 'firstrun' as false

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View customView = getActivity().getLayoutInflater().inflate(R.layout.dialog_klases_izvele, null);

            items = new ArrayList<String>();
            Spinner spinnerKlase = (Spinner)customView.findViewById(R.id.spinnerKlasesIzvele);
            getAllKlases();
            sortKlases();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.custom_spinner, items);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerKlase.setAdapter(dataAdapter);

            spinnerKlase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    isKlaseChoosen = true;
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    int userChoice = spinnerKlase.getSelectedItemPosition();
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("Settings",0);
                    SharedPreferences.Editor prefEditor = sharedPref.edit();
                    prefEditor.putInt("userChoiceSpinner", userChoice);
                    prefEditor.putString("userChoiceKlase", selectedItem);
                    klase = selectedItem;
                    SettingsFragment.klase = klase;
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(items.get(0));
                    Log.d("SubscribtionCheck", "Unsubscribed from " + items.get(0));
                    FirebaseMessaging.getInstance().subscribeToTopic(klase);
                    Log.d("SubscribtionCheck", "Subscribed to " + klase);
                    stunduIzmainasKlase.clear();
                    getData();
                    adapter.notifyDataSetChanged();
                    prefEditor.commit();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Button buttonOk = (Button) customView.findViewById(R.id.buttonKlasesIzveleSave);


            mBuilder.setView(customView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();

            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isKlaseChoosen){
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(getActivity(), "Klase nav izvēlēta!" ,Toast.LENGTH_LONG).show();
                    }
                }
            });


            prefs.edit().putBoolean("firstrun", false).commit();



        }





////////////////////////////////Creating List///////////////////////////////////////////

        listViewInfo = (ListView) v.findViewById(R.id.listInfo);

        infoIzmainas = new ArrayList<>();

        //infoIzmainas.add(new InfoListModel("Seminārā:", "T. Test", "/azvieto/"));


        //loadSlimCurr();
        adapterInfo = new CustomAdapter_Info(infoIzmainas, getActivity().getApplicationContext());
        //listViewInfo.setAdapter(adapterInfo);
        //setListViewHeightBasedOnChildren(listViewInfo);
//        listViewInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                InfoListModel dataModel = infoIzmainas.get(position);
//
//
//                //Snackbar.make(view, dataModel.getKlase() + "\n" + dataModel.getInfo() + " API: " + dataModel.getStunda(), Snackbar.LENGTH_LONG)
//                //        .setAction("No action", null).show();
//                //Toast.makeText(getContext().getApplicationContext(), dataModel.getStunda(), Toast.LENGTH_SHORT).show();
//            }
//        });


        listView = (ListView) v.findViewById(R.id.klasesIzmainasList);
        stunduIzmainasKlase = new ArrayList<>();

        //Gets data from php output and puts it into list
        getData();

        if(!stunduIzmainasKlase.isEmpty()) {
            savePreviousChanges();
        }

        ///Sorts lessons by their number
        sortList();
//        if(!stunduIzmainasKlase.isEmpty()) {
//            Collections.sort(stunduIzmainasKlase, new Comparator<KlaseListModel>() {
//                @Override
//                public int compare(KlaseListModel change, KlaseListModel change1) {
//                    return change.getStundasNum().compareTo(change1.getStundasNum());
//                }
//            });
//        }

        adapter = new CustomAdapter_Klase(stunduIzmainasKlase, getActivity().getApplicationContext());


//        listView.setOnTouchListener(new ListView.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        // Disallow ScrollView to intercept touch events.
//                        v.getParent().requestDisallowInterceptTouchEvent(true);
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        // Allow ScrollView to intercept touch events.
//                        v.getParent().requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
//
//                // Handle ListView touch events.
//                v.onTouchEvent(event);
//                return true;
//            }
//        });


        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                KlaseListModel dataModel = stunduIzmainasKlase.get(position);

                Toast.makeText(getContext().getApplicationContext(), dataModel.getInfo(), Toast.LENGTH_SHORT).show();

            }
        });

//        listView.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                return (event.getAction() == MotionEvent.ACTION_MOVE);
//            }
//        });

/////////////////////////////////////////////////////////////////////////////////////



        TextView dzimsanasDienuSvinText = (TextView)v.findViewById(R.id.dzimsanasDienuSvin);
        ImageView giftIconImage =  (ImageView)v.findViewById(R.id.imageView);
        TextView splitText = (TextView)v.findViewById(R.id.textView103);
        TextView textP = (TextView)v.findViewById(R.id.pamatskolaText);
        TextView textSvinP = (TextView)v.findViewById(R.id.pamatskolaSvin);
        TextView textV = (TextView)v.findViewById(R.id.VidusskolaText);
        TextView textSvinV = (TextView)v.findViewById(R.id.vidusskolaSvin);
        TextView textS = (TextView)v.findViewById(R.id.skolotajiText);
        TextView textSvinS = (TextView)v.findViewById(R.id.skolotajiSvin);
        TextView vardaDienuSvin = (TextView)v.findViewById(R.id.vardaDienuSvin);
        getBirthdays();
        getDarbBirthdays();

        if(skolotaji.isEmpty() && vidusskola.isEmpty() && pamatskola.isEmpty()){
            dzimsanasDienuSvinText.setVisibility(View.GONE);
            giftIconImage.setVisibility(View.GONE);
            splitText.setVisibility(View.GONE);
        }
        if(pamatskola.isEmpty()){
            textSvinP.setVisibility(View.GONE);
            textP.setVisibility(View.GONE);
        }
        else{
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < pamatskola.size(); i++){
                builder.append(pamatskola.get(i) + "\n");
            }
            builder.setLength(builder.length() - 1);
            textSvinP.setText(builder.toString());
        }
        if(vidusskola.isEmpty()){
            textSvinV.setVisibility(View.GONE);
            textV.setVisibility(View.GONE);
        }
        else{
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < vidusskola.size(); i++){
                builder.append(vidusskola.get(i) + "\n");
            }
            builder.setLength(builder.length() - 1);
            textSvinV.setText(builder.toString());
        }
        if(skolotaji.isEmpty()){
            textSvinS.setVisibility(View.GONE);
            textS.setVisibility(View.GONE);
        }
        else{
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < skolotaji.size(); i++){
                builder.append(skolotaji.get(i) + "\n");
            }
            builder.setLength(builder.length() - 1);
            textSvinS.setText(builder.toString());
        }
        getNameDays();
        vardaDienuSvin.setText(vardaDiena);


        textInfoHomeTitle.setText("Informācija: " + SplashScreen.dateCurrentFormat);


        //scrollView.fullScroll(ScrollView.FOCUS_UP);
        //scrollView.pageScroll(View.FOCUS_UP);
        scrollView.smoothScrollTo(0,0);

        return v;
    }

    private void getBirthdays(){
        result = SplashScreen.resultDzim;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;


            for(int i=0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);

                if(Integer.parseInt(jo.getString("klase")) < 10){
                    pamatskola.add(jo.getString("jubilars"));

                }
                else{
                    vidusskola.add(jo.getString("jubilars"));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getDarbBirthdays(){
        result = SplashScreen.resultDarbDzim;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;


            for(int i=0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);

                skolotaji.add(jo.getString("skolotajs"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getAllKlases(){
        result = SplashScreen.resultKlases;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;


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

    private void getNameDays(){
        result = SplashScreen.resultVardD;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;


            for(int i=0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);
                vardaDiena = jo.getString("vardi");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getData(){
        result = SplashScreen.result;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;


            for(int i=0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);

                if(!(jo.getString("prieksmets").equals("") && jo.getString("text").equals("")) && (jo.getString("klase").equals(SettingsFragment.klase) || jo.getString("klase").contains(",") || jo.getString("klase").contains("-"))) {
                    if(jo.getString("klase").contains(",")) {
                        String text = jo.getString("klase");
                        String klase = "";
                        int dotCounter = 0;
                        for (int o = 0; o<text.length(); o++){
                            if( text.charAt(o) == '.'){
                                dotCounter++;
                            }
                        }
                        if(dotCounter == 1) {
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
                                if (klases.get(p).equals(SettingsFragment.klase)) {
                                    stunduIzmainasKlase.add(new KlaseListModel(jo.getString("stunda"), klases.get(p), jo.getString("prieksmets"), jo.getString("text"), jo.getString("datums")));
                                }
                            }
                        }
                    }
                    else if(jo.getString("klase").contains("-") && !(jo.getString("klase").contains(".") || jo.getString("klase").contains(","))){
                        String text = jo.getString("klase");
                        text = text.replaceAll("\\s+", "");
                        String[] items = text.split("[-]");
                        String userKlaseNum = SettingsFragment.klase.split("[.]")[0];

                        if(items.length == 2) {
                            try {
                                if ((Integer.parseInt(userKlaseNum) >= Integer.parseInt(items[0])) && (Integer.parseInt(userKlaseNum) <= Integer.parseInt(items[1]))) {
                                    stunduIzmainasKlase.add(new KlaseListModel(jo.getString("stunda"), text, jo.getString("prieksmets"), jo.getString("text"), jo.getString("datums")));
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                    }
                    else{
                        stunduIzmainasKlase.add(new KlaseListModel(jo.getString("stunda"), jo.getString("klase"), jo.getString("prieksmets"), jo.getString("text"), jo.getString("datums")));
                    }


                }
//                else{
//                    noChanges = true;
//                }
            }
            if(noChanges) {
                stunduIzmainasKlase.add(new KlaseListModel("", "", "Nav Izmaiņu", "", ""));
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
        //stunduIzmainasKlase.add(new KlaseListModel("1", "11.1", "Matematika", "testIzmaina", "22-12-2017"));
        //stunduIzmainasKlase.add(new KlaseListModel("3", "11.1", "Fizika", "testing", "22-12-2017"));
        //stunduIzmainasKlase.add(new KlaseListModel("6", "11.1", "Ķīmija", "kabinets", "22-12-2017"));
        //stunduIzmainasKlase.add(new KlaseListModel("0", "11.1", "Bioloģija", "testIzmaina", "22-12-2017"));

        if(stunduIzmainasKlase.isEmpty()){
            listView.setVisibility(View.INVISIBLE);
            imageNavIzmainas.setVisibility(View.VISIBLE);
        }
        else{
            listView.setVisibility(View.VISIBLE);
            imageNavIzmainas.setVisibility(View.GONE);
            setListViewHeightBasedOnChildren(listView);
            //RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams ( RelativeLayout.LayoutParams.WRAP_CONTENT,
            //        RelativeLayout.LayoutParams.WRAP_CONTENT );
            //lp.addRule(RelativeLayout.BELOW, R.id.listInfo);
            //textInfoHome.setLayoutParams(lp);
        }
        loadInfoCurrent();


//        for(int i = 0; i < stunduIzmainasKlase.size(); i++){
//            for (int o = 0; o < stunduIzmainasKlase.size(); o++){
//                if((stunduIzmainasKlase.get(i).getDatums() + stunduIzmainasKlase.get(i).getStunda() + stunduIzmainasKlase.get(i).getInfo() + stunduIzmainasKlase.get(i).getKlase() + stunduIzmainasKlase.get(i).getStundasNum()).equals(stunduIzmainasKlase.get(o).getDatums() + stunduIzmainasKlase.get(o).getStunda() + stunduIzmainasKlase.get(o).getInfo() + stunduIzmainasKlase.get(o).getKlase() + stunduIzmainasKlase.get(o).getStundasNum())){
//                    stunduIzmainasKlase.remove(o);
//                }
//            }
//
//        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 60;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void savePreviousChanges(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(stunduIzmainasKlase);

        editor.putString("userKlaseChangesPrevious", json);
        editor.commit();
    }

    private void sortList(){
        Collections.sort(stunduIzmainasKlase, new Comparator<KlaseListModel>() {
            @Override
            public int compare(KlaseListModel change, KlaseListModel change1)
            {
                return Integer.valueOf(change.getStundasNum()).compareTo(Integer.valueOf(change1.getStundasNum()));
            }
        });

        for(int i=0; i<stunduIzmainasKlase.size(); i++){

            if(stunduIzmainasKlase.get(i).getDatums().equals(SplashScreen.dateCurrent)){
                izmainasCurrentDay.add(stunduIzmainasKlase.get(i));
            }
        }
        if(!izmainasCurrentDay.isEmpty()) {
            Collections.sort(izmainasCurrentDay, new Comparator<KlaseListModel>() {
                @Override
                public int compare(KlaseListModel change, KlaseListModel change1) {
                    return Integer.valueOf(change.getStundasNum()).compareTo(Integer.valueOf(change1.getStundasNum()));
                }
            });

            stunduIzmainasKlase.removeAll(izmainasCurrentDay);

            for (int i = 0; i < izmainasCurrentDay.size(); i++) {
                stunduIzmainasKlase.add(i, izmainasCurrentDay.get(i));
            }
        }
        izmainasCurrentDay.clear();
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

    private void loadInfoCurrent(){
        infoIzmainas.clear();
        ArrayList<String> kur = new ArrayList<>();
        String fullInfo = "";
        result = SplashScreen.resultSlimCurr;

        boolean isAlreadyOnList = false;
        if(!(result.contains("null") && !result.contains("{"))) {
            try {
                JSONArray ja = new JSONArray(result);
                JSONObject jo = null;


                for (int i = 0; i < ja.length(); i++) {
                    isAlreadyOnList = false;
                    jo = ja.getJSONObject(i);

                    for (int o = 0; o < kur.size(); o++) {
                        if ((kur.get(o).equals(jo.getString("kur")))) {
                            isAlreadyOnList = true;
                        }
                    }
                    if (kur.size() == 0) {
                        kur.add(jo.getString("kur"));
                    } else if (!isAlreadyOnList) {
                        kur.add(jo.getString("kur"));
                    }

                    infoIzmainas.add(new InfoListModel(jo.getString("kur") + ":", jo.getString("skol"), jo.getString("text")));
                }


                for (int o = 0; o < kur.size(); o++) {
                    fullInfo += "<font color=#25B7D3>" + kur.get(o) + ": " + "</font>";
                    for (int i = 0; i < infoIzmainas.size(); i++) {
                        if (infoIzmainas.get(i).getKurinfo().equals(kur.get(o) + ":")) {
                            String s = infoIzmainas.get(i).getDarbinieksinfo();
                            String firstNameLetter = String.valueOf(infoIzmainas.get(i).getDarbinieksinfo().charAt(0));
                            s = firstNameLetter + "." + s.substring(s.indexOf((" ")) + 1);
                            if (infoIzmainas.get(i).getInfo().equals("") || infoIzmainas.get(i).getInfo().equals(" ")) {
                                fullInfo += s + ", ";
                            } else {
                                fullInfo += s + "(<font color=#06D6A0><i>" + infoIzmainas.get(i).getInfo() + "</i></font>)" + ", ";
                            }
                        }
                    }
                    fullInfo = fullInfo.replaceAll(", $", ".<br>");
                }


                infoIzmainas.clear();
                loadInfoIzCurr();

                if(!infoIzmainas.isEmpty()){
                    fullInfo += "<br>";
                }

                for(int i = 0; i < infoIzmainas.size(); i++){
                    fullInfo += "<b><font color=#25B7D3>" +infoIzmainas.get(i).getKurinfo()  + " </font></b>" + infoIzmainas.get(i).getDarbinieksinfo() + "<br>";
                }
                fullInfo = fullInfo.substring(0, fullInfo.length()-4);
//            fullInfo = "<table>";
//            for(int o =0; o<kur.size(); o++) {
//                fullInfo += fullInfo.replaceAll(", $", ".<br>");
//                fullInfo += "<tr><td><font color=#EA3C4E>" + kur.get(o) + ": " + "</font>";
//                for (int i = 0; i < infoIzmainas.size(); i++) {
//                    if (infoIzmainas.get(i).getKurinfo().equals(kur.get(o) + ":")) {
//                        String s = infoIzmainas.get(i).getDarbinieksinfo();
//                        String firstNameLetter = String.valueOf(infoIzmainas.get(i).getDarbinieksinfo().charAt(0));
//                        s = firstNameLetter + "." + s.substring(s.indexOf((" ")) + 1);
//                        if (infoIzmainas.get(i).getInfo().equals("") || infoIzmainas.get(i).getInfo().equals(" ")) {
//                            fullInfo += s + ", ";
//                        } else {
//                            fullInfo += s + "(<font color=#ffaf0f><i>" + infoIzmainas.get(i).getInfo() + "</i></font>)" + ", ";
//                        }
//                    }
//                }
//                fullInfo += "</td></tr>";
//            }
//            fullInfo+= "</table>";
                Log.d("Table", fullInfo);
                //textInfoHome.setText(Html.fromHtml(fullInfo), TextView.BufferType.SPANNABLE);
                if (!textInfoHome.equals("")) {
                    displayHTMLText(textInfoHome, fullInfo);
                } else {

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            infoHomeWindow.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("deprecation")
    public void displayHTMLText(TextView textView, String textToDisplay){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            textView.setText(Html.fromHtml(textToDisplay, Html.FROM_HTML_MODE_LEGACY));
        }
        else{
            textView.setText(Html.fromHtml(textToDisplay));
        }
    }






//    private int getInterval(){
//        int days = 1;
//        int hours = 24;
//        int minutes = 60;
//        int seconds = 60;
//        int milliseconds = 1000;
//        int repeatMS = days * hours * minutes * seconds * milliseconds;
//        return repeatMS;
//    }
}
