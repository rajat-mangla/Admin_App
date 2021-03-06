package com.example.rajatiit.admin_app.activities.intefaces.teacherInterface;

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

import com.example.rajatiit.admin_app.dataclasses.Database;
import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.users.TeacherDetail;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;

public class TeacherInterface extends AppCompatActivity implements AddEditTeacherDialog.TeacherDetailsPasser{

    // for getting the position of teacher under department for displaying data for edit dialog
    int teacherPosition;

    // THE EXPANDABLE LISTVIEW OBJECT
    CustomTeacherListAdapter customTeacherListAdapter;

    UserStorage userStorage;
    // TeDO : SOLVE THE CONFLICT WITH THIS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.TEACHER_DATA);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_display_data_interface);

        userStorage = new UserStorage();
        // teDO: WORK WITH THIS USING REALTIME DATABASE


        //  ListView for Teachers
        final ListView listView = (ListView) findViewById(R.id.displayDataList);
        listView.setDivider(null);
        listView.setDividerHeight(0);

        // Setting the adapter for the listView
        customTeacherListAdapter = new CustomTeacherListAdapter(getBaseContext()
                ,R.layout.activity_display_data_interface, userStorage.getTeacherDetails());
        listView.setAdapter(customTeacherListAdapter);

        // Handling Floating Add Button
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addDetail);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addDialog = new AddEditTeacherDialog();
                addDialog.show(getFragmentManager(),Integer.toString(R.string.ADD_DIALOG));
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
            /*case R.id.viewItem:
                Toast.makeText(this,"View Details",Toast.LENGTH_SHORT).show();
                return true;*/

            case R.id.editItem:
                Toast.makeText(this,"Edit Details",Toast.LENGTH_SHORT).show();

                //shows EDIT Dialog
                DialogFragment editDialog = new AddEditTeacherDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Integer.toString(R.string.TEACHER_DATA),UserStorage.getTeacherDetail(teacherPosition));

                editDialog.setArguments(bundle);
                editDialog.show(getFragmentManager(),Integer.toString(R.string.EDIT_DIALOG));
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

                                deleteTeacher();

                            }
                        });
                builder.show();
                return true;
            default:
                return true;
        }
    }

    private void deleteTeacher(){
        if (UserStorage.getTeacherDetail(teacherPosition).getClassroomIds().size() == 0){
            Toast.makeText(getBaseContext(),"Deleted",Toast.LENGTH_SHORT).show();

            UserStorage.deleteTeacherDetail(teacherPosition);



            // updating in database also
            Database.deleteTeacherInfo();
            customTeacherListAdapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(getBaseContext(),"Cannot Delete :" +
                    "Teacher already teaches some courses",Toast.LENGTH_SHORT).show();
        }
    }

    //Methods for getting Add and Edit Dialog Data ...
    @Override
    public void passAddDialogDetail(TeacherDetail teacherDetail) {
        /*
        THE BATCH ID IS USED TO GET BATCH FROM TOTAL BATCHES
         IT IS STORED IN CLASSROOM OBJECT TO LINK Course and teachers
         */
        teacherDetail.setTeacherId(UserStorage.noOfTeachers());

        UserStorage.addTeacherDetail(teacherDetail);

        Database.sendTeacherInfo(teacherDetail);

        customTeacherListAdapter.notifyDataSetChanged();
    }


    @Override
    public void passEditDialogDetail(TeacherDetail teacherDetail,String teacherUniqueCode) {
        Database.sendTeacherInfo(teacherDetail);

        customTeacherListAdapter.notifyDataSetChanged();
    }
}
