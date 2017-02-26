package com.example.rajatiit.admin_app.intefaces.classroomInterface;

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
import com.example.rajatiit.admin_app.dataclasses.Classroom;
import com.example.rajatiit.admin_app.dataclasses.Institute;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;

public class ClassroomInterface extends AppCompatActivity implements AddEditCourseDialog.classroomDetailspasser{

    // Tag for passing course Detail to Edit Dialog
    public static final String CLASSROOM_DATA = "CourseDetail";

    // TAG TO PASS DEPARTMENT NAME TO ADD DIALOG and so on ....
    public static final String DEPARTMENT_NAME = "DepartmentName";

    // TAG TO IDENTIFY IF IT'S A ADD DIALOG
    public static final String ADD_DIALOG = "AddDialog";

    // Postion in array to get the Specific Classroom
    private int classroomId;

    // Adapter for displaying the course list
    CustomCourseListAdapter customCourseListAdapter;

    Institute institute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Toast.makeText(getBaseContext(),"In Classroom Interface",Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data_interface);

        institute = new Institute();

        final ListView listView = (ListView) findViewById(R.id.displayDataList);
        customCourseListAdapter = new CustomCourseListAdapter(getBaseContext(),
                R.layout.activity_display_data_interface,institute.getClassrooms());
        listView.setAdapter(customCourseListAdapter);

        // For Listview Long CLick Listener
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // *** IMPORTANT
                classroomId = position;
                registerForContextMenu(listView);
                openContextMenu(listView);
                return true;
            }
        });

        // For Floating action button add details
        final FloatingActionButton addDetail = (FloatingActionButton) findViewById(R.id.addDetail);
        addDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classroomId = institute.getClassrooms().size();
                registerForContextMenu(addDetail);
                openContextMenu(addDetail);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.addDetail) {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.setHeaderTitle("Select Department");
            for (int i = 0; i < institute.getDepartments().size(); i++) {
                menu.add(0, v.getId(), 0, institute.getDepartments().get(i));
            }
        }
        else {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.context_menu,menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.viewItem:
                Toast.makeText(this,"View Details",Toast.LENGTH_SHORT).show();
                // TODO : WORK WITH VIEW DETAILS
                return true;

            case R.id.editItem:
                Toast.makeText(this,"Edit Details",Toast.LENGTH_SHORT).show();
                //shows EDIT Dialog
                DialogFragment editDialog = new AddEditCourseDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(CLASSROOM_DATA,institute.getClassrooms().get(classroomId));

                editDialog.setArguments(bundle);
                editDialog.show(getFragmentManager(),"EditDialog");
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

                                // TODO :HANDLE DELETE Classroom EVENT HERE
                            }
                        });
                builder.show();
                return true;
        }
        for (int i=0;i<institute.getDepartments().size();i++){
            if (item.getTitle().toString().equals(institute.getDepartments().get(i))){

                DialogFragment addDialog = new AddEditCourseDialog();
                Bundle bundle = new Bundle();
                bundle.putString(DEPARTMENT_NAME,item.getTitle().toString());
                addDialog.setArguments(bundle);

                addDialog.show(getFragmentManager(),ADD_DIALOG);

                // TODO pass value of department to course display activity
                return true;
            }
        }
        return true;
    }

    @Override
    public void passAddDialogDetails(Classroom classroom) {
        institute.getClassrooms().add(classroom);
        UserStorage.getTeacherDetail(classroom.getTeacherId()).getClassroomIds().add(classroomId);
        UserStorage.getBatchDetail(classroom.getBatchId()).getClassroomIds().add(classroomId);
        FirebaseClass.updateInstitute(institute);
        FirebaseClass.updateUsers(new UserStorage());
    }

    @Override
    public void passEditDialogDetails(Classroom classroom) {
        UserStorage.getTeacherDetail(classroom.getTeacherId()).getClassroomIds().add(classroomId);
        UserStorage.getBatchDetail(classroom.getBatchId()).getClassroomIds().add(classroomId);
        FirebaseClass.updateInstitute(institute);
        FirebaseClass.updateUsers(new UserStorage());
    }
}
