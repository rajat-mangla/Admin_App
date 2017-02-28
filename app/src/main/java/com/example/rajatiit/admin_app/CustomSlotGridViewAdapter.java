package com.example.rajatiit.admin_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rajatiit.admin_app.dataclasses.Classroom;
import com.example.rajatiit.admin_app.dataclasses.Institute;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;

import java.util.List;

/**
 * Created by rajat on 28/2/17.
 */

public class CustomSlotGridViewAdapter extends ArrayAdapter<Classroom>{
    public CustomSlotGridViewAdapter(Context context, int resource, List<Classroom> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.show_custom_slot_gridview_display,null);
        }
        TextView courseId = (TextView) convertView.findViewById(R.id.show_custom_slot_gridview_display_courseId);
        TextView courseName = (TextView) convertView.findViewById(R.id.show_custom_slot_gridview_display_courseName);
        TextView teacherName = (TextView) convertView.findViewById(R.id.show_custom_slot_gridview_display_teacherName);
        TextView batchName = (TextView) convertView.findViewById(R.id.show_custom_slot_gridview_display_batchName);
        TextView roomNo = (TextView) convertView.findViewById(R.id.show_custom_slot_gridview_display_roomNo);


        courseId.setText(getItem(position).getCourseDetail().getId());
        courseName.setText(getItem(position).getCourseDetail().getName());
        teacherName.setText(UserStorage.getTeacherDetail(getItem(position).getTeacherId()).getFirstName()
                            + " " + UserStorage.getTeacherDetail(getItem(position).getTeacherId()).getLastName());
        batchName.setText(UserStorage.getBatchDetail(getItem(position).getBatchId()).getUserName());
        roomNo.setText(Institute.getRoomDetail(getItem(position).getRoomId()).getRoomNo());


        return convertView;
    }
}
