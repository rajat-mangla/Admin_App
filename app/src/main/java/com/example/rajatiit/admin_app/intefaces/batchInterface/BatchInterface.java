package com.example.rajatiit.admin_app.intefaces.batchInterface;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rajatiit.admin_app.FirebaseClass;
import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.BatchDetail;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;

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
        getSupportActionBar().setTitle("Batch Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.display_data_interface);

        userStorage = new UserStorage();

        // displaying the listview ...
        final ListView listView = (ListView) findViewById(R.id.displayDataList);
        customBatchListAdapter = new CustomBatchListAdapter
                (getBaseContext(),R.layout.display_data_interface,userStorage.getBatchDetails());
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
        Log.e("tag",item.getTitle().toString());

        switch(item.getItemId()){
            case R.id.viewItem:
                Toast.makeText(this,"View Details",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.editItem:
                Toast.makeText(this,"Edit Details",Toast.LENGTH_SHORT).show();

                //shows EDIT Dialog
                DialogFragment editDialog = new AddEditBatchDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(BATCH_DATA , userStorage.getBatchDetail(batchPosition));

                editDialog.setArguments(bundle);
                editDialog.show(getFragmentManager(),EDIT_DIALOG);
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
                                Toast.makeText(getBaseContext(),"Confirmed",Toast.LENGTH_SHORT).show();

                                // TODO :HANDLE DELETE TEACHER EVENT HERE
                            }
                        });
                builder.show();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void passAddDialogDetail(BatchDetail batchDetail) {
        userStorage.getBatchDetails().add(batchDetail);
        customBatchListAdapter.notifyDataSetChanged();
        FirebaseClass.updateUsers(userStorage);
    }
    @Override
    public void passEditDialogDetail() {
        customBatchListAdapter.notifyDataSetChanged();
        FirebaseClass.updateUsers(userStorage);
    }
}
