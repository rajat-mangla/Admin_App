package com.example.rajatiit.admin_app.activities.userActivities.adminActivity.AdminFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.timetablehandler.SlotDetails;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rajat on 28/2/17.
 */

public class CustomSlotsListViewAdapter extends ArrayAdapter<SlotDetails>{

    private List<String> TimeSlot = Arrays.asList("08:00 AM", "09:00 AM", "10:00 AM","11:00 AM","01:00 PM","02:00 AM");

    public CustomSlotsListViewAdapter(Context context, int resource, List<SlotDetails> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.show_custom_slot,null);
        }
        TextView timeDisplay = (TextView) convertView.findViewById(R.id.show_custom_slot_time);
        timeDisplay.setText(TimeSlot.get(position));
        TextView slotName = (TextView) convertView.findViewById(R.id.show_custom_slot_name);
        slotName.setText(getItem(position).getName());

        return convertView;
    }
}
