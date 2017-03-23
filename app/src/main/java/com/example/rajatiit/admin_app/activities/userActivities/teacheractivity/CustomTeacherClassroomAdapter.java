package com.example.rajatiit.admin_app.activities.userActivities.teacheractivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.insti.Classroom;
import com.example.rajatiit.admin_app.dataclasses.insti.Institute;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;

import java.util.Arrays;
import java.util.List;


public class CustomTeacherClassroomAdapter extends ArrayAdapter<Classroom>{
    private List<String> TimeSlot = Arrays.asList("08:00 AM", "09:00 AM", "10:00 AM","11:00 AM","01:00 PM","02:00 AM");

    public CustomTeacherClassroomAdapter(Context context, int resource, List<Classroom> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.show_custom_classroom, null);
        }
        TextView timeDisplay = (TextView) convertView.findViewById(R.id.show_custom_slot_time);
        timeDisplay.setText(TimeSlot.get(position));

        if(getItem(position).getRoomId() == -1){
            TextView batch = (TextView) convertView.findViewById(R.id.show_custom_batch);
            batch.setText("No class");
        } else {
            TextView course = (TextView) convertView.findViewById(R.id.show_custom_course);
            course.setText(getItem(position).getCourseDetail().getName());
            TextView batch = (TextView) convertView.findViewById(R.id.show_custom_batch);
            batch.setText(UserStorage.getBatchDetail(getItem(position).getBatchId()).getUserName());
            TextView room = (TextView) convertView.findViewById(R.id.show_custom_room);
            room.setText(Integer.toString(Institute.getRoomDetail(getItem(position).getRoomId()).getRoomNo()));
        }

        return convertView;
    }
}
