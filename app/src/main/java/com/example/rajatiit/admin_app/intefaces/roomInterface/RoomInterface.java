package com.example.rajatiit.admin_app.intefaces.roomInterface;

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
import com.example.rajatiit.admin_app.dataclasses.Institute;
import com.example.rajatiit.admin_app.dataclasses.RoomDetail;

public class RoomInterface extends AppCompatActivity implements AddEditRoomDialog.RoomDetailsPasser{

    CustomRoomListAdapter customRoomListAdapter;

    private int roomPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.ROOM_DATA);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_display_data_interface);

        // displaying the listview ...
        final ListView listView = (ListView) findViewById(R.id.displayDataList);
        customRoomListAdapter = new CustomRoomListAdapter
                (getBaseContext(),R.layout.activity_display_data_interface, new Institute().getRoomDetails());
        listView.setAdapter(customRoomListAdapter);

        //  handling add dialog button
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addDetail);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new AddEditRoomDialog();
                dialogFragment.show(getFragmentManager(),Integer.toString(R.string.ADD_DIALOG));
            }
        });

        // Long item listener for more options ...
        // Adding Context Menu On Item Long Click Listener
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                registerForContextMenu(listView);

                roomPosition = position;

                openContextMenu(listView);
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
            /*case R.id.viewItem:
                Toast.makeText(this,"View Details",Toast.LENGTH_SHORT).show();
                return true;*/

            case R.id.editItem:
                Toast.makeText(this,"Edit Details",Toast.LENGTH_SHORT).show();

                //shows EDIT Dialog
                DialogFragment editDialog = new AddEditRoomDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Integer.toString(R.string.ROOM_DATA),new Institute().getRoomDetails().get(roomPosition));

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
                                deleteRoom();
                            }
                        });
                builder.show();
                return true;
            default:
                return true;
        }
    }

    private void deleteRoom(){
            Toast.makeText(getBaseContext(),"Deleted",Toast.LENGTH_SHORT).show();

            Institute.deleteRoomDetail(roomPosition);
            // updating in database also
            Database.deleteRoomInfo();

            customRoomListAdapter.notifyDataSetChanged();
    }

    @Override
    public void passAddDialogDetail(RoomDetail roomDetail) {
        Institute.addRoomDetail(roomDetail);

        Database.sendRoomInfo(roomDetail,Institute.totalNoOfRooms()-1);

        customRoomListAdapter.notifyDataSetChanged();
    }

    @Override
    public void passEditDialogDetail(RoomDetail roomDetail) {

        Database.sendRoomInfo(roomDetail,roomPosition);

        customRoomListAdapter.notifyDataSetChanged();

    }
}
