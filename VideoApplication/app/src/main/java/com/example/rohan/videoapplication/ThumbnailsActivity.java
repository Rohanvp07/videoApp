package com.example.rohan.videoapplication;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

public class ThumbnailsActivity extends AppCompatActivity {

    private String path;
    private String p;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnails);


        path=getIntent().getStringExtra("path");

        Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();

        ImageView thumbnail_mini = (ImageView)findViewById(R.id.Thumbnail);

        Bitmap bitmap=ThumbnailUtils.createVideoThumbnail(path,MediaStore.Video.Thumbnails.MINI_KIND);

        thumbnail_mini.setImageBitmap(bitmap);



    }
    private String getRealPathfromuri(Context context, Uri uri)
    {
        Cursor cursor=null;

        try
        {
            String[] proj = {MediaStore.Images.Media.DATA};

            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();

            return cursor.getString(column_index);


        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        finally
        {
            if(cursor!=null)
            {
                cursor.close();
            }
        }
    }
}
