package com.avg.roboo.stunduizmainas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by roboo on 19.06.2017.
 */

public class CustomAdapter_Klase extends ArrayAdapter<KlaseListModel> implements View.OnClickListener {
    private ArrayList<KlaseListModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtStundaNum;
        TextView txtStunda;
        TextView txtKlase;
        TextView txtDatums;
        TextView txtInfo;

    }

    public CustomAdapter_Klase(ArrayList<KlaseListModel> data, Context context) {
        super(context, R.layout.activity_main, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        KlaseListModel dataModel=(KlaseListModel)object;

        switch (v.getId())
        {
            //case R.id.item_info:
            //    Snackbar.make(v, "Release date " +dataModel.getInfo(), Snackbar.LENGTH_LONG)
            //            .setAction("No action", null).show();
            //    break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        KlaseListModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.stundas_info_klases, parent, false);
            viewHolder.txtStundaNum = (TextView) convertView.findViewById(R.id.textStundNum);
            viewHolder.txtKlase = (TextView) convertView.findViewById(R.id.textKlase);
            viewHolder.txtStunda = (TextView) convertView.findViewById(R.id.textStundasNosauk);
            viewHolder.txtDatums = (TextView) convertView.findViewById(R.id.textDatums);
            viewHolder.txtInfo = (TextView) convertView.findViewById(R.id.textKlasesInfo);
            //viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        viewHolder.txtStundaNum.setText(dataModel.getStundasNum());
        viewHolder.txtKlase.setText(dataModel.getKlase());
        viewHolder.txtStunda.setText(dataModel.getStunda());
        if(dataModel.getDatums().equals(SplashScreen.dateCurrent)){
            viewHolder.txtDatums.setText(SplashScreen.dateCurrentFormat);
        }
        else if(dataModel.getDatums().equals(SplashScreen.dateNext)){
            viewHolder.txtDatums.setText(SplashScreen.dateNextFormat);
        }
        else{
            viewHolder.txtDatums.setText(dataModel.getDatums());
        }
        viewHolder.txtInfo.setText("Info: " + dataModel.getInfo());
        //viewHolder.info.setOnClickListener(this);
        //viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
