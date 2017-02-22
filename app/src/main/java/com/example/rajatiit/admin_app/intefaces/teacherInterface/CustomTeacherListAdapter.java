package com.example.rajatiit.admin_app.intefaces.teacherInterface;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.TeacherDetail;

import java.util.List;

/**
 * Created by rajat on 19/2/17.
 */

public class CustomTeacherListAdapter extends ArrayAdapter<TeacherDetail> {

    public CustomTeacherListAdapter(Context context, int resource, List<TeacherDetail> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.display_teacher,null);
        }
        TeacherDetail teacherDetail = getItem(position);
        TextView firstName = (TextView) convertView.findViewById(R.id.firstName);
        TextView lastName = (TextView) convertView.findViewById(R.id.lastName);
        TextView departmentName = (TextView) convertView.findViewById(R.id.departmentName);

        firstName.setText(teacherDetail.getFirstName());
        lastName.setText(teacherDetail.getLastName());
        departmentName.setText(teacherDetail.getDepartment());

        return convertView;
    }
}
