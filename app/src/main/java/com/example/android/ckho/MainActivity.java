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
        LoaderManager.LoaderCallbacks<List<List<Event>>>{

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
    private EventAdapter mSecondAdapter;
    private String uid,uname;

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int EVENT_LOADER_ID = 1;
    private static final String UPCOMING_EVENTS_REQUEST_URL =
            "https://ckho-api-test-desmondanimus.c9users.io/upcoming";
    private static final String PAST_EVENTS_REQUEST_URL =
            "https://ckho-api-test-desmondanimus.c9users.io/past";

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
        mSecondAdapter = new EventAdapter(this,new ArrayList<Event>());
        upcomingEventsListView = (ListView)findViewById(R.id.upcoming_list);
        pastEventsListView = (ListView)findViewById(R.id.past_list);
        mEmptyStateTextView = (TextView)findViewById(R.id.empty_view);

        upcomingEventsListView.setEmptyView(mEmptyStateTextView);

        upcomingEventsListView.setAdapter(mAdapter);
        pastEventsListView.setAdapter(mSecondAdapter);




        //Set on click listener to logout button
        logOut.setOnClickListener(this);


        Name.setText(infointent.getStringExtra("Name"));
        EmailId.setText(infointent.getStringExtra("EmailId"));
        url = infointent.getStringExtra("url");
        Glide.with(this).load(url).into(profilePicture);

        uid = infointent.getStringExtra("EmailId");
        uname = infointent.getStringExtra("Name");


        upcomingEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Event currentEvent = mAdapter.getItem(position);
                String eventName = currentEvent.getEventName();
                String date = currentEvent.getDateTime();
                String location = currentEvent.getLocation();
                String description = currentEvent.getDescription();
                String eid = currentEvent.getEid();

                Intent intent = new Intent(MainActivity.this,Register.class);
                intent.putExtra("eventName",eventName);
                intent.putExtra("date",date);
                intent.putExtra("location",location);
                intent.putExtra("description",description);
                intent.putExtra("uid",uid);
                intent.putExtra("uname",uname);
                intent.putExtra("eid",eid);
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

    }

    @Override
    public Loader<List<List<Event>>> onCreateLoader(int i,Bundle bundle)
    {
        return new EventLoader(this,UPCOMING_EVENTS_REQUEST_URL,PAST_EVENTS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<List<Event>>> loader, List<List<Event>> event) {
        // Set empty state text to display "No Events found."
        mEmptyStateTextView.setText("NO EVENTS");

        // Clear the adapter of previous events data
        mAdapter.clear();
        mSecondAdapter.clear();


        try {

            if ((event.get(0) != null || !event.get(0).isEmpty()) && (event.get(1) != null || !event.get(1).isEmpty())) {

                mAdapter.addAll(event.get(0));
                mSecondAdapter.addAll(event.get(1));
            }
         }catch (NullPointerException np)
        {
            mEmptyStateTextView.setText("NO EVENTS");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<List<Event>>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
        mSecondAdapter.clear();

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
