package com.example.android.ckho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private TextView eventName,dateTime,location,description;
    private Button registerBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();

        eventName=(TextView)findViewById(R.id.event_name);
        dateTime=(TextView)findViewById(R.id.date_time);
        location=(TextView)findViewById(R.id.location);
        description=(TextView)findViewById(R.id.description);
        registerBtn=(Button)findViewById(R.id.register_button);

        eventName.setText(intent.getStringExtra("eventName"));
        dateTime.setText(intent.getStringExtra("date"));
        location.setText(intent.getStringExtra("location"));
        description.setText("Random Decription from server");


        registerBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                onClickRegister(v);
            }
        });
    }
    public void onClickRegister(View v)
    {
        Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
    }
}
