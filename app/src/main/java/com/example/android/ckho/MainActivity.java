package com.example.android.ckho;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{

    // Declaring Variables
    private Button logOut;
    private TextView Name,EmailId;
    private String url;
    private ImageView profilePicture;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent mainintent = getIntent();


        //Initialising Variables
        logOut = (Button)findViewById(R.id.logout_button);
        Name = (TextView)findViewById(R.id.profile_name);
        EmailId = (TextView)findViewById(R.id.profile_id);
        profilePicture = (ImageView)findViewById(R.id.profile_pic);

        //Set on click listener to logout button
        logOut.setOnClickListener(this);


        Name.setText(mainintent.getStringExtra("name"));
        EmailId.setText(mainintent.getStringExtra("emailId"));
        url = mainintent.getStringExtra("imgUrl");
        Glide.with(this).load(url).into(profilePicture);



        //Creating dummy data list of events
        ArrayList<Event> upcomingEvent = new ArrayList<Event>();
        ArrayList<Event> pastEvent = new ArrayList<Event>();

        upcomingEvent.add(new Event("Intro to C++","30:01:18,10:00 AM","91Springboard"));
        upcomingEvent.add(new Event("Continue with C++","05:02:18,12:00 AM","91Springboard"));

        pastEvent.add(new Event("What is Programming","30:12:17,10:00 AM","91Springboard"));
        pastEvent.add(new Event("How WhatsApp works","23:12:17,12:00 AM","91Springboard"));
        pastEvent.add(new Event("What is Bitcoin","03:12:17,11:00 AM","Innov8"));
        pastEvent.add(new Event("What is Opensource?","29:11:17,10:00 AM","Unboxed"));

        final EventAdapter pastEventAdapter = new EventAdapter(this,pastEvent);
        final EventAdapter upcomingEventAdapter = new EventAdapter(this,upcomingEvent);

        ListView upcomingListView = (ListView)findViewById(R.id.upcoming_list);
        ListView pastListView = (ListView)findViewById(R.id.past_list);

        upcomingListView.setAdapter(upcomingEventAdapter);
        pastListView.setAdapter(pastEventAdapter);

        upcomingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Event currentEvent = upcomingEventAdapter.getItem(position);
                String eventName = currentEvent.getEventName();
                String date = currentEvent.getDateTime();
                String location = currentEvent.getLocation();
                String list = "upcoming";

                Intent intent = new Intent(MainActivity.this,Register.class);
                intent.putExtra("eventName",eventName);
                intent.putExtra("date",date);
                intent.putExtra("location",location);
                intent.putExtra("list",list);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

        LogOut();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void LogOut()
    {

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Intent logoutintent = new Intent(getApplicationContext(),Login.class);
                startActivity(logoutintent);
            }
        });
    }
    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.disconnect();
        googleApiClient.connect();
        super.onStart();
    }
}
