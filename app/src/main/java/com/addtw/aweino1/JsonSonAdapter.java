package com.addtw.aweino1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awei on 2016/2/17.
 */
public class JsonSonAdapter extends ArrayAdapter<JsonSongs> {
    ArrayList<JsonSongs> songList;
    int Resource;
    Context context;
    LayoutInflater vi;


    public JsonSonAdapter(Context context, int resource, ArrayList<JsonSongs> objects) {
        super(context, resource, objects);
        songList = objects;
        Resource = resource;
        this.context = context;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       final ViewHolder holder;
        if(convertView == null){
           convertView = vi.inflate(Resource,null);
            holder = new ViewHolder();
            holder.textJson_ID=(TextView)convertView.findViewById(R.id.textJson_ID);
            holder.textJSONname=(TextView)convertView.findViewById(R.id.textJSONname);
            holder.textJsonSong=(TextView)convertView.findViewById(R.id.textJsonSong);
            holder.textJsonYear=(TextView)convertView.findViewById(R.id.textJsonYear);
            holder.textJsonWeeks=(TextView)convertView.findViewById(R.id.textJsonWeeks);



            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.textJson_ID.setText(songList.get(position).get_id());
        holder.textJsonWeeks.setText(songList.get(position).getWeeksAtOne());
        holder.textJsonYear.setText(songList.get(position).getDecade());
        holder.textJsonSong.setText(songList.get(position).getSong());
        holder.textJSONname.setText(songList.get(position).getArtist());

         return convertView;
    }


    static class ViewHolder{

     public TextView textJsonYear;
     public TextView textJSONname;
     public TextView textJsonSong;
     public TextView textJsonWeeks;
     public TextView textJson_ID;


 }

}
