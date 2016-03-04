package com.addtw.aweino1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by awei on 2016/2/10.
 */
public class LoginOK extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsucces);
        TextView OK = (TextView)findViewById(R.id.textFBloginSucess);
        OK.setText("ok");
    }
}
