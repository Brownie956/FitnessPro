package com.cfbrownweb.chrisbrown.fitnesspro;

import android.app.IntentService;
import android.content.Intent;

public class MyBackgroundService extends IntentService {

    public MyBackgroundService() {
        super("MyBackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
