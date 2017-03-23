package com.example.rajatiit.admin_app.activities.intefaces.roomInterface;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.insti.RoomDetail;

import java.util.List;

/**
 * Created by rajat on 25/2/17.
 */

public class CustomRoomListAdapter extends ArrayAdapter<RoomDetail> {
    public CustomRoomListAdapter(Context context, int resource, List<RoomDetail> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.display_room,null);
        }
        TextView roomNo = (TextView) convertView.findViewById(R.id.display_room_roomNo);
        roomNo.setText(Integer.toString(getItem(position).getRoomNo()));
        return convertView;
    }
}
