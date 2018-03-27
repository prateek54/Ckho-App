package com.example.android.ckho;

/**
 * Created by Shenron on 028, 28 Mar 2018.
 */

public class UserDetails {

    String mName,mEmail,mUrl;

    public UserDetails(String name,String email,String url)
    {
        mName=name;
        mEmail=email;
        mUrl=url;
    }

    public String getmName()
    {
        return mName;
    }
    public String getmEmail()
    {
        return mEmail;
    }
    public String getmUrl()
    {
        return mUrl;
    }

}
