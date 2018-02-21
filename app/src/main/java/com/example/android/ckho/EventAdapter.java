package com.example.android.ckho;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shenron on 021, 21 Jan 2018.
 */

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(@NonNull Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Event currentEvent = getItem(position);

        TextView eventNameTextView = (TextView)listItemView.findViewById(R.id.event_name);
        eventNameTextView.setText(currentEvent.getEventName());


        TextView dateTimeTextView = (TextView)listItemView.findViewById(R.id.date_time);
        dateTimeTextView.setText(currentEvent.getDateTime());

        TextView locationTextView = (TextView)listItemView.findViewById(R.id.location);
        locationTextView.setText(currentEvent.getLocation());

        TextView descriptionTextView = (TextView)listItemView.findViewById(R.id.description);
        descriptionTextView.setText(currentEvent.getDescription());


        return listItemView;
    }

}
