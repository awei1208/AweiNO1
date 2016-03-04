package com.addtw.aweino1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Arrays;

/**
 * Created by awei on 2016/2/6.
 */

public class GalleryActivity extends Activity
{
    private static final String[] IMAGES = { "http://www.wentrupgallery.com/media/gallery.jpg", "http://www.wentrupgallery.com/media/3712-560x420.jpg","http://www.wentrupgallery.com/media/ABC20141-560x373.jpg","http://www.wentrupgallery.com/media/2223-560x417.jpg"
    ,"http://www.wentrupgallery.com/media/AG_M_297_wand2-560x389.jpg","http://www.wentrupgallery.com/media/Public-Scribble-1-2009-enamel-on-wall-80-x-120feet-installated-in-Soho-New-York-City-courtesy-Art-Production-Fund-New-York-560x412.jpg","http://www.wentrupgallery.com/media/Public-Scribble-1-2009-enamel-on-wall-80-x-120feet-installated-in-Soho-New-York-City-courtesy-Art-Production-Fund-New-York-560x412.jpg"
            ,"http://www.wentrupgallery.com/media/FM_Startbild7-560x407.jpg","http://www.wentrupgallery.com/media/0070_OM_Deutsche-Kiste_2015_Foto%C2%A9L-Felle-560x373.jpg","http://www.wentrupgallery.com/media/DR_Startbild-560x407.jpeg"
            ,"http://www.wentrupgallery.com/media/WT_M_172_Startbild-560x392.jpg","http://www.wentrupgallery.com/media/WT_M_172_Startbild-560x392.jpg","http://www.wentrupgallery.com/media/79-560x420.jpg"

          //  public final static String ID_EXTRA = "com.addtw.menutest._ID";

    };

    /**
     * 用來展示圖片的Gallery
     */
    private GridView photoGallery;
    public final static String ID_EXTRA = "com.addtw.aweino1._ID";
    /**
     * GridView所使用的Adapter
     */
    private PhotoGalleryAdapter adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_gallery );
        this.photoGallery = (GridView) findViewById( R.id.gridPhoto );

        // TODO 這裡的IMAGES可以換成任何來源，不論是遠端伺服器查詢、或者是手機儲存

        this.adapter = new PhotoGalleryAdapter( this, Arrays.asList(IMAGES), photoGallery );
        this.photoGallery.setAdapter(adapter);

       photoGallery.setOnItemClickListener(
               new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       String list = String.valueOf(parent.getItemAtPosition(position));
                       try {
                          Class myClass = Class.forName("com.addtw.aweino1.PicturePage");
                           Intent myIntent = new Intent(GalleryActivity.this,myClass);
                          // myIntent.putExtra(ID_EXTRA,String.valueOf(id));
                           myIntent.putExtra(ID_EXTRA,IMAGES[position]);
                           startActivity(myIntent);
                       }
                      catch (ClassNotFoundException e){
                           e.printStackTrace();

                      }
                   }
               }

       );
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // 結束Activity時同時也取消所有圖片下載的工作

        this.adapter.cancelAllTasks();
    }

}