package com.addtw.aweino1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by awei on 2016/2/5.
 */


class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(Context context, String[] list) {///要填入哪個ARRAY跟哪個LAYOUT
        super(context, R.layout.custom_row, list);///要填入哪個ARRAY跟哪個LAYOUT，告訴這個ADAPTER說這是用哪個LAYOUT
    }

    ///用GETVIEW是要說:嘿，你要把字擺在哪，用哪個LAYOUT
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ///產生?!?!
        LayoutInflater AweiInflater = LayoutInflater.from(getContext());
        ///將產生在哪一個LAYOUT裡面
        View customView = AweiInflater.inflate(R.layout.custom_row, parent, false);
        ///將東西對應起來
        String singleItem = getItem(position);
        TextView aweiText = (TextView) customView.findViewById(R.id.aweiText);
        ImageView aweiImage = (ImageView) customView.findViewById(R.id.aweiImage);
        ////將東西填進去
        aweiText.setText(singleItem);
        aweiImage.setImageResource(R.drawable.smalllogo);
        ///丟出結果
        return customView;
    }
}
