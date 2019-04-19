package com.example.rohan.videoapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private Button p2,p3,p4,create_btn;
    private TextView create_video,title,shoot,shootfootage,audio_track,t2,t3;
    private String video_title,path,click,secondpath,audiopath;
    private ImageView imageView,p1,imageView2;
    private Uri uri1,uri2,uri3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        p1 = (ImageView) findViewById(R.id.p1);
        create_video = (TextView) findViewById(R.id.create_video);
        title = (TextView) findViewById(R.id.t1);
        imageView=(ImageView)findViewById(R.id.image);
        imageView2=(ImageView)findViewById(R.id.image1);
        shootfootage=(TextView)findViewById(R.id.shoot_footage);
        t2=(TextView)findViewById(R.id.t2);

        t3=(TextView)findViewById(R.id.t3);

        p4=(Button)findViewById(R.id.p4);
        create_btn=(Button)findViewById(R.id.main_btn);
        shoot=(TextView)findViewById(R.id.shoot_interview);
        audio_track=(TextView)findViewById(R.id.audio_track);

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);

                LinearLayout linearLayout = new LinearLayout(SecondActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(50, 0, 50, 100);

                final EditText newvideoname = new EditText(SecondActivity.this);

                newvideoname.setLayoutParams(layoutParams);
                newvideoname.setGravity(Gravity.TOP | Gravity.START);
                newvideoname.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

                linearLayout.addView(newvideoname, layoutParams);


                builder.setTitle("Create Video Title");
                builder.setMessage("   ");
                builder.setView(linearLayout);


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        video_title = String.valueOf(newvideoname.getText());
                        title.setText("Video Title : "+video_title);


                        create_video.setEnabled(false);
                        create_video.setTextColor(Color.TRANSPARENT);

                    }
                });
                builder.show();

            }


        });

        p2=(Button)findViewById(R.id.p2);

        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);

                LinearLayout linearLayout = new LinearLayout(SecondActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(50, 0, 50, 50);

                final Button shootusingcamera= new Button(SecondActivity.this);
                final Button gallery=new Button(SecondActivity.this);

                shootusingcamera.setText("Shoot a video now");
                shootusingcamera.setBackgroundColor(Color.WHITE);
                shootusingcamera.setLayoutParams(layoutParams);
                shootusingcamera.setGravity(Gravity.TOP | Gravity.START);
                linearLayout.addView(shootusingcamera, layoutParams);

                gallery.setText("Select a video from gallery");
                gallery.setBackgroundColor(Color.WHITE);
                gallery.setLayoutParams(layoutParams);
                gallery.setGravity(Gravity.BOTTOM | Gravity.START);
                linearLayout.addView(gallery, layoutParams);



                builder.setMessage("Select any one action from below:");
                builder.setView(linearLayout);

                shootusingcamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent callforcamera=new Intent();
                        callforcamera.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
                        startActivityForResult(callforcamera,0);


                    }
                });

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("video/*");
                        startActivityForResult(intent,100);

                    }
                });


                builder.show();


            }
        });


        p3=(Button)findViewById(R.id.p3);
        p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);

                LinearLayout linearLayout = new LinearLayout(SecondActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(50, 0, 50, 50);

                final Button shootusingcamera= new Button(SecondActivity.this);
                final Button gallery=new Button(SecondActivity.this);

                shootusingcamera.setText("Shoot a video now");
                shootusingcamera.setBackgroundColor(Color.WHITE);
                shootusingcamera.setLayoutParams(layoutParams);
                shootusingcamera.setGravity(Gravity.TOP | Gravity.START);
                linearLayout.addView(shootusingcamera, layoutParams);

                gallery.setText("Select a video from gallery");
                gallery.setBackgroundColor(Color.WHITE);
                gallery.setLayoutParams(layoutParams);
                gallery.setGravity(Gravity.BOTTOM | Gravity.START);
                linearLayout.addView(gallery, layoutParams);



                builder.setMessage("Select any one action from below:");
                builder.setView(linearLayout);


                shootusingcamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent callforcamera=new Intent();
                        callforcamera.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
                        startActivityForResult(callforcamera,1);


                    }
                });

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("video/*");
                        startActivityForResult(intent,101);

                    }
                });
                builder.show();
            }
        });

        p4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);

                LinearLayout linearLayout = new LinearLayout(SecondActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(50, 0, 50, 50);

                final Button shootusingcamera= new Button(SecondActivity.this);
                final Button gallery=new Button(SecondActivity.this);

                shootusingcamera.setText("Record now");
                shootusingcamera.setBackgroundColor(Color.WHITE);
                shootusingcamera.setLayoutParams(layoutParams);
                shootusingcamera.setGravity(Gravity.TOP | Gravity.START);
                linearLayout.addView(shootusingcamera, layoutParams);

                gallery.setText("Select a audio from media");
                gallery.setBackgroundColor(Color.WHITE);
                gallery.setLayoutParams(layoutParams);
                gallery.setGravity(Gravity.BOTTOM | Gravity.START);
                linearLayout.addView(gallery, layoutParams);



                builder.setMessage("Select any one action from below:");
                builder.setView(linearLayout);


                shootusingcamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent callforcamera=new Intent();
                        callforcamera.setAction(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                        startActivityForResult(callforcamera,2);


                    }
                });

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("audio/*");
                        startActivityForResult(intent,102);

                    }
                });
                builder.show();
            }
        });
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);

                LinearLayout linearLayout = new LinearLayout(SecondActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(50, 0, 50, 100);

                final EditText newvideoname1 = new EditText(SecondActivity.this);

                newvideoname1.setLayoutParams(layoutParams);
                newvideoname1.setGravity(Gravity.TOP | Gravity.START);
                newvideoname1.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

                linearLayout.addView(newvideoname1, layoutParams);


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
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String video_name=newvideoname1.getText().toString();

                        Intent intent=new Intent(SecondActivity.this,MergeActivity.class);
                        intent.putExtra("uri1",uri1.toString());
                        intent.putExtra("uri2",uri2.toString());
                        intent.putExtra("uri3",uri3.toString());
                        intent.putExtra("name",video_name);
                        startActivity(intent);

                    }
                });
                builder.show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == RESULT_OK)
        {
            uri1=data.getData();
            path=getRealPathfromuri(getApplicationContext(),uri1);
            Bitmap bitmap=ThumbnailUtils.createVideoThumbnail(path,MediaStore.Video.Thumbnails.MINI_KIND);
            imageView.setImageBitmap(bitmap);
            shoot.setTextColor(Color.TRANSPARENT);
            t2.setText(" ");

        }
       else if(requestCode == 100 && resultCode == RESULT_OK)
        {
            uri1=data.getData();
            path=getRealPathfromuri(getApplicationContext(),uri1);
            Bitmap bitmap=ThumbnailUtils.createVideoThumbnail(path,MediaStore.Video.Thumbnails.MINI_KIND);
            imageView.setImageBitmap(bitmap);
            shoot.setTextColor(Color.TRANSPARENT);
            t2.setText(" ");
        }


        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            uri2=data.getData();
            secondpath=getRealPathfromuri(getApplicationContext(),uri2);
            Bitmap bitmap=ThumbnailUtils.createVideoThumbnail(secondpath,MediaStore.Video.Thumbnails.MINI_KIND);
            imageView2.setImageBitmap(bitmap);
            shootfootage.setTextColor(Color.TRANSPARENT);
            t3.setText(" ");
        }
        else if(requestCode == 101 && resultCode == RESULT_OK)
        {
            uri2=data.getData();
            secondpath=getRealPathfromuri(getApplicationContext(),uri2);
            Bitmap bitmap=ThumbnailUtils.createVideoThumbnail(secondpath,MediaStore.Video.Thumbnails.MINI_KIND);
            imageView2.setImageBitmap(bitmap);
            shootfootage.setTextColor(Color.TRANSPARENT);
            t2.setText(" ");
        }
        if(requestCode == 2 && resultCode == RESULT_OK)
        {
            uri3=data.getData();
            audiopath = getRealPathfromuri(getApplicationContext(), uri3);

            audio_track.setText("Added audio track is :"+audiopath);

        }
        else if(requestCode == 102 && resultCode == RESULT_OK)
        {
            uri3=data.getData();
            audiopath=getRealPathfromuri(getApplicationContext(),uri3);

            audio_track.setText("Added audio track is :"+audiopath);
        }
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
