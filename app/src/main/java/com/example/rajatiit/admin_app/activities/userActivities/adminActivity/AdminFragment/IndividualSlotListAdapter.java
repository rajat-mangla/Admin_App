package com.example.rajatiit.admin_app.activities.userActivities.adminActivity.AdminFragment;

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

import java.util.List;

/**
 * Created by rajat on 28/2/17.
 */

public class IndividualSlotListAdapter extends ArrayAdapter<Classroom> {

    public IndividualSlotListAdapter(Context context, int resource, List<Classroom> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.show_slot,null);
        }

        TextView courseId = (TextView) convertView.findViewById(R.id.show_slot_courseId);
        TextView courseName = (TextView) convertView.findViewById(R.id.show_slot_courseName);
        TextView teacherName = (TextView) convertView.findViewById(R.id.show_slot_teacherName);
        TextView batchName = (TextView) convertView.findViewById(R.id.show_slot_batchName);
        TextView roomNo = (TextView) convertView.findViewById(R.id.show_slot_roomNo);


        courseId.setText(getItem(position).getCourseDetail().getId());
        courseName.setText(getItem(position).getCourseDetail().getName());
        teacherName.setText(UserStorage.getTeacherDetail(getItem(position).getTeacherId()).getFirstName()
                + " " + UserStorage.getTeacherDetail(getItem(position).getTeacherId()).getLastName());
        batchName.setText(UserStorage.getBatchDetail(getItem(position).getBatchId()).getUserName());

        roomNo.setText(Integer.toString(Institute.getRoomDetail(getItem(position).getRoomId()).getRoomNo()));

        return convertView;
    }
}
