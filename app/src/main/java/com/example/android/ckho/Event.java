package com.example.android.ckho;

/**
 * Created by Shenron on 021, 21 Jan 2018.
 */

public class Event {

    private String mEventName,mDateTime,mLocation,mDescription;

    public Event(String eventName,String dateTime,String location)
    {
        mEventName=eventName;
        mDateTime=dateTime;
        mLocation=location;
    }
    public Event(String eventName,String dateTime,String location,String description)
    {
        mEventName=eventName;
        mDateTime=dateTime;
        mLocation=location;
        mDescription=description;
    }

    public String getEventName()
    {
        return mEventName;
    }

    public String getDateTime()
    {
        return mDateTime;
    }

    public String getLocation()
    {
        return mLocation;
    }
    public String getDescription(){return mDescription;}

}
