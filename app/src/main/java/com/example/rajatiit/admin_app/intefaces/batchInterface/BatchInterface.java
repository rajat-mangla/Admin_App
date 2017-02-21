package com.example.rajatiit.admin_app.intefaces.batchInterface;

import android.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ForwardingListener;
import android.view.View;
import android.widget.ListView;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.BatchDetail;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.example.rajatiit.admin_app.intefaces.teacherInterface.CustomTeacherListAdapter;

public class BatchInterface extends AppCompatActivity implements AddEditBatchDialog.BatchDetailsPasser{

    // Fragment TAG Passed to DIALOG FRAGMENT to identify ADD DIALOG
    private final String ADD_DIALOG = "Add_Dialog";

    // Fragment TAG Passed to DIALOG FRAGMENT to identify EDIT DIALOG
    private final String EDIT_DIALOG = "Edit_Dialog";

    // Tag for finding arguements when its a EDIT DIALOG
    private final String BATCH_DATA = "BatchDetails";

    // For getting the position of teacher under department for displaying data for edit dialog
    int batchPosition;

    // Institute class object
    UserStorage userStorage;
    // TODO : SOLVE THE CONFLICT WITH THIS

    // Custom Adapter for batchlist
    CustomBatchListAdapter customBatchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_interface);

        userStorage = new UserStorage();

        // displaying the listview ...
        ListView listView = (ListView) findViewById(R.id.batchList);
        customBatchListAdapter = new CustomBatchListAdapter
                (getBaseContext(),R.layout.activity_batch_interface,userStorage.getBatchDetails());
        listView.setAdapter(customBatchListAdapter);

        //  handling add dialog button
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addDetail);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new AddEditBatchDialog();
                dialogFragment.show(getFragmentManager(),ADD_DIALOG);
            }
        });
    }


    @Override
    public void passAddDialogDetail(BatchDetail batchDetail) {
        userStorage.getBatchDetails().add(batchDetail);
        customBatchListAdapter.notifyDataSetChanged();
    }
    @Override
    public void passEditDialogDetail(BatchDetail batchDetail) {

    }
}
