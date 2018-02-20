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

public class CustomAdapter extends ArrayAdapter<ListModel> implements View.OnClickListener {
    private ArrayList<ListModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtStundNum;
        TextView txtKlase;
        TextView txtStundasNosauk;
        TextView txtInfo;
    }

    public CustomAdapter(ArrayList<ListModel> data, Context context) {
        super(context, R.layout.activity_main, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        ListModel dataModel=(ListModel)object;

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
        ListModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.stundas_info, parent, false);
            viewHolder.txtStundNum = (TextView) convertView.findViewById(R.id.textStundNum);
            viewHolder.txtKlase = (TextView) convertView.findViewById(R.id.textKlase);
            viewHolder.txtStundasNosauk = (TextView) convertView.findViewById(R.id.textStundasNosauk);
            viewHolder.txtInfo = (TextView) convertView.findViewById(R.id.textInfo);
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

        viewHolder.txtStundNum.setText(dataModel.getStundasNum());
        viewHolder.txtKlase.setText(dataModel.getKlase());
        viewHolder.txtStundasNosauk.setText(dataModel.getStunda());
        if(!dataModel.getInfo().equals("")) {
            viewHolder.txtInfo.setText("Info: " + dataModel.getInfo());
        }
        else{
            viewHolder.txtInfo.setVisibility(View.GONE);
        }
        //viewHolder.info.setOnClickListener(this);
        //viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
