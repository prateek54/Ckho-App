package com.example.android.ckho;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener,
        LoaderManager.LoaderCallbacks<List<Event>>{

    // Declaring Variables
    private Button logOut;
    private TextView Name,EmailId;
    private String url;
    private ImageView profilePicture;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    private EventAdapter mAdapter;
    private ListView upcomingEventsListView;
    private ListView pastEventsListView;

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int EVENT_LOADER_ID = 1;
    private static final String EVENTS_REQUEST_URL =
            "https://ckho-api-test-desmondanimus.c9users.io/nearpast?ecity=Delhi";

    /** TextView that
     *  is displayed when the
     *  list is empty */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent infointent = getIntent();


        //Initialising Variables
        logOut = (Button)findViewById(R.id.logout_button);
        Name = (TextView)findViewById(R.id.profile_name);
        EmailId = (TextView)findViewById(R.id.profile_id);
        profilePicture = (ImageView) findViewById(R.id.profile_pic);
        mAdapter = new EventAdapter(this,new ArrayList<Event>());
        upcomingEventsListView = (ListView)findViewById(R.id.upcoming_list);
        pastEventsListView = (ListView)findViewById(R.id.past_list);
        mEmptyStateTextView = (TextView)findViewById(R.id.empty_view);

        upcomingEventsListView.setAdapter(mAdapter);
        pastEventsListView.setAdapter(mAdapter);




        //Set on click listener to logout button
        logOut.setOnClickListener(this);


        Name.setText(infointent.getStringExtra("Name"));
        EmailId.setText(infointent.getStringExtra("EmailId"));
        url = infointent.getStringExtra("url");
        Glide.with(this).load(url).into(profilePicture);


        upcomingEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Event currentEvent = mAdapter.getItem(position);
                String eventName = currentEvent.getEventName();
                String date = currentEvent.getDateTime();
                String location = currentEvent.getLocation();
                String description = currentEvent.getDescription();

                Intent intent = new Intent(MainActivity.this,Register.class);
                intent.putExtra("eventName",eventName);
                intent.putExtra("date",date);
                intent.putExtra("location",location);
                intent.putExtra("description",description);
                startActivity(intent);

            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected())
        {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EVENT_LOADER_ID, null, this);
        }else{
            // Update empty state with no connection error message
            mEmptyStateTextView.setText("NO INTERNET CONNECTION");
        }





//
//
//        //Creating dummy data list of events
//        ArrayList<Event> upcomingEvent = new ArrayList<Event>();
//        ArrayList<Event> pastEvent = new ArrayList<Event>();
//
//        upcomingEvent.add(new Event("Intro to C++","30:01:18,10:00 AM","91Springboard"));
//        upcomingEvent.add(new Event("Continue with C++","05:02:18,12:00 AM","91Springboard"));
//
//        pastEvent.add(new Event("What is Programming","30:12:17,10:00 AM","91Springboard"));
//        pastEvent.add(new Event("How WhatsApp works","23:12:17,12:00 AM","91Springboard"));
//        pastEvent.add(new Event("What is Bitcoin","03:12:17,11:00 AM","Innov8"));
//        pastEvent.add(new Event("What is Opensource?","29:11:17,10:00 AM","Unboxed"));
//
//        final EventAdapter pastEventAdapter = new EventAdapter(this,pastEvent);
//        final EventAdapter upcomingEventAdapter = new EventAdapter(this,upcomingEvent);
//
//        ListView upcomingListView = (ListView)findViewById(R.id.upcoming_list);
//        ListView pastListView = (ListView)findViewById(R.id.past_list);
//
//        upcomingListView.setAdapter(upcomingEventAdapter);
//        pastListView.setAdapter(pastEventAdapter);
//
//        upcomingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Event currentEvent = upcomingEventAdapter.getItem(position);
//                String eventName = currentEvent.getEventName();
//                String date = currentEvent.getDateTime();
//                String location = currentEvent.getLocation();
//                String list = "upcoming";
//
//                Intent intent = new Intent(MainActivity.this,Register.class);
//                intent.putExtra("eventName",eventName);
//                intent.putExtra("date",date);
//                intent.putExtra("location",location);
//                intent.putExtra("list",list);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public Loader<List<Event>> onCreateLoader(int i,Bundle bundle)
    {
        return new EventLoader(this,EVENTS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Event>> loader, List<Event> event) {
        // Set empty state text to display "No events found."
        mEmptyStateTextView.setText("NO EVENTS");

        // Clear the adapter of previous events data
        mAdapter.clear();

        if (event!= null && !event.isEmpty())
        {
            mAdapter.addAll(event);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Event>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();

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
