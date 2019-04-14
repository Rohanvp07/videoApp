package com.example.rohan.videomerge;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button firstvideo,secondvideo,save,merge;
    private ImageView video1,video2;
    private Uri uri1,uri2;
    private String originalpath1,originalpath2,fileprefix,fileext;
    private File dest;
    private String[] command;
    private int d1,d2,duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstvideo=(Button)findViewById(R.id.first);
        secondvideo=(Button)findViewById(R.id.second);
        merge=(Button)findViewById(R.id.merge);
        save=(Button)findViewById(R.id.save);
        video1=(ImageView)findViewById(R.id.video1);
        video2=(ImageView)findViewById(R.id.video2);


        firstvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent1.setType("video/*");
                startActivityForResult(intent1,100);

            }
        });



        secondvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent2.setType("video/*");
                startActivityForResult(intent2,101);


            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                originalpath1=getRealPathfromuri(getApplicationContext(),uri1);
                originalpath2=getRealPathfromuri(getApplicationContext(),uri2);

                Bitmap bitmap=ThumbnailUtils.createVideoThumbnail(originalpath1,MediaStore.Video.Thumbnails.MINI_KIND);

                video1.setImageBitmap(bitmap);

                Bitmap bitmap2=ThumbnailUtils.createVideoThumbnail(originalpath2,MediaStore.Video.Thumbnails.MINI_KIND);
                video2.setImageBitmap(bitmap2);

            }
        });


        merge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

                LinearLayout linearLayout=new LinearLayout(MainActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(50,0,50,100);

                final EditText newvideoname=new EditText(MainActivity.this);
                newvideoname.setLayoutParams(layoutParams);
                newvideoname.setGravity(Gravity.TOP|Gravity.START);
                newvideoname.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

                linearLayout.addView(newvideoname,layoutParams);

                builder.setMessage("Wanna set a new video name?");
                builder.setTitle("Change video name");
                builder.setView(linearLayout);



                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        fileprefix=newvideoname.getText().toString();



                        MediaPlayer mp1 = MediaPlayer.create(MainActivity.this,uri1);
                        d1= mp1.getDuration();
                        mp1.release();

                        MediaPlayer mp2 = MediaPlayer.create(MainActivity.this,uri2);
                        d2= mp2.getDuration();
                        mp2.release();

                        duration=(d1+d2)/1000;

                        File folder=new File(Environment.getExternalStorageDirectory()+"/VideoMerge");
                        if(!folder.exists())
                        {
                            folder.mkdir();
                        }

                        fileext=".mp4";
                        dest=new File(folder,fileprefix + fileext);

                        command=new String[]{"-y", "-i", originalpath1, "-i", originalpath2 , "-strict", "experimental", "-filter_complex",
                                "[0:v]scale=480x640,setsar=1:1[v0];[1:v]scale=480x640,setsar=1:1[v1];[v0][0:a][v1][1:a] concat=n=2:v=1:a=1",
                                "-ab", "48000", "-ac", "2", "-ar", "22050", "-s", "480x640", "-vcodec", "libx264","-crf","27","-q","4","-preset", "ultrafast", dest.getAbsolutePath()};


                        Intent intent=new Intent(MainActivity.this,ProgressbarActivity.class);
                        intent.putExtra("command",command);
                        intent.putExtra("duration",duration);
                        intent.putExtra("destination",dest.getAbsolutePath());
                        startActivity(intent);
                        finish();
                        dialog.dismiss();

                    }
                    });

                builder.show();



            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100 && resultCode==RESULT_OK)
        {
            uri1=data.getData();
        }

        if(requestCode==101 && resultCode==RESULT_OK)
        {
            uri2=data.getData();
        }
    }



}
