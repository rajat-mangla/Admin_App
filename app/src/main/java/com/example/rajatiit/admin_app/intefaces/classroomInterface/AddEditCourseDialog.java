package com.example.rajatiit.admin_app.intefaces.classroomInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.rajatiit.admin_app.R;

/**
 * Created by rajat on 23/2/17.
 */

public class AddEditCourseDialog extends DialogFragment{

    View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.course_add_edit,null);
        builder.setView(view)
                .setTitle("Add Course")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nothing here
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // DO Nothing
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        Button button = (Button) view.findViewById(R.id.course_add_edit_assignTeacher);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AssignTeacherOrBatch.class);
                startActivity(intent);
            }
        });
    }
}
