package com.addtw.aweino1;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by awei on 2016/2/6.
 */
public class PicturePage extends GalleryActivity {

    String passvar = null;
    private TextView passview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picturepage);
        passvar = getIntent().getStringExtra(GalleryActivity.ID_EXTRA);
        passview = (TextView)findViewById(R.id.picpagID);
        passview.setText("你的ID現在是"+passvar);
    }
}
