package com.addtw.aweino1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by awei on 2016/2/9.
 */
public class Showpage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showpage);
        TextView ProductName = (TextView) findViewById(R.id.textShowPN);
        TextView QT = (TextView)findViewById(R.id.textShowQT);
        TextView Color = (TextView)findViewById(R.id.textShowColor);
        TextView Size = (TextView)findViewById(R.id.textShowSize);
        String Result[];
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Result = bundle.getStringArray("Content");
        for (int i=0;i<Result.length;i++){
            ProductName.setText("產品名稱:"+Result[0]);
            QT.setText("數量:"+Result[1]);
            Color.setText("顏色:"+Result[2]);
            Size.setText("尺寸:"+Result[3]);
        }

    }
}
