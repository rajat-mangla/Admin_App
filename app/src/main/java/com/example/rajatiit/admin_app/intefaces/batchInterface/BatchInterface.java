package com.example.rajatiit.admin_app.intefaces.batchInterface;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ForwardingListener;
import android.view.View;
import android.widget.ListView;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.example.rajatiit.admin_app.intefaces.teacherInterface.CustomTeacherListAdapter;

public class BatchInterface extends AppCompatActivity {

    // fragment TAG Passed to DIALOG FRAGMENT to identify ADD DIALOG
    private final String ADD_DIALOG = "Add_Dialog";

    // fragment TAG Passed to DIALOG FRAGMENT to identify EDIT DIALOG
    private final String EDIT_DIALOG = "Edit_Dialog";

    // tag for finding arguements when its a EDIT DIALOG
    private final String TEACHER_DATA = "TeacherDetails";


    // for getting the position of teacher under department for displaying data for edit dialog
    int batchPosition;

    // Institute class object
    UserStorage userStorage;
    // TODO : SOLVE THE CONFLICT WITH THIS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_interface);

        userStorage = new UserStorage();

        ListView listView = (ListView) findViewById(R.id.batchList);
        CustomBatchListAdapter customBatchListAdapter = new CustomBatchListAdapter
                (getBaseContext(),R.layout.activity_batch_interface,userStorage.getBatchDetails());
        listView.setAdapter(customBatchListAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addDetail);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
