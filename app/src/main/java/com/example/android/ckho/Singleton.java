package com.example.android.ckho;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Shenron on 015, 15 Mar 2018.
 */

public class Singleton {

    private static Singleton mInstance;
    private RequestQueue requestQueue;
    private static Context context;

    private Singleton(Context mContext)
    {
        context = mContext;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized Singleton getInstance(Context mContext)
    {
        if(mInstance==null)
        {
            mInstance=new Singleton(mContext);
        }
        return mInstance;
    }

    public <T>void addToRequestqueue(Request<T> request)
    {
        requestQueue.add(request);
    }

}
