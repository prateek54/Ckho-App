package com.example.android.ckho;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import in.shadowfax.proswipebutton.ProSwipeButton;

public class Register extends AppCompatActivity {

    private TextView eventName,dateTime,location,description;
    private ProSwipeButton registerBtn;
    private boolean list;
    private String registratonUrl="https://ckho-api-test-desmondanimus.c9users.io/register";
    private String eid,uid,uname;
    private String responseCode;
    private int resCode;
    public static final String LOG_TAG = Register.class.getName();


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

        eid = intent.getStringExtra("eid");
        uid = intent.getStringExtra("uid");
        uname = intent.getStringExtra("uname");



        /**registerBtn.setOnClickListener(new View.OnClickListener() {

         public void onClick(View v) {
         onClickRegister(v);
         }
         });**/


        registerBtn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                // user has swiped the btn. Perform your async operation now

                StringRequest stringRequest  = new StringRequest(Request.Method.POST, registratonUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                 responseCode = response.substring(0,2);
                                 resCode = Integer.parseInt(responseCode);
                                Log.e(LOG_TAG, "resCode : " + resCode);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("uname",uname);
                        params.put("uid",uid);
                        params.put("eid",eid);

                        return params;
                    }
                };
                Singleton.getInstance(Register.this).addToRequestqueue(stringRequest);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // task success! show TICK icon in ProSwipeButton
                        onClickRegister();
                    }
                }, 1000);
            }
        });

    }
    public void onClickRegister()
    {
        if(resCode==200) {
            registerBtn.showResultIcon(true); // false if task failed
            Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
        }
        else
        {
            registerBtn.showResultIcon(false); // false if task failed
            Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show();
        }

    }
}
