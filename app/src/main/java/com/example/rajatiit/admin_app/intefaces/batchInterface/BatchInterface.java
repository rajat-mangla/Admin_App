package com.example.rajatiit.admin_app.intefaces.batchInterface;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rajatiit.admin_app.Database;
import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.BatchDetail;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;

public class BatchInterface extends AppCompatActivity implements AddEditBatchDialog.BatchDetailsPasser{

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
        getSupportActionBar().setTitle(R.string.BATCH_DATA);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_display_data_interface);

        userStorage = new UserStorage();

        // displaying the listview ...
        final ListView listView = (ListView) findViewById(R.id.displayDataList);
        customBatchListAdapter = new CustomBatchListAdapter
                (getBaseContext(),R.layout.activity_display_data_interface,userStorage.getBatchDetails());
        listView.setAdapter(customBatchListAdapter);

        //  handling add dialog button
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addDetail);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new AddEditBatchDialog();
                dialogFragment.show(getFragmentManager(),Integer.toString(R.string.ADD_DIALOG));
            }
        });

        // Long item listener for more options ...
        // Adding Context Menu On Item Long Click Listener
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                registerForContextMenu(listView);
                openContextMenu(listView);

                batchPosition =position;

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // setting context menu for batch ...
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.viewItem:
                Toast.makeText(this,"View Details",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.editItem:
                Toast.makeText(this,"Edit Details",Toast.LENGTH_SHORT).show();

                //shows EDIT Dialog
                DialogFragment editDialog = new AddEditBatchDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Integer.toString(R.string.BATCH_DATA), userStorage.getBatchDetail(batchPosition));

                editDialog.setArguments(bundle);
                editDialog.show(getFragmentManager(),"anything");
                return true;

            case R.id.deleteItem:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm Delete")
                        .setMessage(R.string.delete_teacher)
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteBatch();
                            }
                        });
                builder.show();
                return true;
            default:
                return true;
        }
    }

    private void deleteBatch(){
        if (UserStorage.getBatchDetail(batchPosition).getClassroomIds().size() == 0){
            Toast.makeText(getBaseContext(),"Deleted",Toast.LENGTH_SHORT).show();

            UserStorage.deleteBatchDetail(batchPosition);

            // updating in database also
            Database.deleteBatchInfo();

            customBatchListAdapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(getBaseContext(),"Cannot Delete :" +
                    "Batch already Attends some courses",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void passAddDialogDetail(BatchDetail batchDetail) {
        /*
        THE BATCH ID IS USED TO GET BATCH FROM TOTAL BATCHES
         IT IS STORED IN CLASSROOM OBJECT TO LINK Course and teachers
         */
        batchDetail.setBatchId(UserStorage.noOfBatches());

        UserStorage.addBatchDetail(batchDetail);

        Database.sendBatchInfo(batchDetail);

        customBatchListAdapter.notifyDataSetChanged();
    }
    @Override
    public void passEditDialogDetail(BatchDetail batchDetail) {

        Database.sendBatchInfo(batchDetail);

        customBatchListAdapter.notifyDataSetChanged();
    }
}
