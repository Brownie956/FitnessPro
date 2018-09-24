package com.cfbrownweb.chrisbrown.fitnesspro;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "app_tag";
    private int width = 200;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.i(TAG, "onCreate");

        Button count_btn = findViewById(R.id.count_button);
        count_btn.setOnLongClickListener(new Button.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                TextView count = findViewById(R.id.count_text);
                width = 200;
                count.setText("width = " + width);
                return true; //Stop future events
            }
        });

        count_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
//                countButtonClick(v);
                //percentage stuff
                triggerLoader();
            }
        });


        //Start background service
        Intent backgroundIntent = new Intent(this, MyBackgroundService.class);
        startService(backgroundIntent);

        Button clockButton = findViewById(R.id.time_button);
        final Intent clockIntent = new Intent(this, ClockActivity.class);
        clockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(clockIntent);
            }
        });
    }

    public void triggerLoader(){
        LoadingFragment loadingFragment = (LoadingFragment) getSupportFragmentManager().findFragmentById(R.id.loading_fragment);
        loadingFragment.startLoader();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    public void buttonClick(View button){
        TextView textView = findViewById(R.id.textView);
        EditText editText = findViewById(R.id.editText);
        textView.setText(editText.getText());
        editText.setText("");
    }

    public void luckyButtonClick(View button){
        TextView textView = findViewById(R.id.textView);
        EditText editText = findViewById(R.id.editText);
        textView.setText("sdfsdfdsfds");
        editText.setText("");

        LinearLayout ll = findViewById(R.id.extra);

        TextView luckyMessage = new TextView(this);
        luckyMessage.setText("You just clicked the lucky button");

        RelativeLayout luckyLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams luckyParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        luckyParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        luckyParams.addRule(RelativeLayout.BELOW, R.id.something_button);
        luckyParams.setMargins(0,0,0,50);

        luckyLayout.addView(luckyMessage, luckyParams);
        luckyLayout.setId(3);

        ll.addView(luckyLayout);
    }

    public void countButtonClick(View button){
        Resources res = getResources();
        //convert dp to px to be user in g setWidth
        if(width < 100){
            width = 200;
        }
        else {
            width--;
        }
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, res.getDisplayMetrics());
        TextView count = findViewById(R.id.count_text);
        count.setText("width = " + width);
        count.setWidth(px);
    }
}
