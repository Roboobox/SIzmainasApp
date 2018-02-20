package com.avg.roboo.stunduizmainas;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by roboo on 08.08.2017.
 */

public class CustomAdapter_InfoNews extends ArrayAdapter<InfoNewsListModel> implements View.OnClickListener {
    private ArrayList<InfoNewsListModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtSkolInfo;
        TextView txtInfo;
        ImageView pinImage;

    }

    public CustomAdapter_InfoNews(ArrayList<InfoNewsListModel> data, Context context) {
        super(context, R.layout.activity_main, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        InfoNewsListModel dataModel=(InfoNewsListModel)object;

        switch (v.getId())
        {
            //case R.id.item_info:
            //    Snackbar.make(v, "Release date " +dataModel.getInfo(), Snackbar.LENGTH_LONG)
            //            .setAction("No action", null).show();
            //    break;
        }
    }

    private int lastPosition = -1;

    @SuppressWarnings("deprecation")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InfoNewsListModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        CustomAdapter_InfoNews.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new CustomAdapter_InfoNews.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.infonews_izmainas, parent, false);
            viewHolder.txtSkolInfo = (TextView) convertView.findViewById(R.id.textInfoNewsSkol);
            viewHolder.txtInfo = (TextView) convertView.findViewById(R.id.textInfoNews);
            viewHolder.pinImage = (ImageView) convertView.findViewById(R.id.imageViewInfoNewsPin);
            //viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapter_InfoNews.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtSkolInfo.setText(dataModel.getSkol());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            viewHolder.txtInfo.setText(Html.fromHtml(dataModel.getInfo(), Html.FROM_HTML_MODE_LEGACY));
        }
        else{
            viewHolder.txtInfo.setText(Html.fromHtml(dataModel.getInfo()));
        }
        viewHolder.txtInfo.setMovementMethod(LinkMovementMethod.getInstance());

        //viewHolder.info.setOnClickListener(this);
        //viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
