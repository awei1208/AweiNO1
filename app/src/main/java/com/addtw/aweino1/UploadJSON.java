package com.addtw.aweino1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by awei on 2016/2/19.
 */
public class UploadJSON extends Activity {
    EditText artist;
    EditText week;
    EditText year;
    EditText song;
    Button Upload;
    int cameraOption ;
    private String imageLocation;
    Bitmap resizePhoto;
    Button imageUpload;
    String[] imagesLocations = new String[4];
    TextView BitmapArraySize;

    android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            artist.setText(null);
            week.setText(null);
            year.setText(null);
            song.setText(null);
            Toast.makeText(UploadJSON.this,"上傳成功",Toast.LENGTH_LONG).show();
        }
    };

    private static final int ACTIVITY_CAMERA_START =0;
    ImageButton imageButtonOne ;
    ImageButton imageButtonTwo ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadjson);
        artist = (EditText) findViewById(R.id.editUploadArtist);
        week = (EditText) findViewById(R.id.editUploadWeek);
        year = (EditText) findViewById(R.id.editUploadYear);
        song = (EditText) findViewById(R.id.editUploadSong);
        Upload = (Button) findViewById(R.id.btUpload);
        imageButtonOne =(ImageButton)findViewById(R.id.imageButtonOne);
        imageButtonTwo = (ImageButton)findViewById(R.id.imageButtonTwo);
        imageUpload = (Button)findViewById(R.id.btImageUpload);
        BitmapArraySize = (TextView)findViewById(R.id.TextsizeOfBitmapArray);


      //  final Intent callCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       final Intent callCamera = new Intent();
        imageButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
               // startActivityForResult(callCamera ,ACTIVITY_CAMERA_START );
                cameraOption = 0;
                File photoFile = null;
                try{
                    photoFile = creatImageFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                callCamera.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
                startActivityForResult(callCamera,ACTIVITY_CAMERA_START);
            }
        });

        imageButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraOption = 1;
                File photoFile = null;
                try{
                    photoFile = creatImageFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                callCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(callCamera,ACTIVITY_CAMERA_START);
            }
        });

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //只能點一次
                Upload.setEnabled(false);
                //

               Runnable r = new Runnable() {
                   @Override
                   public void run() {
                       HttpURLConnection Connection = null;
                       try {
                           StringBuilder sb = new StringBuilder();
                           JSONObject jsonObject = new JSONObject();
                           jsonObject.put("artist", artist.getText().toString());
                           jsonObject.put("weeksAtOne", week.getText().toString());
                           jsonObject.put("decade", year.getText().toString());
                           jsonObject.put("song", song.getText().toString());

                           

                           String http = "http://118.161.247.21:3000/songs";
                           URL url = new URL(http);
                           Connection = (HttpURLConnection) url.openConnection();
                           Connection.setDoOutput(true);
                           Connection.setRequestMethod("POST");
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
                       } //
                       handler.sendEmptyMessage(0);

                       final String boundary;
                       final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                       boundary = "===" + System.currentTimeMillis() + "===";
                       OkHttpClient client = new OkHttpClient();

                       File file ;
                       String fileName;


                       File file0 = new File(imagesLocations[0]);
                       File file1 = new File(imagesLocations[1]);
                       String fileName0 = file0.getName();
                       String fileName1 = file1.getName();
                       RequestBody body = new MultipartBody.Builder()
                               .setType(MultipartBody.FORM)
                               .addFormDataPart("images", fileName0, RequestBody.create(MEDIA_TYPE_PNG, file0))
                               .addFormDataPart("images", fileName1, RequestBody.create(MEDIA_TYPE_PNG, file1))
                               .build();


                       Request request = new Request.Builder()
                               .url("http://118.161.247.21:3000/upload")
                               .post(body)
                               .addHeader("content-type", "multipart/form-data; boundary="+boundary)
                               .addHeader("cache-control", "no-cache")
                               .build();

                       try {
                           Response response = client.newCall(request).execute();

                       }catch (IOException e){
                           e.printStackTrace();
                       }

                   }
               };
                    Thread connThread = new Thread(r);
                    connThread.start();
            }
        });

            imageUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Runnable r = new Runnable() {
                        @Override
                        public void run()  {
                             final String boundary;
                            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                            boundary = "===" + System.currentTimeMillis() + "===";
                            OkHttpClient client = new OkHttpClient();

                            File file ;
                            String fileName;


                            File file0 = new File(imagesLocations[0]);
                            File file1 = new File(imagesLocations[1]);
                            String fileName0 = file0.getName();
                            String fileName1 = file1.getName();
                            RequestBody body = new MultipartBody.Builder()
                                                    .setType(MultipartBody.FORM)
                                                    .addFormDataPart("images", fileName0, RequestBody.create(MEDIA_TYPE_PNG, file0))
                                                    .addFormDataPart("images", fileName1, RequestBody.create(MEDIA_TYPE_PNG, file1))
                                                    .build();


                            Request request = new Request.Builder()
                                    .url("http://118.161.247.21:3000/upload")
                                    .post(body)
                                    .addHeader("content-type", "multipart/form-data; boundary="+boundary)
                                    .addHeader("cache-control", "no-cache")
                                    .build();

                            try {
                                Response response = client.newCall(request).execute();

                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                     };

                    Thread uploadImageThread = new Thread(r);
                    uploadImageThread.start();

                }
            });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTIVITY_CAMERA_START && resultCode == RESULT_OK){
            Toast.makeText(this, "拍好了", Toast.LENGTH_SHORT).show();

         //  Bundle extra = data.getExtras();
          //  Bitmap result = (Bitmap) extra.get("data");
           decreaseImageSize();
        }
    }
    File creatImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "IMG_"+timeStamp+"_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File image = File.createTempFile(filename, ".jpg", storageDir);
        imageLocation = image.getAbsolutePath();
       //
            return  image;
        }

    void decreaseImageSize(){
        int targetWidth =0;
        int targetHeight =0;

        if (cameraOption == 0) {
            targetWidth = imageButtonOne.getWidth();
            targetHeight = imageButtonOne.getHeight();
        }if (cameraOption == 1){
            targetWidth = imageButtonTwo.getWidth();
            targetHeight = imageButtonTwo.getHeight();
        }
        BitmapFactory.Options bmOption = new BitmapFactory.Options();
        bmOption.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageLocation,bmOption);
        int camwidth = bmOption.outWidth;
        int camheight = bmOption.outHeight;

        int scaleFactor= Math.min(camwidth / targetWidth, camheight / targetHeight);
        bmOption.inSampleSize=scaleFactor;
        bmOption.inJustDecodeBounds = false;

        resizePhoto = BitmapFactory.decodeFile(imageLocation,bmOption);
        if (cameraOption == 0){
            imageButtonOne.setImageBitmap(resizePhoto);
            imagesLocations[0]=imageLocation ;
           }
        if (cameraOption == 1){
            imageButtonTwo.setImageBitmap(resizePhoto);
            imagesLocations[1]=imageLocation;
        }

        }




    /*private void rotateImage(Bitmap bitmap){
        ExifInterface exifInterface = null;
        try{
            exifInterface = new ExifInterface(imageLocation);
        }catch (IOException e){
            e.printStackTrace();
        }
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix =new Matrix();
        switch (orientation){
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotateM(180);

        }
    }*/

    }



