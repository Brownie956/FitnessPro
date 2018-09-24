package com.cfbrownweb.chrisbrown.fitnesspro;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class ClockActivity extends AppCompatActivity {

    private ClockService clockService;
    private boolean isBound;

    private static class ClockHandler extends Handler {
        private final WeakReference<ClockActivity> fragmentActivity;

        private ClockHandler(ClockActivity loadingFragment){
            fragmentActivity = new WeakReference<>(loadingFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            ClockActivity clockActivity = fragmentActivity.get();
            clockActivity.showTime();
        }
    }

    private final ClockHandler clockHandler = new ClockHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        Intent intent = new Intent(this, ClockService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        Runnable clockStart = new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    try {
                        do{
                            wait(500);
                            clockHandler.sendEmptyMessage(0);
                        } while(isBound);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread clockIntent = new Thread(clockStart);
        clockIntent.start();
    }

    public void showTime(){
        String currentTime = clockService.getCurrentTime();
        TextView clockText = findViewById(R.id.time_text);
        clockText.setText(currentTime);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ClockService.MyBinder binder = (ClockService.MyBinder) service;
            clockService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
}
