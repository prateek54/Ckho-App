package com.example.android.ckho;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import in.shadowfax.proswipebutton.ProSwipeButton;

public class Register extends AppCompatActivity {

    private TextView eventName,dateTime,location,description;
    private ProSwipeButton registerBtn;
    private boolean list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        int primary = getResources().getColor(R.color.colorPrimary);
//        int secondary = getResources().getColor(R.color.colorPrimaryDark);
//        Slidr.attach(this, primary, secondary);

        Intent intent = getIntent();

        eventName = (TextView) findViewById(R.id.event_name);
        dateTime = (TextView) findViewById(R.id.date_time);
        location = (TextView) findViewById(R.id.location);
        description = (TextView) findViewById(R.id.description);
        registerBtn = (ProSwipeButton) findViewById(R.id.register_button);


        eventName.setText(intent.getStringExtra("eventName"));
        dateTime.setText(intent.getStringExtra("date"));
        location.setText(intent.getStringExtra("location"));
        description.setText(intent.getStringExtra("description"));



        /**registerBtn.setOnClickListener(new View.OnClickListener() {

         public void onClick(View v) {
         onClickRegister(v);
         }
         });**/


        registerBtn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                // user has swiped the btn. Perform your async operation now
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // task success! show TICK icon in ProSwipeButton
                        onClickRegister();
                        registerBtn.showResultIcon(true); // false if task failed
                    }
                }, 1000);
            }
        });

    }
    public void onClickRegister()
    {
        Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();

    }
}
