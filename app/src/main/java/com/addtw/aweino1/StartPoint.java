package com.addtw.aweino1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * Created by awei on 2016/1/30.
 */
public class StartPoint extends Activity {

    String list[] = {"MainActivity","GalleryActivity","ImageViewer","Workout","FacebookLogin","GoogleMapPage","JsonTest","UploadJSON","TryGlide"};

    @Override
    protected void onCreate(Bundle Awei) {
        super.onCreate(Awei);
        setContentView(R.layout.startpoint);

        ListAdapter aweiadapter= new CustomAdapter(this,list);
        ListView aweilistview = (ListView) findViewById(R.id.listView2);
        aweilistview.setAdapter(aweiadapter);

        aweilistview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String list = String.valueOf(parent.getItemAtPosition(position));
                        //Toast.makeText(StartPoint.this,list,Toast.LENGTH_LONG).show();
                        try {
                            Class myClass = Class.forName("com.addtw.aweino1." + list);
                            Intent myIntent = new Intent(StartPoint.this, myClass);
                            startActivity(myIntent);
                        }catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
        );

    }
}
