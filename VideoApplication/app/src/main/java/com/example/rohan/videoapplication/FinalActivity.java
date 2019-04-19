package com.example.rohan.videoapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

public class FinalActivity extends AppCompatActivity {

    private static final String TAG = FinalActivity.class.getName();

    MediaController mediaController;
    ImageView whatsapp,fb;
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;


    private String path;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        mediaController = new MediaController(this);

        path = getIntent().getStringExtra("path");

        VideoView videoView = (VideoView) findViewById(R.id.video);
        whatsapp = (ImageView) findViewById(R.id.whtsapp);
        fb = (ImageView) findViewById(R.id.fb);

        videoView.setVideoPath(path);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        mediaController.requestFocus();
        videoView.start();

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        // Create a callbackManager to handle the login responses.
        callbackManager = CallbackManager.Factory.create();

        shareDialog = new ShareDialog(this);


        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(path);
                Intent videoshare = new Intent(Intent.ACTION_SEND);
                videoshare.setType("*/*");
                videoshare.setPackage("com.whatsapp");
                videoshare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                videoshare.putExtra(Intent.EXTRA_STREAM, uri);

                startActivity(videoshare);
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        if (requestCode == 1) {
            Uri videoFileUri = data.getData();
            ShareVideo video = new ShareVideo.Builder()
                    .setLocalUrl(videoFileUri)
                    .build();
            ShareVideoContent content = new ShareVideoContent.Builder()
                    .setVideo(video)
                    .build();

            shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
        } else {
            // Call callbackManager.onActivityResult to pass login result to the LoginManager via callbackManager.
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    private FacebookCallback<Sharer.Result> callback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onSuccess(Sharer.Result result) {
            Toast.makeText(getApplicationContext(),"Succesfully posted",Toast.LENGTH_LONG).show();
            }

        @Override
        public void onCancel() {

            Toast.makeText(getApplicationContext(),"Cancel occured",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(FacebookException error) {
            Toast.makeText(getApplicationContext(),"Error : "+error.getMessage().toString(),Toast.LENGTH_LONG).show();
             }
    };



}


