package com.example.android.ckho;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class EventLoader extends AsyncTaskLoader<List<Event>>{

    /** Tag for log messages */
    private static final String LOG_TAG = EventLoader.class.getName();

    /** Query URL */
    private String mUrl;


    public EventLoader(Context context,String url) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }



    @Override
    public List<Event> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news.
        List<Event> event = QueryUtils.fetchEventData(mUrl);
        return event;
    }
}
