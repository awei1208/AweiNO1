package com.addtw.aweino1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by awei on 2016/2/22.
 */
public class TryGlide extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tryglide);
        ImageView photo = (ImageView)findViewById(R.id.imageTryGlide);
        Glide.with(this).load("http://36.224.139.189:3000/upload/Chau belle.png").into(photo);
    }
}
