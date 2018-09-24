package com.cfbrownweb.chrisbrown.fitnesspro;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClockService extends Service {

    private final IBinder binder = new MyBinder();

    public ClockService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public String getCurrentTime(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        return (df.format(new Date()));
    }

    public class MyBinder extends Binder {
        ClockService getService(){
            return ClockService.this;
        }
    }
}
