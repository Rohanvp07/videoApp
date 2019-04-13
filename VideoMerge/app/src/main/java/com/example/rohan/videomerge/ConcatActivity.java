package com.example.rohan.videomerge;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ConcatActivity extends AppCompatActivity {

    private String path1,path2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concat);

        ImageView video1=(ImageView)findViewById(R.id.video1);
        ImageView video2=(ImageView)findViewById(R.id.video2);

         path1 = getIntent().getStringExtra("path1");
         path2 = getIntent().getStringExtra("path2");




    }
}
