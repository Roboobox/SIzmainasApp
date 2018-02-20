package com.avg.roboo.stunduizmainas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by roboo on 03.07.2017.
 */

public class KlasesIzmainasOtrdienaFragment extends Fragment {
    private static CustomAdapterKlases adapter;
    static ArrayList<KlasesStundasModel> klasesStundasOtrdiena;
    private static Context context = null;
    private List<String> subjects;
    ListView listView;
    //    EditText text1;
//    EditText text2;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otrdiena, container,false);

        context = getActivity();
        subjects = new ArrayList<String>();

        listView = (ListView) view.findViewById(R.id.klasesStundasOtrdiena);
        klasesStundasOtrdiena = new ArrayList<>();
        loadList();
        loadSubjects();


        adapter = new CustomAdapterKlases(klasesStundasOtrdiena, getActivity().getApplicationContext(), getActivity());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                KlasesStundasModel dataModel = klasesStundasOtrdiena.get(position);

                TextView tv = (TextView)view.findViewById(R.id.textStundNum);

                //Snackbar.make(view, dataModel.getKlase() + "\n" + dataModel.getInfo() + " API: " + dataModel.getStunda(), Snackbar.LENGTH_LONG)
                //        .setAction("No action", null).show();
                if(tv.getCurrentTextColor() == ContextCompat.getColor(getActivity(), R.color.colorOrange)) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    View customView = getActivity().getLayoutInflater().inflate(R.layout.dialog_klases_izmainas, null);
                    TextView stundaOld = (TextView) customView.findViewById(R.id.textIzmainasStundaOld);
                    TextView kabinetsOld = (TextView) customView.findViewById(R.id.textIzmainasKabinetsOld);
                    TextView prieksmetsOld = (TextView) customView.findViewById(R.id.textIzmainasPrieksmetsOld);
                    TextView stundaNew = (TextView) customView.findViewById(R.id.textIzmainasStundaNew);
                    TextView klaseNew = (TextView) customView.findViewById(R.id.textIzmainasKlaseNew);
                    TextView prieksmetsNew = (TextView) customView.findViewById(R.id.textIzmainasPrieksmetsNew);
                    TextView infoNew = (TextView) customView.findViewById(R.id.textIzmainasInfoNew);
                    Button buttonOk = (Button) customView.findViewById(R.id.buttonOkIzmainas);

                    stundaOld.setText(klasesStundasOtrdiena.get(position).getStundasNum());
                    kabinetsOld.setText(klasesStundasOtrdiena.get(position).getKabinets());
                    prieksmetsOld.setText(klasesStundasOtrdiena.get(position).getStunda());

                    if (!CustomAdapterKlases.izmainas.isEmpty()) {
                        for (int i = 0; i < CustomAdapterKlases.izmainas.size(); i++) {
                            if (CustomAdapterKlases.izmainas.get(i).getStundasNum().equals(klasesStundasOtrdiena.get(position).getStundasNum())) {
                                stundaNew.setText(CustomAdapterKlases.izmainas.get(i).getStundasNum());
                                klaseNew.setText(CustomAdapterKlases.izmainas.get(i).getKlase());
                                prieksmetsNew.setText(CustomAdapterKlases.izmainas.get(i).getStunda());
                                infoNew.setText(CustomAdapterKlases.izmainas.get(i).getInfo());
                                if(prieksmetsNew.getLineCount() == 2){
                                    while(prieksmetsNew.getLineCount() == 2) {
                                        prieksmetsNew.setTextSize(prieksmetsNew.getTextSize() - 2);
                                    }
                                }
                            }
                        }
                    }

//                    prieksmetsNew.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            int lineCount = prieksmetsNew.getLineCount();
//                            Log.d("Split", prieksmetsNew.getLineCount() + " Line count");
//                            if(prieksmetsNew.getLineCount() == 2){
//                                while(prieksmetsNew.getLineCount() == 2) {
//                                    Log.d("Split", prieksmetsNew.getTextSize() + " size");
//                                    Log.d("Split", prieksmetsNew.getTextB + " size");
//                                    Log.d("Split", prieksmetsNew.getTextSize() + " size");
//
//                                    Log.d("Split", (prieksmetsNew.getTextSize() - 10) + " NEW size");
//                                    prieksmetsNew.setTextSize(TypedValue.COMPLEX_UNIT_PX, prieksmetsNew.getTextSize() - 10);
//                                }
//                            }

                            // Use lineCount here
                     //   }
//                    });
//
                    mBuilder.setView(customView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
                //Toast.makeText(getContext().getApplicationContext(), dataModel.getKabinets(), Toast.LENGTH_SHORT).show();
            }
        });



//        LayoutInflater inflaterView = getActivity().getLayoutInflater();
//        View row = inflaterView.inflate(R.layout.klases_stunda, container, false);
//        ImageButton deleteImageView = (ImageButton) row.findViewById(R.id.buttonDelete);
//        deleteImageView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.d("Button", "Button delete pressed");
//                Toast.makeText(getActivity(), "Delete" ,Toast.LENGTH_LONG).show();
//            }
//        });

        final View viewD = inflater.inflate(R.layout.klases_izmainas, container, false);

