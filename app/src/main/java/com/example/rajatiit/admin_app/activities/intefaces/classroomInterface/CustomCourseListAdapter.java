package com.example.rajatiit.admin_app.activities.intefaces.classroomInterface;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.insti.Classroom;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;

import java.util.List;

/**
 * Created by rajat on 22/2/17.
 */

public class CustomCourseListAdapter extends ArrayAdapter<Classroom> {

    public CustomCourseListAdapter(Context context, int resource, List<Classroom> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.display_course,null);
        }
        TextView courseName = (TextView) convertView.findViewById(R.id.display_course_Name);
        TextView courseId = (TextView) convertView.findViewById(R.id.display_course_courseId);
        courseName.setText(getItem(position).getCourseDetail().getName());
        courseId.setText(getItem(position).getCourseDetail().getId());


        String teacher = UserStorage.getTeacherDetail(getItem(position).getTeacherId()).getFirstName();
        teacher = teacher + " "+ UserStorage.getTeacherDetail(getItem(position).getTeacherId()).getLastName();

        TextView teacherName = (TextView) convertView.findViewById(R.id.display_course_teacherName);
        TextView batchName = (TextView) convertView.findViewById(R.id.display_course_batchName);

        teacherName.setText(teacher);
        batchName.setText(UserStorage.getBatchDetail(getItem(position).getBatchId()).getUserName());

        return convertView;
    }
}
