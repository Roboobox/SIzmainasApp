package com.avg.roboo.stunduizmainas;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by roboo on 04.07.2017.
 */

public class CustomAdapterKlases extends ArrayAdapter<KlasesStundasModel> implements View.OnClickListener {
    private ArrayList<KlasesStundasModel> dataSet;
    static public ArrayList<ListModel> izmainas;
    ArrayList<TextView> test;
    Context mContext;
    Context ctx;

    // View lookup cache
    private static class ViewHolder {
        TextView txtStundaNum;
        TextView txtStunda;
        TextView txtKabinets;
        ImageButton imageButtonDelete;
    }

    public CustomAdapterKlases(ArrayList<KlasesStundasModel> data, Context context, Context ctx) {
        super(context, R.layout.activity_main, data);
        this.dataSet = data;
        this.ctx = ctx;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        KlasesStundasModel dataModel=(KlasesStundasModel) object;

        switch (v.getId())
        {
            //case R.id.item_info:
                //Snackbar.make(v, "Release date " +dataModel.getStunda(), Snackbar.LENGTH_LONG)
                //        .setAction("No action", null).show();
                //break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        KlasesStundasModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        test = new ArrayList<>();

        final View result;

        if (convertView == null) {

            viewHolder = new CustomAdapterKlases.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.klases_stunda, parent, false);
            viewHolder.txtStundaNum = (TextView) convertView.findViewById(R.id.textStundNum);
            viewHolder.txtStunda = (TextView) convertView.findViewById(R.id.textStundasNosauk);
            viewHolder.txtKabinets = (TextView) convertView.findViewById(R.id.textStundasKabinets);
            viewHolder.imageButtonDelete = (ImageButton) convertView.findViewById(R.id.buttonDelete);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapterKlases.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtStundaNum.setText(dataModel.getStundasNum());
        viewHolder.txtStunda.setText(dataModel.getStunda());
        viewHolder.txtKabinets.setText(dataModel.getKabinets());


        viewHolder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                View customView = LayoutInflater.from(mContext).inflate(R.layout.dialog_dzest, parent, false);
                Button buttonConfirm = (Button)customView.findViewById(R.id.buttonConfirm);
                Button buttonDecline = (Button)customView.findViewById(R.id.buttonDecline);

                mBuilder.setView(customView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                buttonConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("deleteTest", "set before deletion " + dataSet);
                        dataSet.remove(position);
                        Log.d("deleteTest", "deleting... new set is " + dataSet);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                buttonDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        getData();

        if(!izmainas.isEmpty()) {
            boolean isUserNumberEqualChangesNumber = false;
                for (int i = 0; i < izmainas.size(); i++) {
                    if (viewHolder.txtStundaNum.getText().toString().equals(izmainas.get(i).getStundasNum())) {
                        isUserNumberEqualChangesNumber = true;
                        //Log.d("List", viewHolder.txtStundaNum.getText().toString() + " " + viewHolder.txtStunda.getText().toString() + " " + viewHolder.txtKabinets.getText().toString());
                        //Log.d("List", izmainas.get(i).getStunda() + " " + izmainas.get(i).getStundasNum() + " " + izmainas.get(i).getKlase() + " " + izmainas.get(i).getInfo());
                    }
                }
                if(isUserNumberEqualChangesNumber){
                    test.add(viewHolder.txtKabinets);
                    viewHolder.txtStunda.setTextColor(ContextCompat.getColor(ctx, R.color.colorOrange));
                    viewHolder.txtKabinets.setTextColor(ContextCompat.getColor(ctx, R.color.colorOrange));
                    viewHolder.txtStundaNum.setTextColor(ContextCompat.getColor(ctx, R.color.colorOrange));
                }
                else{
                    viewHolder.txtStunda.setTextColor(ContextCompat.getColor(ctx, R.color.colorRed));
                    viewHolder.txtStundaNum.setTextColor(ContextCompat.getColor(ctx, R.color.colorRed));
                    viewHolder.txtKabinets.setTextColor(ContextCompat.getColor(ctx, R.color.colorDarkBlue));
                }


        }

//        LayoutInflater inflaterView = (LayoutInflater)activity.gegetLayoutInflater();
//        View row = inflaterView.inflate(R.layout.klases_stunda, parent, false);
//        ImageButton deleteImageView = (ImageButton) row.findViewById(R.id.buttonDelete);
//        deleteImageView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.d("Button", "Button delete pressed");
//                Toast.makeText(getActivity(), "Delete" ,Toast.LENGTH_LONG).show();
//            }
//        });

        // Return the completed view to render on screen
        return convertView;
    }

    private void getData(){
        izmainas = new ArrayList<ListModel>();
        String result = SplashScreen.result;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;


            for(int i=0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);
                ;
                if(!jo.getString("prieksmets").equals("") && (jo.getString("klase").equals(SettingsFragment.klase) || jo.getString("klase").contains(",") || jo.getString("klase").contains("-"))) {
                    if(jo.getString("datums").equals(SplashScreen.dateCurrent) && returnDayEN(KlasesIzmainasFragment.daySelected).equals(SplashScreen.nameOfDayCurrent)) {
                        if(jo.getString("klase").contains(",")){
                            getCommaSeperatedChanges(jo);
                        }
                        else if(jo.getString("klase").contains("-")){
                            getDashSeparetedChanges(jo);
                        }
                        else {
                            izmainas.add(new ListModel(jo.getString("stunda"), jo.getString("klase"), jo.getString("prieksmets"), jo.getString("text")));
                        }
                    }
                    else if(jo.getString("datums").equals(SplashScreen.dateNext) && returnDayEN(KlasesIzmainasFragment.daySelected).equals(SplashScreen.nameOfDayNext)){
                        if(jo.getString("klase").contains(",")){
                            getCommaSeperatedChanges(jo);
                        }
                        else if(jo.getString("klase").contains("-")){
                            getDashSeparetedChanges(jo);
                        }
                        else {
                            izmainas.add(new ListModel(jo.getString("stunda"), jo.getString("klase"), jo.getString("prieksmets"), jo.getString("text")));
                        }
                    }
;                    //stunduIzmainasKlase.add(new KlaseListModel(jo.getString("stunda"), jo.getString("klase"), jo.getString("prieksmets"), jo.getString("text"), jo.getString("datums")));
                }
//                else{
//                    noChanges = true;
//                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String returnDayEN(String day){
        if(day.equals("Pirmdiena")){
            return "Mon";
        }
        else if(day.equals("Otrdiena")){
            return "Tue";
        }
        else if(day.equals("Trešdiena")){
            return "Wed";
        }
        else if(day.equals("Ceturtdiena")){
            return "Thu";
        }
        else if(day.equals("Piektdiena")){
            return "Fri";
        }
        return "";
    }

    private void getCommaSeperatedChanges(JSONObject jo) throws JSONException{
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
                    izmainas.add(new ListModel(jo.getString("stunda"), klases.get(p), jo.getString("prieksmets"), jo.getString("text")));
                }
            }
        }
    }

    private void getDashSeparetedChanges(JSONObject jo) throws JSONException{
        String text = jo.getString("klase");
        text = text.replaceAll("\\s+", "");
        String[] items = text.split("[-]");
        String userKlaseNum = SettingsFragment.klase.split("[.]")[0];

        if(items.length == 2) {
            try {
                if ((Integer.parseInt(userKlaseNum) >= Integer.parseInt(items[0])) && (Integer.parseInt(userKlaseNum) <= Integer.parseInt(items[1]))) {
                    izmainas.add(new ListModel(jo.getString("stunda"), text, jo.getString("prieksmets"), jo.getString("text")));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
