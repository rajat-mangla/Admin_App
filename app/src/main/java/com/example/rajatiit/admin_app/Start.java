package com.example.rajatiit.admin_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

public class Start extends AppCompatActivity {

    private String TAG = "START ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //ProgressBar progressBar = (ProgressBar) findViewById(R.id.activity_start_progressBar);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                if (sp.getBoolean(Login.ADMIN_LOGIN_CHECK, false)) {
                    Intent intent = new Intent(Start.this, Intermediate.class);
                    startActivity(intent);
                    finish();
                } else if (sp.getBoolean(Login.TEACHER_LOGIN_CHECK, false)) {
                    Intent intent = new Intent(Start.this, Intermediate.class);
                    startActivity(intent);
                    finish();
                } else if (sp.getBoolean(Login.BATCH_LOGIN_CHECK, false)) {
                    Intent intent = new Intent(Start.this, Intermediate.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Start.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);



    }


}
