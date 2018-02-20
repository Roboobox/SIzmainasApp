package com.avg.roboo.stunduizmainas;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by roboo on 07.08.2017.
 */

public class CustomAdapter_Info extends ArrayAdapter<InfoListModel> implements View.OnClickListener {
    private ArrayList<InfoListModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtDarbinieksInfo;
        TextView txtKurInfo;
        TextView txtInfo;

    }

    public CustomAdapter_Info(ArrayList<InfoListModel> data, Context context) {
        super(context, R.layout.activity_main, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        InfoListModel dataModel=(InfoListModel)object;

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
        InfoListModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        CustomAdapter_Info.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new CustomAdapter_Info.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.info_izmainas, parent, false);
            viewHolder.txtDarbinieksInfo = (TextView) convertView.findViewById(R.id.textDarbinieksInfo);
            viewHolder.txtKurInfo = (TextView) convertView.findViewById(R.id.textKurInfo);
            viewHolder.txtInfo = (TextView) convertView.findViewById(R.id.textInfoIzmainas);
            //viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapter_Info.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtDarbinieksInfo.setText(dataModel.getDarbinieksinfo());
        viewHolder.txtKurInfo.setText(dataModel.getKurinfo());
        viewHolder.txtInfo.setText(dataModel.getInfo());

        if(viewHolder.txtKurInfo.getText().equals("Informācija:")){
            viewHolder.txtKurInfo.setTypeface(null, Typeface.BOLD_ITALIC);
        }
        else{
            viewHolder.txtKurInfo.setTypeface(null, Typeface.NORMAL);
        }

        if(viewHolder.txtInfo.getText().equals("")){
            viewHolder.txtInfo.setVisibility(View.GONE);
        }
        else{
            viewHolder.txtInfo.setVisibility(View.VISIBLE);
        }
        //viewHolder.info.setOnClickListener(this);
        //viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
