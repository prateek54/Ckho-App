package com.example.android.ckho;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class EventLoader extends AsyncTaskLoader<List<List<Event>>>{

    /** Tag for log messages */
    private static final String LOG_TAG = EventLoader.class.getName();

    /** Query URL */
    private String pastUrl,upcomingUrl;
    private List<List<Event>> totalEvent= new ArrayList<>();


    public EventLoader(Context context,String upcomingurl,String pasturl) {
        super(context);
        pastUrl=pasturl;
        upcomingUrl=upcomingurl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }



    @Override
    public List<List<Event>> loadInBackground() {
        if (pastUrl == null || upcomingUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of events.
        List<Event> pastEvent = QueryUtils.fetchEventData(pastUrl);
        List<Event> upcomingEvent = QueryUtils.fetchEventData(upcomingUrl);

        totalEvent.add(upcomingEvent);
        totalEvent.add(pastEvent);
        return totalEvent;
    }
}
