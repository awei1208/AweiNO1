package com.addtw.aweino1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by awei on 2016/2/9.
 */


public class Workout extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout);
        ImageView userphoto = (ImageView) findViewById(R.id.imageUserPhoto);
        TextView name = (TextView) findViewById(R.id.textUserName);
        TextView id = (TextView) findViewById(R.id.textUserID);
        String UserInfo[];
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        UserInfo = bundle.getStringArray("UserInfo");
        id.setText(UserInfo[0]);
        name.setText(UserInfo[1]);
        Glide.with(Workout.this).load(UserInfo[2]).override(200, 200).into(userphoto);















































































































































































































































































































































































































































































































































































































































































































































































































    }

}
