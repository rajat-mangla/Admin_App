package com.example.rajatiit.admin_app.intefaces.teacherInterface;

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

import com.example.rajatiit.admin_app.FirebaseClass;
import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.TeacherDetail;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;

public class TeacherInterface extends AppCompatActivity implements AddEditTeacherDialog.TeacherDetailsPasser{

    // fragment TAG Passed to DIALOG FRAGMENT to identify ADD DIALOG
    private final String ADD_DIALOG = "Add_Dialog";

    // fragment TAG Passed to DIALOG FRAGMENT to identify EDIT DIALOG
    private final String EDIT_DIALOG = "Edit_Dialog";

    // tag for finding arguements when its a EDIT DIALOG
    private final String TEACHER_DATA = "TeacherDetails";


    // for getting the position of teacher under department for displaying data for edit dialog
    int teacherPosition;

    // THE EXPANDABLE LISTVIEW OBJECT
    CustomTeacherListAdapter customTeacherListAdapter;

    UserStorage userStorage;
    // TODO : SOLVE THE CONFLICT WITH THIS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Teachers Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.display_data_interface);

        userStorage = new UserStorage();
        // TODO: WORK WITH THIS USING REALTIME DATABASE


        //  ListView for Teachers
        final ListView listView = (ListView) findViewById(R.id.displayDataList);
        customTeacherListAdapter = new CustomTeacherListAdapter(getBaseContext(),R.layout.display_data_interface, userStorage.getTeacherDetails());
        listView.setAdapter(customTeacherListAdapter);

        // Handling Floating Add Button
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addDetail);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addDialog = new AddEditTeacherDialog();
                addDialog.show(getFragmentManager(),ADD_DIALOG);
            }
        });

        // Adding Context Menu On Item Long Click Listener
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                registerForContextMenu(listView);
                openContextMenu(listView);

                teacherPosition =position;

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
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
                DialogFragment editDialog = new AddEditTeacherDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(TEACHER_DATA , userStorage.getTeacherDetail(teacherPosition));

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

    //Methods for getting Add and Edit Dialog Data ...
    @Override
    public void passAddDialogDetail(TeacherDetail teacherDetail) {

        // TODO : NOW HERE YOU HAVE TO UPDATE IN OTHER CLASSES ALSO
        userStorage.getTeacherDetails().add(teacherDetail);
        customTeacherListAdapter.notifyDataSetChanged();

        FirebaseClass.updateUsers(userStorage);
    }


    @Override
    public void passEditDialogDetail(TeacherDetail teacherDetail,String teacherUniqueCode) {
        Toast.makeText(this,"Details Passed",Toast.LENGTH_SHORT).show();

        // TODO Use unique key to update data in other Classes

        customTeacherListAdapter.notifyDataSetChanged();
    }
}
