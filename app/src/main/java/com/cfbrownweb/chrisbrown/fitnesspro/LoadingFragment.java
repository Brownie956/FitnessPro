package com.cfbrownweb.chrisbrown.fitnesspro;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;


public class LoadingFragment extends Fragment {

    private final String TAG = "Chris";

    private TextView percentage;
    private boolean toggle = true;
    private int count;
    private double parentWidth;

    /* Handler messages get put into activity looper queue
    * Non-static inner classes hold a reference to their outer-class
    * This means unless you use a weak reference the outer-class context
    * isn't garbage collected if it the looper hasn't processed all messages
    * in the queue. Static inner classes don't have a reference to their outer
    * class */
    private static class MyHandler extends Handler {
        private final WeakReference<LoadingFragment> fragmentActivity;

        private MyHandler(LoadingFragment loadingFragment){
            fragmentActivity = new WeakReference<>(loadingFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            LoadingFragment fa = fragmentActivity.get();
            fa.parentWidth = ((View)fa.percentage.getParent()).getWidth();
            if(fa.count < 1){
                fa.percentage.setWidth(fa.count);
            }
            else {
                double width = ((fa.parentWidth / 100) * fa.count);
                fa.percentage.setWidth((int)Math.floor(width));
            }
            fa.percentage.setText(fa.count + "%");
        }
    }

    private final MyHandler handler = new MyHandler(this);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);

        percentage = view.findViewById(R.id.loading_percentage);

        return view;
    }

    public void startLoader(){
//        int width;
//        if(toggle){
//            int parentWidth = ((View)percentage.getParent()).getWidth();
//            width = parentWidth / 2;
//            percentage.setWidth(width);
//            percentage.setText(width + "%");
//            toggle = false;
//        }
//        else {
//            int parentWidth = ((View)percentage.getParent()).getWidth();
//            width = parentWidth;
//            percentage.setWidth(width);
//            percentage.setText(width + "%");
//            toggle = true;
//        }

        count = 0;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    try {
                        while (count <= 100) {
                            handler.sendEmptyMessage(0);
                            wait(100);
                            count++;
                        }
                    }
                    catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
