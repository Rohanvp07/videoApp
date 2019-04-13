package com.example.rohan.video;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private Button record,play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        videoView=(VideoView)findViewById(R.id.videoview);
        record=(Button)findViewById(R.id.record);
        play=(Button)findViewById(R.id.play);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callforcamera=new Intent();
                callforcamera.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(callforcamera,0);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                videoView.start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0 && resultCode==RESULT_OK)
        {
            Uri videouri=data.getData();
            videoView.setVideoURI(videouri);
        }
    }
}
