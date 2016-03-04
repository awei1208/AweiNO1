package com.addtw.aweino1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by awei on 2016/2/7.
 */
public class ImageViewer extends AppCompatActivity {

    ImageView mImageView ;
    private static final int RESULT_OPEN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_viewer);
       mImageView =(ImageView) findViewById(R.id.tvImageView);
        mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(),"不要再碰我了!",Toast.LENGTH_LONG).show();
                return true;
            }
        });

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_OPEN);
    }
    @Override
    public void onActivityResult(int requestCode ,int resultCode , Intent resultData) {
        if (requestCode == RESULT_OPEN && resultCode == RESULT_OK){
            Uri uri= null;
            if (resultData!=null ){
                uri = resultData.getData();
               /* try {
                    Bitmap bitmap = getBitmapFromUri(uri);
                    mImageView.setImageBitmap(bitmap);
                }catch (IOException e) {
                    e.printStackTrace();
                }
                */
                Glide.with(this).load(uri).into(mImageView);
            }
        }
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException{
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri,"r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        return  bitmap;
    }
}
