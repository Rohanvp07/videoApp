package com.example.rohan.videomerge;

import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;

public class ProgressbarActivity extends AppCompatActivity {


    private CircleProgressBar circleProgressBar;
    private String[] command;
    private String path;

    ServiceConnection serviceConnection;
    ffmpegservice mpegservice;
    Integer res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);

        circleProgressBar=(CircleProgressBar)findViewById(R.id.cirleprogress);
        circleProgressBar.setMax(100);

        final Intent i=getIntent();

        command=i.getStringArrayExtra("command");
        path=i.getStringExtra("destination");

        final Intent intent=new Intent(ProgressbarActivity.this,ffmpegservice.class);

        intent.putExtra("command",command);
        intent.putExtra("destination",path);
        startService(intent);

        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ffmpegservice.Localbinder localbinder=(ffmpegservice.Localbinder)service;

                mpegservice= localbinder.getServiceInstance();
                mpegservice.registerclient(getParent());


                final Observer<Integer> resultobserver= new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {

                        res = integer;

                        if(res<100)
                        {
                            circleProgressBar.setProgress(res);
                        }
                        if(res==100)
                        {
                            circleProgressBar.setProgress(res);
                            stopService(intent);
                            Toast.makeText(getApplicationContext(),"Video merged successfully",Toast.LENGTH_LONG).show();

                        }
                    }
                };

                mpegservice.getPercentage().observe(ProgressbarActivity.this,resultobserver);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {


            }
        };


        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
