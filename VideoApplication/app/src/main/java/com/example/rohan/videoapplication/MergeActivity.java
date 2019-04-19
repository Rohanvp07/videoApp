package com.example.rohan.videoapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class MergeActivity extends AppCompatActivity {

    private Uri uri1, uri2,uri3;
    private String name, u1, u2, u3;
    private int d1, d2, duration;
    private String originalpath1, originalpath2, fileext,originalpath3;
    private File dest;
    private String[] command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);

        name = getIntent().getStringExtra("name");
        u1 = getIntent().getStringExtra("uri1");
        u2 = getIntent().getStringExtra("uri2");
        u3 = getIntent().getStringExtra("uri3");


        uri1 = Uri.parse(u1);
        uri2 = Uri.parse(u2);
        uri3 = Uri.parse(u3);


        MediaPlayer mp1 = MediaPlayer.create(MergeActivity.this, uri1);
        d1 = mp1.getDuration();
        mp1.release();

        MediaPlayer mp2 = MediaPlayer.create(MergeActivity.this, uri2);
        d2 = mp2.getDuration();
        mp2.release();

        duration = (d1 + d2) / 1000;

        File folder = new File(Environment.getExternalStorageDirectory() + "/VideoApplication");
        if (!folder.exists()) {
            folder.mkdir();
        }

        fileext = ".mp4";
        dest = new File(folder, name + fileext);

        originalpath1 = getRealPathfromuri(getApplicationContext(), uri1);
        originalpath2 = getRealPathfromuri(getApplicationContext(), uri2);
        originalpath3 = getRealPathfromuri(getApplicationContext(), uri3);

        command = new String[]{"-y", "-i", originalpath1, "-i", originalpath2,"-strict", "experimental", "-filter_complex",
                "[0:v]scale=1920x1080,setsar=1:1[v0];[1:v]scale=1920x1080,setsar=1:1[v1];[v0][0:a][v1][1:a] concat=n=2:v=1:a=1",
                "-ab", "48000", "-ac", "2", "-ar", "22050", "-s", "1920x1080", "-vcodec", "libx264", "-crf", "27", "-q", "4", "-preset", "ultrafast", dest.getAbsolutePath()};


        Intent intent=new Intent(MergeActivity.this,ProgressbarActivity.class);
        intent.putExtra("command",command);
        intent.putExtra("duration",duration);
        intent.putExtra("destination",dest.getAbsolutePath());
        startActivity(intent);
        finish();

    }


    private String getRealPathfromuri(Context context, Uri uri) {
        Cursor cursor = null;

        try {
            String[] proj = {MediaStore.Images.Media.DATA};

            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();

            return cursor.getString(column_index);


        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}