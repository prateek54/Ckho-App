package com.example.android.ckho;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SpashScreen extends AppCompatActivity {
    boolean firstStart;
    SharedPrefApp sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        sharedPref = SharedPrefApp.getInstance();

        for(int i=0;i<1000;i++)
        {
        }

        SharedPreferences settings = getSharedPreferences("PREFS",0);
        firstStart = settings.getBoolean("firs_time_start", true);

        if(sharedPref.getISLogged_IN(SpashScreen.this))
        {
            startActivity(new Intent(this,MainActivity.class));
        }
        else
        {
            startActivity(new Intent(this,Login.class));
        }


    }
}