//        Button button = (Button)viewD.findViewById(R.id.buttonAdd);
//        button.setClickable(true);
//        EditText userKabinets = (EditText)view.findViewById(R.id.editKabinets);
//        EditText userStunda = (EditText)view.findViewById(R.id.editStunda);
//        EditText userPrieksmets = (EditText)view.findViewById(R.id.editPrieksmets);

        Button button = (Button)view.findViewById(R.id.buttonPievienot);
        //EditText userKabinets = (EditText)view.findViewById(R.id.editKab);
        //EditText userStunda = (EditText)view.findViewById(R.id.editStun);
        //EditText userPrieksmets = (EditText)view.findViewById(R.id.editPrksm);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(!(userKabinets.getText().toString().equals("") || userPrieksmets.getText().toString().equals("") || userStunda.getText().toString().equals(""))){
                //klasesStundasOtrdiena.add(new KlasesStundasModel(userStunda.getText().toString(), userPrieksmets.getText().toString(), userKabinets.getText().toString()));
                //adapter.notifyDataSetChanged();

                //}
                //Log.d("Settings", "Button pressed");

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View customView = getActivity().getLayoutInflater().inflate(R.layout.dialog_pievienot_stundu, null);
                EditText stunda = (EditText)customView.findViewById(R.id.editDialogStunda);
                EditText kabinets = (EditText)customView.findViewById(R.id.editDialogKabinets);
                AutoCompleteTextView autoPrieksmets = (AutoCompleteTextView)customView.findViewById(R.id.autoCompletePrieksmets);
                //Spinner spinnerPrieksmets = (Spinner)customView.findViewById(R.id.spinnerDialogPrieksmets);
                Button buttonPievienot = (Button)customView.findViewById(R.id.buttonPievienotDialog);

                String[] subjectsStringArray = subjects.toArray(new String[0]);
                //Log.d("Button", subjectsStringArray[1]);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.drop_down_suggestion_list, subjectsStringArray);

                autoPrieksmets.setAdapter(adapter);

                ArrayAdapter<String> dataAdapterSubjects = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.custom_spinner_pievienotst, subjects);
                dataAdapterSubjects.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //spinnerPrieksmets.setAdapter(dataAdapterSubjects);


                mBuilder.setView(customView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


                buttonPievienot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!(stunda.getText().toString().isEmpty() || kabinets.getText().toString().isEmpty() || autoPrieksmets.getText().toString().isEmpty())){
                            klasesStundasOtrdiena.add(new KlasesStundasModel(stunda.getText().toString(), autoPrieksmets.getText().toString(), kabinets.getText().toString()));
                            adapter.notifyDataSetChanged();
                            sortList();
                            saveList();
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(getActivity(), "Aizpildiet visus tukšos laukus" ,Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });








//        final EditText edittext = (EditText) view.findViewById(R.id.textStundasNosauk);
//        edittext.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    for (int i = 0; i < klasesStundasOtrdiena.size(); i++){
//                        Log.d("List", klasesStundasOtrdiena.get(i).getStunda() + " izmainas");
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });

//        buttonAdd = (Button)view.findViewById(R.id.buttonAdd);
//        stunda = (EditText)view.findViewById(R.id.editStunda);
//        kabinets = (EditText)view.findViewById(R.id.editKabinets);
//        prieksmets = (EditText)view.findViewById(R.id.editPrieksmets);
//        buttonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                klasesStundasOtrdiena.add(new KlasesStundasModel(stunda.getText().toString(), prieksmets.getText().toString(), kabinets.getText().toString()));
//            }
//        });
//        text1.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                for(int i=0; i<klasesStundasOtrdiena.size(); i++){
//                    if(klasesStundasOtrdiena.get(i).getStunda().equals(klasesStundasOtrdiena.getSelected()))
//                }
//            }
//        });

        sortList();


        return view;
    }
    public static void saveList(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(klasesStundasOtrdiena);

        editor.putString("OtrdienaStundas", json);
        editor.commit();
    }

    private void loadList(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("OtrdienaStundas", null);
        Type type = new TypeToken<ArrayList<KlasesStundasModel>>() {}.getType();
        if(gson.fromJson(json, type) != null) {
            klasesStundasOtrdiena = gson.fromJson(json, type);
        }
    }

    private void loadSubjects(){
        String result;
        result = SplashScreen.resultSubjects;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;

            subjects.add("Priekšmets");
            for(int i=0; i<ja.length(); i++){

                jo = ja.getJSONObject(i);
                subjects.add(jo.getString("prieksmets"));
                // Log.d("Button", subjects.get(i).toString());
            }
            sortSubjectsAlphabetically();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sortList() {
        Collections.sort(klasesStundasOtrdiena, new Comparator<KlasesStundasModel>() {
            @Override
            public int compare(KlasesStundasModel change, KlasesStundasModel change1) {
                return Integer.valueOf(change.getStundasNum()).compareTo(Integer.valueOf(change1.getStundasNum()));
            }
        });
    }

    public void sortSubjectsAlphabetically(){
        Collections.sort(subjects, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(!(o1.equals("Priekšmets") || o2.equals("Priekšmets"))) {
                    o1 = o1.replace("Ē", "E");
                    o2 = o2.replace("Ē", "E");
                    o1 = o1.replace("Ģ", "G");
                    o2 = o2.replace("Ģ", "G");
                    o1 = o1.replace("Ķ", "K");
                    o2 = o2.replace("Ķ", "K");
                    return o1.compareToIgnoreCase(o2);
                }
                return 0;
            }
        });
    }
}


