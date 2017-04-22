package com.example.rajatiit.admin_app.activities.userActivities.batchActivity;

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


public class CustomBatchClassroomAdapter extends ArrayAdapter<Classroom>{
    private List<String> TimeSlot = Arrays.asList("08:00 AM - 09:00 AM", "09:00 AM - 10:00 AM", "10:00 AM - 11:00 AM"
            ,"11:00 AM - 12:00 AM","01:00 PM - 02:00 AM","02:00 AM - 03:00 AM");

    public CustomBatchClassroomAdapter(Context context, int resource, List<Classroom> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.show_custom_classroom, null);
        }
        TextView timeDisplay = (TextView) convertView.findViewById(R.id.show_custom_classrom_time);
        timeDisplay.setText(TimeSlot.get(position));

        TextView teacher = (TextView) convertView.findViewById(R.id.show_custom_classrom_teacherorbatch);
        TextView course = (TextView) convertView.findViewById(R.id.show_custom_classrom_course);
        TextView room = (TextView) convertView.findViewById(R.id.show_custom_classrom_room);


        if(getItem(position).getRoomId() == -1){

            course.setText("No class");
            room.setVisibility(View.GONE);
            teacher.setVisibility(View.GONE);
        }
        else {

            String teacherName = UserStorage.getTeacherDetail(getItem(position).getTeacherId()).getFirstName();
            teacherName = teacherName + " " + UserStorage.getTeacherDetail(getItem(position).getTeacherId()).getLastName();

            course.setText(getItem(position).getCourseDetail().getName());
            teacher.setText(teacherName);
            room.setText(String.valueOf(Institute.getRoomDetail(getItem(position).getRoomId()).getRoomNo()));
        }
        return convertView;
    }
}
