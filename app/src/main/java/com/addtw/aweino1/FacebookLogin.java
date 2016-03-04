package com.addtw.aweino1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by awei on 2016/2/9.
 */
public class FacebookLogin extends Activity {

    CallbackManager callbackManager;
    private AccessToken accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebooklogin);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.FaceBooklogin_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                Log.d("FB", "access token got.");
                Toast.makeText(FacebookLogin.this, "Gooooooooood", Toast.LENGTH_LONG).show();
                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("FB", "complete");
                        Log.d("FB", object.optString("name"));
                        Log.d("FB", object.optString("link"));
                        Log.d("FB", object.optString("id"));
                        Log.d("FB", object.optString("gender"));
                        TextView textUSERname;
                        textUSERname = (TextView) findViewById(R.id.textUSERname);
                        String id = object.optString("id");
                        String name = object.optString("name");
                        textUSERname.setText("Yo!!!! wellcome :" + name);
                        ImageView userPhoto = (ImageView) findViewById(R.id.imageFB_user);
                        String i = "http://graph.facebook.com/" + id + "/picture?type=large";
                        Glide.with(FacebookLogin.this).load(i).override(200, 200).into(userPhoto);
                        Intent intent =new Intent(FacebookLogin.this,com.addtw.aweino1.Workout.class);
                        String UserInfo[] = {id,name,i};
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("UserInfo",UserInfo);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }


            @Override
            public void onCancel() {
                Log.d("FB", "CANCEL");
                Toast.makeText(FacebookLogin.this, "CANCLE", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(FacebookLogin.this, "Error", Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);

    }


}
