package com.example.rajatiit.admin_app.intefaces.batchInterface;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.BatchDetail;

import java.util.List;

/**
 * Created by rajat on 20/2/17.
 */

public class CustomBatchListAdapter extends ArrayAdapter<BatchDetail> {
    public CustomBatchListAdapter(Context context, int resource, List<BatchDetail> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.display_batch,null);
        }
        TextView department = (TextView) convertView.findViewById(R.id.display_batch_departmentName);
        TextView year = (TextView) convertView.findViewById(R.id.display_batch_batchYear);
        department.setText(getItem(position).getDepartment());
        year.setText(getItem(position).getYear());
        return convertView;
    }
}
