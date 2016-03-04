package com.addtw.aweino1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by awei on 2016/2/19.
 */
public class SongPage extends Activity{
    //textview
    TextView ID;
    TextView artist;
    TextView year;
    TextView weeks;
    TextView songname;
    EditText editSongName;
    EditText editArtist;
    EditText editWeeks;
    EditText editYear;
    String songID;

    SongInfoHolder SongHolder = new SongInfoHolder();
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ID.setText("資料已刪除");
            artist.setText(null);
            weeks.setText(null);
            year.setText(null);
            songname.setText(null);
            Toast.makeText(SongPage.this,"刪除成功",Toast.LENGTH_LONG).show();
        }
    };
    Handler editHanlder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            year.setText(editYear.getText());
            weeks.setText(editWeeks.getText());
            artist.setText(editArtist.getText());
            songname.setText(editSongName.getText());
            Toast.makeText(SongPage.this,"修改成功",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Intent intent = this.getIntent();
        //get the item id

       songID = intent.getStringExtra("itemid");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songpage);

        //
      //  Intent returnIntent = new Intent();
       // setResult(RESULT_CANCELED, returnIntent);
      //  finish();

        //connection to the api
        if (songID != null){
        new Connection().execute("http://118.161.247.21:3000/findById/" + songID);
        }else{

        };

        final SongInfoHolder SongHolder = new SongInfoHolder();
        final ViewSwitcher nameSwitcher =(ViewSwitcher)findViewById(R.id.switcher_name);
        final ViewSwitcher artistSwitcher = (ViewSwitcher)findViewById(R.id.switcher_Artist);
        final ViewSwitcher yeasSwitcher = (ViewSwitcher)findViewById(R.id.switcher_Year);
        final ViewSwitcher weeksSwitcher = (ViewSwitcher)findViewById(R.id.switcher_Weeks);


        ID = (TextView)findViewById(R.id.textSongPage_ID);
        artist = (TextView)findViewById(R.id.textSongPageArtist);
        year = (TextView)findViewById(R.id.textSongPageYear);
        weeks = (TextView)findViewById(R.id.textSongPageWeeks);
        songname = (TextView)findViewById(R.id.textSongPageName);
        editSongName = (EditText)findViewById(R.id.edit_SongPage_Name);
        editArtist = (EditText)findViewById(R.id.edit_Artist);
        editWeeks = (EditText)findViewById(R.id.edit_Weeks);
        editYear = (EditText)findViewById(R.id.edit_Year);

        Button delete = (Button)findViewById(R.id.btSongPageDelete);
        final Button edit = (Button)findViewById(R.id.btSongPageEdit);
        edit.setTag(1);
        edit.setText("修改");
        final Button editConfirm = (Button)findViewById(R.id.bt_Edit_Confirm);


        Runnable rEdit;

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Runnable rDeltet = new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection Connection = null;
                        try {
                            StringBuilder sb = new StringBuilder();
                            JSONObject jsonObject = new JSONObject();
                            String http = "http://118.161.247.21:3000/songs/"+songID;
                            URL url = new URL(http);
                            Connection = (HttpURLConnection) url.openConnection();
                            Connection.setDoOutput(true);
                            Connection.setRequestMethod("DELETE");
                            Connection.setUseCaches(false);
                            Connection.setConnectTimeout(10000);
                            Connection.setReadTimeout(10000);
                           // Connection.setRequestProperty("Content-Type", "application/json");
                           // Connection.setRequestProperty("Host", "114.37.184.215:3000/songs");
                            Connection.connect();

                            int HttpResult = 0;
                            try {
                                HttpResult = Connection.getResponseCode();
                            }catch (IOException ioex){
                                Log.v("ConnError", ioex.getMessage());
                            };
                            if (HttpResult == HttpURLConnection.HTTP_OK) {
                                BufferedReader br = new BufferedReader(new InputStreamReader(
                                        Connection.getInputStream(), "utf-8"));
                                String line = null;
                                //
                                while ((line = br.readLine()) != null) {
                                    sb.append(line + "\n");
                                }
                                br.close();

                                System.out.println("" + sb.toString());

                            } else {
                                System.out.println(Connection.getResponseMessage());
                            }

                        } catch (MalformedURLException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally{
                            if(Connection!=null)
                                Connection.disconnect();
                        }
                        handler.sendEmptyMessage(0);
                    }

                };
                Thread deleteThread = new Thread(rDeltet);
                deleteThread.start();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int status = (Integer)v.getTag();
                if (status == 1 ){
                    editConfirm.setVisibility(View.VISIBLE);
                    edit.setText("取消");
                    edit.setTag(0);
                }else {
                    edit.setText("修改");
                    edit.setTag(1);
                    editConfirm.setVisibility(View.GONE);
                }
                nameSwitcher.showNext();
                artistSwitcher.showNext();
                yeasSwitcher.showNext();
                weeksSwitcher.showNext();
            }
        });

        editConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable editRun = new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection Connection = null;
                        try {
                            StringBuilder sb = new StringBuilder();
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("artist", editArtist.getText().toString());
                            jsonObject.put("weeksAtOne", editWeeks.getText().toString());
                            jsonObject.put("decade", editYear.getText().toString());
                            jsonObject.put("song", editSongName.getText().toString());
                            String http = "http://118.161.247.21:3000/songs/"+songID;
                            URL url = new URL(http);
                            Connection = (HttpURLConnection) url.openConnection();
                            Connection.setDoOutput(true);
                            Connection.setRequestMethod("PUT");
                            Connection.setUseCaches(false);
                            Connection.setConnectTimeout(10000);
                            Connection.setReadTimeout(10000);
                            Connection.setRequestProperty("Content-Type", "application/json");
                            Connection.setRequestProperty("Host", "118.161.247.21:3000/songs");
                            Connection.connect();
                            OutputStreamWriter out = new OutputStreamWriter(Connection.getOutputStream());
                            out.write(jsonObject.toString());
                            // out.write(URLEncoder.encode(jsonObject.toString(), "UTF-8"));
                            out.flush();
                            out.close();
                            int HttpResult = 0;
                            try {
                                HttpResult = Connection.getResponseCode();
                            }catch (IOException ioex){
                                Log.v("ConnError", ioex.getMessage());
                            };
                            if (HttpResult == HttpURLConnection.HTTP_OK) {
                                BufferedReader br = new BufferedReader(new InputStreamReader(
                                        Connection.getInputStream(), "utf-8"));
                                String line = null;
                                //
                                while ((line = br.readLine()) != null) {
                                    sb.append(line + "\n");
                                }
                                br.close();

                                System.out.println("" + sb.toString());

                            } else {
                                System.out.println(Connection.getResponseMessage());
                            }

                        } catch (MalformedURLException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }finally{
                            if(Connection!=null)
                                Connection.disconnect();
                        }
                        editHanlder.sendEmptyMessage(0);
                    }
                };
                Thread editThread = new Thread(editRun);
                editThread.start();
                edit.callOnClick();
            }
        });




    }



class Connection extends AsyncTask< String , String ,String> {
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
            JSONObject finaObject = new JSONObject(finalJson);
            //Put string into seter
           SongHolder.set_id(finaObject.getString("_id"));
            SongHolder.setDecade(finaObject.getString("decade"));
            SongHolder.setArtist(finaObject.getString("artist"));
            SongHolder.setSong(finaObject.getString("song"));
            SongHolder.setWeeksAtOne(finaObject.getString("weeksAtOne"));
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
            ID.setText(SongHolder.get_id());
            artist.setText(SongHolder.getArtist());
            year.setText(SongHolder.getDecade());
            weeks.setText(SongHolder.getWeeksAtOne());
            songname.setText(SongHolder.getSong());
            editSongName.setText(SongHolder.getSong());
            editArtist.setText(SongHolder.getArtist());
            editYear.setText(SongHolder.getDecade());
            editWeeks.setText(SongHolder.getWeeksAtOne());

        }else {

        }
    }
}
}


