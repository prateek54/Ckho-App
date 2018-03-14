package com.example.android.ckho;

/**
 * Created by Shenron on 021, 21 Jan 2018.
 */

public class Event {

    private String mEventName,mDateTime,mLocation,mDescription,eid;

    public Event(String eventName,String dateTime,String location,String eId)
    {
        mEventName=eventName;
        mDateTime=dateTime;
        mLocation=location;
        eid = eId;
    }
    public Event(String eventName,String dateTime,String location,String description,String eId)
    {
        mEventName=eventName;
        mDateTime=dateTime;
        mLocation=location;
        mDescription=description;
        eid = eId;
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

    public String getEid(){return eid;}

}
