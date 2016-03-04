package com.addtw.aweino1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by awei on 2016/2/17.
 */


public class JsonTest extends Activity {

    ListView list;
    ArrayList<JsonSongs> songlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsonmain);

        list = (ListView)findViewById(R.id.JsonlistView);
        songlist = new ArrayList<JsonSongs>();
        new JsonTalk().execute("http://118.161.247.21:3000/songs");
       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    Class myclass = Class.forName("com.addtw.aweino1.SongPage");
                    Intent intent = new Intent(JsonTest.this, myclass);
                    //從點擊的位置取出它的字串，用position就可以找出點擊位置
                    String itemid = songlist.get(position).get_id();
                    intent.putExtra("itemid", itemid);
                    Toast.makeText(JsonTest.this, itemid, Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
       // Toast.makeText(this,"Welcome back,",Toast.LENGTH_SHORT).show();
        this.onCreate(null);
    }

    class JsonTalk extends AsyncTask< String , String ,String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
               // JSONObject parentJsonObject = new JSONObject(finalJson);
                JSONArray jsonArray = new JSONArray(finalJson);

                for (int i = 0 ; i < jsonArray.length();i++){
                    JsonSongs jsonSongs = new JsonSongs();
                    JSONObject finaObject = jsonArray.getJSONObject(i);
                    jsonSongs.set_id(finaObject.getString("_id"));
                    jsonSongs.setDecade(finaObject.getString("decade"));
                    jsonSongs.setArtist(finaObject.getString("artist"));
                    jsonSongs.setSong(finaObject.getString("song"));
                    jsonSongs.setWeeksAtOne(finaObject.getString("weeksAtOne"));
                    songlist.add(jsonSongs);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (JSONException e){
                e.printStackTrace();
            }

            finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result == null){
                JsonSonAdapter adapter = new JsonSonAdapter(getApplicationContext(),R.layout.jsonlayout,songlist);
                list.setAdapter(adapter);

            }else {
                Toast.makeText(JsonTest.this,"未連線到資料庫唷!!!",Toast.LENGTH_LONG).show();
            }
        }
    }
}



