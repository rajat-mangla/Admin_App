package com.example.rajatiit.admin_app.teacherInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.rajatiit.admin_app.FirebaseClass;
import com.example.rajatiit.admin_app.dataclasses.Institute;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.Firebase;

/**
 * Created by RajatIIT on 10-02-2017.
 */

public class CustomTeacherInterfaceListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private static Institute institute;

    public CustomTeacherInterfaceListAdapter(Context context) {
        this.context = context;
        if (institute == null) {
            institute = new Institute();
            Firebase firebase = new Firebase(FirebaseClass.URL);
            firebase.child("Institute").setValue(institute);
        }
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
            convertView.setPadding(100, 0, 0, 0);
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        String departmentName = institute.getDepartmentDetail(groupPosition).getName();
        textView.setText(departmentName);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        String teacherName = institute.getDepartmentDetail(groupPosition).getTeacherDetail(childPosition).getFirstName();
        textView.setText(teacherName);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getGroupCount() {
        return institute.getTotalDepartments();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return institute.getDepartmentDetail(groupPosition).getTeacherDetail(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return institute.getDepartmentDetail(groupPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return institute.getDepartmentDetail(groupPosition).noOfTeachers();
    }

}
